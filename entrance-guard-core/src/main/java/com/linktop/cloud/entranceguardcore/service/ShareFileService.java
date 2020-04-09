package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.commonutils.BusiSvrUploadFileUsage;
import com.linktop.cloud.entranceguardcore.dao.ShareFileMapper;
import com.linktop.cloud.entranceguardmodel.BusiCommonResult;
import com.linktop.cloud.entranceguardmodel.GetPtuidListResult;
import com.linktop.cloud.entranceguardmodel.database.ShareFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShareFileService {
    private static Logger log = LoggerFactory.getLogger(ShareFileService.class);

    @Autowired
    private BusiServerApiService busiServerApiService;
    @Autowired
    private ShareFileMapper shareFileMapper;

    public int add(String usage,
                   String opType,
                   String deviceAliaId,
                   String userAliaId,
                   String fnOnServer,
                   boolean needDel) {
        int ret = 0;
        // 给一个设备增加一个用户信息之前，判断给设备下发过的这个用户信息文件的个数，
        // 设备没有用户信息文件就直接添加
        delCurrentFile(usage,
                deviceAliaId,
                userAliaId);
        log.info("add, usage:{}, opType:{}, userAliaId:{}, fnOnServer:{}",
                usage, opType, userAliaId, fnOnServer);
        // 删除操作不需要同步文件，不用添加到数据库
        if(!opType.equalsIgnoreCase("delete")) {
            ret = shareFileMapper.add(usage,
                    opType,
                    deviceAliaId,
                    userAliaId,
                    fnOnServer,
                    needDel);
        }

        return ret;
    }

    public int updateNeedDel(int id, boolean needDel) {
        return shareFileMapper.updateNeedDel(id, needDel);
    }

    public int updateNeedDelByDeviceAliaIdFn(String deviceAliaId, String fn, boolean needDel) {
        return shareFileMapper.updateNeedDelByDeviceAliaIdFn(deviceAliaId, fn, needDel);
    }

    public int addMutiDev(String usage,
                          String opType,
                          List<String> listDeviceAliaId,
                          String userAliaId,
                          String fnOnServer) {
        int ret = 0;
        for(String deviceAliaId:listDeviceAliaId) {
            ret = add(usage,
                    opType,
                    deviceAliaId,
                    userAliaId,
                    fnOnServer,
                    false);
        }

        return ret;
    }

    public int delete(int id) {
        return shareFileMapper.delete(id);
    }

    public int batchDel(List<Integer> ids) {
        return shareFileMapper.batchDel(ids);
    }

    public int deleteByFn(String fn) {
        return shareFileMapper.deleteByFn(fn);
    }

    public int deleteByDeviceAliaIdFn(String deviceAliaId, String fn) {
        return shareFileMapper.deleteByDeviceAliaIdFn(deviceAliaId, fn);
    }

    public int batchDelByFns(List<String> fns) {
        return shareFileMapper.batchDelByFn(fns);
    }

    public List<ShareFile> getList(String usage,
                                   String deviceAliaId,
                                   String userAliaId) {
        return shareFileMapper.getList(usage, deviceAliaId, userAliaId);
    }

    public List<String> getListFnOnly(String usage,
                                      String deviceAliaId,
                                      String userAliaId) {
        List<ShareFile> listUserFn = shareFileMapper.getList(usage, deviceAliaId, userAliaId);
        if(CollectionUtils.isEmpty(listUserFn)) {
            return null;
        }
        List<String> listFn = new ArrayList<>();
        for(ShareFile uf:listUserFn) {
            listFn.add(uf.getFnOnServer());
        }
        return listFn;
    }

    public void delCurrentFile(String usage,
                   String deviceAliaId,
                   String userAliaId) {
        BusiCommonResult br;
        log.info("delCurrentFile, usage:{},deviceAliaId:{},userAliaId:{}",
                usage, deviceAliaId, userAliaId);
        List<String>listFn = getListFnOnly(usage,
                deviceAliaId,
                userAliaId);
        if(!CollectionUtils.isEmpty(listFn)) {
            // 对每个已添加的用户信息文件（可能有多个设备同时添加同一文件），
            // 针对一个设备删除该条记录
            // 再读取该文件对所有设备的记录条数
            // 记录数为0说明已经没有设备使用该文件，删除其云端文件
            for(String fn:listFn) {
                // share_file exc=deviceAlidId
                br = busiServerApiService.rmFileByPtuid(deviceAliaId,
                        usage,
                        fn);
                if(br.getState() == 0) {
                    log.info("rmFileByPtuid success:{},{},{}", deviceAliaId, usage, fn);
                    shareFileMapper.deleteByDeviceAliaIdFn(deviceAliaId, fn);
                } else {
                    log.error("rm_file by ptuid {} fail, usage:{}, fn:{}, mark to db",
                            deviceAliaId, usage, fn);
                    shareFileMapper.updateNeedDelByDeviceAliaIdFn(deviceAliaId, fn, true);
                }

                if(shareFileMapper.getFnCount(fn) == 0) {
                    log.info("rm server file:{}", "".join(",", listFn));
                    br = busiServerApiService.rmFile(usage,
                            "".join(",", listFn));
                }
            }
        }
    }

    public List<ShareFile> getListGroupByFn(String usage) {
        return shareFileMapper.getListGroupByFn(usage);
    }

    //每隔30秒执行一次
    @Scheduled(fixedRate = 30000)
    public void tryDel() {
        BusiCommonResult br;
        int delCount = 10;
        List<ShareFile> listDel = shareFileMapper.getNeedDelList(null, null, true);
        for(ShareFile uf:listDel) {
            br = busiServerApiService.rmFileByPtuid(
                    uf.getDeviceAliaId(),
                    uf.getFileUsage(),
                    uf.getFnOnServer());
            if(br.getState() == 0) {
                shareFileMapper.deleteByDeviceAliaIdFn(uf.getDeviceAliaId(), uf.getFnOnServer());
            } else {
                log.error("tryDel,rmFileByPtuid failed. state:{},msg:{}" +
                                ",ptuid:{},usage:{},fn:{}",
                        br.getState(),
                        br.getMsg(),
                        uf.getDeviceAliaId(),
                        uf.getFileUsage(),
                        uf.getFnOnServer());
                GetPtuidListResult res = busiServerApiService.getPtuidList();
                if(res.getState() == 0) {
                    boolean delFromDb = true;
                    List<String> listPtuid = res.getListPtuid();
                    if(!CollectionUtils.isEmpty(listPtuid)) {
                        for(String ptuid:listPtuid) {
                            if(ptuid.equalsIgnoreCase(uf.getDeviceAliaId())) {
                                delFromDb = false;
                                break;
                            }
                        }
                    }
                    if(delFromDb) {
                        log.error("{} not in server list, delete fn:{}",
                                uf.getDeviceAliaId(),
                                uf.getFnOnServer());
                        shareFileMapper.deleteByDeviceAliaIdFn(
                                uf.getDeviceAliaId(),
                                uf.getFnOnServer()
                        );
                    }
                }
            }

            if(shareFileMapper.getFnCount(uf.getFnOnServer()) == 0) {
                log.info("rm server file:{}", uf.getFnOnServer());
                br = busiServerApiService.rmFile(
                        uf.getFileUsage(),
                        uf.getFnOnServer());
            }
            if(--delCount == 0) {
                // 每次最多只处理10个
                break;
            }
        }

    }

}
