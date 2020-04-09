package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.commonutils.BusiSvrUploadFileUsage;
import com.linktop.cloud.entranceguardcore.dao.DeviceMapper;
import com.linktop.cloud.entranceguardcore.dao.ShareFileMapper;
import com.linktop.cloud.entranceguardmodel.BusiCommonResult;
import com.linktop.cloud.entranceguardmodel.ConnStateResult;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.Device;
import com.linktop.cloud.entranceguardmodel.database.ShareFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {
    private static Logger log = LoggerFactory.getLogger(DeviceService.class);
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private ShareFileMapper shareFileMapper;
    @Autowired
    private BusiServerApiService busiServerApiService;

    public int add(String buildingNum,
                   String unitNum,
                   String macAddr,
                   int type,
                   int estateId,
                   String aliaId) {

        return deviceMapper.add(buildingNum,
                                unitNum,
                                macAddr,
                                type,
                                estateId,
                                aliaId);
    }

    public int update(Device device) {
        return deviceMapper.update(device.getId(),
                                    device.getBuildingNum(),
                                    device.getUnitNum(),
                                    device.getMacAddr(),
                                    device.getType(),
                                    device.getEstateId(),
                                    device.getAliaId());
    }

    public int delete(int id) {
        return deviceMapper.delete(id);
    }

    public int batchDel(List<Integer> ids) {
        return deviceMapper.batchDel(ids);
    }

    public int deleteByAliaId(String aliaId) {
        return deviceMapper.deleteByAliaId(aliaId);
    }

    public int batchDelByAliaId(List<String> aliaIds) {
        return deviceMapper.batchDelByAliaId(aliaIds);
    }

    public Device get(int id) {
        return deviceMapper.get(id);
    }

    public Device getByAliaId(String aliaId) {
        return deviceMapper.getByAliaId(aliaId);
    }

    public List<Device> getList() {
        return deviceMapper.getList();
    }

    public List<String> getDeviceAliaIdList() {
        List<Device> listDevice = getList();
        if(CollectionUtils.isEmpty(listDevice)) {
            return null;
        }
        List<String> listAliaId = new ArrayList<>();
        for (Device d:listDevice) {
            listAliaId.add(d.getAliaId());
        }

        return listAliaId;
    }

    public List<String> getDeviceAliaIdList(String ids) {
        List<Device> listDevice = batchGet(ids);
        List<String> listIds = new ArrayList<>();
        if(StringUtils.isEmpty(listDevice)) {
            return listIds;
        }

        for(Device d:listDevice) {
            listIds.add(d.getAliaId());
        }

        return listIds;
    }

    public List<String> getDeviceAliaIdListByAliaIds(String aliaIds) {
        List<Device> listDevice = batchGetByAliaIds(aliaIds);
        List<String> listIds = new ArrayList<>();
        if(StringUtils.isEmpty(listDevice)) {
            return listIds;
        }

        for(Device d:listDevice) {
            listIds.add(d.getAliaId());
        }

        return listIds;
    }

    public PageWrapper<Device> getPage(int page,
                                             int count,
                                             String filter) {
        PageWrapper<Device> pw = new PageWrapper<Device>();
        int total = deviceMapper.getCount(filter);
        List<Device> listDevice = deviceMapper.getPage((page - 1) * count, count, filter);
        for(Device d:listDevice) {
            ConnStateResult conn = busiServerApiService.getConnState(d.getAliaId());
            if(conn.getState() == 0) {
                d.setConnBegin(conn.getBegin());
                d.setConnIp(conn.getIp());
                d.setConnOnline(conn.getOnline());
            }
        }
        pw.setTotal(total);
        pw.setData(listDevice);
        return pw;
    }

    public List<Device> batchGetByAliaIds(List<String> listAliaId) {
        return deviceMapper.batchGetByAliaId(listAliaId);
    }

    public List<Device> batchGet(String ids) {
        if(StringUtils.isEmpty(ids)) {
            return null;
        }
        List<String> listStrIds = CollectionUtils.arrayToList(ids.split(","));
        List<Integer> listIds = new ArrayList<>();
        for(String strId:listStrIds) {
            listIds.add(Integer.valueOf(strId));
        }
        if(CollectionUtils.isEmpty(listIds)) {
            return null;
        }
        return deviceMapper.batchGet(listIds);
    }

    public List<Device> batchGet(List<Integer> ids) {
        return deviceMapper.batchGet(ids);
    }


    public List<Device> batchGetByAliaIds(String aliaIds) {
        if(StringUtils.isEmpty(aliaIds)) {
            return null;
        }
        List<String> listAliaIds = CollectionUtils.arrayToList(aliaIds.split(","));
        if(CollectionUtils.isEmpty(listAliaIds)) {
            return null;
        }
        return deviceMapper.batchGetByAliaId(listAliaIds);
    }

    public String getExcludeDeviceIds(List<String>all, List<String>inc) {
        if(CollectionUtils.isEmpty(all)) {
            return "";
        }
        if(CollectionUtils.isEmpty(inc)) {
            return "".join(",", all);
        }
        List<String>listExclud = new ArrayList<>(all);

        for(String i:inc) {
            for(String ex:listExclud) {
                if(ex.equalsIgnoreCase(i)) {
                    listExclud.remove(ex);
                    break;
                }
            }
        }
        return "".join(",", listExclud);
    }

    public String getExcludeDeviceIds(List<String>all, String inc) {
        List<String> listInc = new ArrayList<String>();
        listInc.add(inc);
        return getExcludeDeviceIds(all, listInc);
    }

    public List<ShareFile> getInitFileList(String fileUsage) {
        return shareFileMapper.getListGroupByFn(fileUsage);
    }

    public int delInitFileList(String deviceAliaId) {
        // 删除u_info
        List<ShareFile> listShareFile = getInitFileList(BusiSvrUploadFileUsage.USER_INFO);
        int pos = 0;
        int max = 64;
        if(!CollectionUtils.isEmpty(listShareFile)) {

            if(listShareFile.size() > 64) {
                do {
                    delInitFileListPart(deviceAliaId,
                            BusiSvrUploadFileUsage.USER_INFO,
                            listShareFile.subList(pos, pos + max));
                    pos += max;
                    if(pos + max >= listShareFile.size()) {
                        max = listShareFile.size() - pos - 1;
                    }
                } while(pos < listShareFile.size() - 1);
            } else {
                delInitFileListPart(deviceAliaId,
                        BusiSvrUploadFileUsage.USER_INFO,
                        listShareFile);
            }
        }
        // 删除cfg
        listShareFile = getInitFileList(BusiSvrUploadFileUsage.DEVICE_CONFIG);
        if(!CollectionUtils.isEmpty(listShareFile)) {
            pos = 0;
            max = 64;

            if(listShareFile.size() > 64) {
                do {
                    delInitFileListPart(deviceAliaId,
                            BusiSvrUploadFileUsage.DEVICE_CONFIG,
                            listShareFile.subList(pos, pos + max));
                    pos += max;
                    if(pos + max >= listShareFile.size()) {
                        max = listShareFile.size() - pos - 1;
                    }
                } while(pos < listShareFile.size() - 1);
            } else {
                delInitFileListPart(deviceAliaId,
                        BusiSvrUploadFileUsage.DEVICE_CONFIG,
                        listShareFile);
            }
        }
        return 1;
    }

    public int delInitFileListPart(String deviceAliaId, String usage, List<ShareFile>listShareFile) {
        String fns;
        BusiCommonResult bcr;
        List<String> listFnOnServer = new ArrayList<>();
        for(ShareFile sf:listShareFile) {
            listFnOnServer.add(sf.getFnOnServer());
        }
        log.info("delInitFileList listFnOnServer count:{} [0]:{}",
                listFnOnServer.size(),
                listFnOnServer.get(0));
        fns = "".join("|", listFnOnServer);
        bcr = busiServerApiService.rmFileByPtuid(
                deviceAliaId,
                usage,
                fns);
        if(bcr.getState() == 0) {
            log.info("delInitFileList {} for {} success!", usage, deviceAliaId);
        } else {
            // 网络删除失败，插入数据库定时器会删除
            log.info("delInitFileList u_info for {} failed, add needDel to db", deviceAliaId);
            for(ShareFile sf:listShareFile) {
                shareFileMapper.add(
                        usage,
                        "delete",
                        deviceAliaId,
                        sf.getUserAliaId(),
                        sf.getFnOnServer(),
                        true);
            }
        }
        return 1;
    }
}
