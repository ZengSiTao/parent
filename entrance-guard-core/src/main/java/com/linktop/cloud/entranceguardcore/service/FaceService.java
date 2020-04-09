package com.linktop.cloud.entranceguardcore.service;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.coder.Coder;
import com.linktop.cloud.commonutils.BusiSvrUploadFileUsage;
import com.linktop.cloud.commonutils.FilePaths;
import com.linktop.cloud.entranceguardcore.dao.FaceMapper;
import com.linktop.cloud.entranceguardcore.dao.UsersMapper;
import com.linktop.cloud.entranceguardmodel.BusiCommonResult;
import com.linktop.cloud.entranceguardmodel.FileDownloadResult;
import com.linktop.cloud.entranceguardmodel.FileUploadResult;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.Device;
import com.linktop.cloud.entranceguardmodel.database.Face;
import com.linktop.cloud.entranceguardmodel.database.User;
import com.linktop.cloud.entranceguardmodel.sync.SyncVOConvert;
import com.linktop.cloud.entranceguardmodel.sync.UserForDevice;
import com.linktop.cloud.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class FaceService {
    private static Logger log = LoggerFactory.getLogger(FaceService.class);
    @Autowired
    private FaceMapper faceMapper;
    @Autowired
    private UsersService usersService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    private BusiServerApiService busiServerApiService;

    public int add(int userId,
                   String deviceIds,
                   String fnOnServer) {
        int ret = 0;
        ret = faceMapper.add(userId,
                deviceIds,
                fnOnServer);
        if(ret == 1) {
            notifyAdd(userId,
                    deviceIds,
                    fnOnServer);
        }
        return ret;
    }

    public int update(Face face) {
        notifyUpdate(face);
        return faceMapper.update(face.getId(),
                                    face.getUserId(),
                                    face.getDeviceIds(),
                                    face.getFnOnServer());
    }

    public int delete(int id) {
        notifyDel(id);
        int ret = faceMapper.delete(id);
        return ret;
    }

    public int batchDel(List<Integer> ids) {
        for(int id:ids) {
            notifyDel(id);
        }
        return faceMapper.batchDel(ids);
    }

    public Face get(int id) {
        return faceMapper.get(id);
    }

    public Face getByUserId(int userId) {
        return faceMapper.getByUserId(userId);
    }

    public List<Face> getList() {
        List<Face> list = faceMapper.getList();
        for (Face item:list) {
            try {
                String saveName = URLEncoder.encode(item.getFnOnServer(), "utf-8");
                String saveFullPath = FilePaths.STORE_FACE_FILE_PATH + saveName;
                FileDownloadResult fdload = null;
                if(! FileUtil.exists(saveFullPath)) {
                    fdload = busiServerApiService.fileDownload("face", item.getFnOnServer());
                    if(0 == fdload.getState()) {
                        FileUtil.byte2File(fdload.getBody(), FilePaths.STORE_FACE_FILE_PATH, saveName);
                    }
                }
                item.setWebAccessName(FilePaths.WEB_FACE_FILE_ROOT + URLEncoder.encode(saveName, "utf-8"));
            } catch (Exception e) {
                log.error("FaceService getList exception:" + e.getMessage());
            }
        }
        return list;
    }

    public PageWrapper<Face> getPage(int page,
                                             int count,
                                             String filter) {
        PageWrapper pw = new PageWrapper();
        List<Face> list = faceMapper.getPage((page - 1) * count, count, filter);
        for (Face item:list) {
            try {
                String saveName = URLEncoder.encode(item.getFnOnServer(), "utf-8");
                String saveFullPath = FilePaths.STORE_FACE_FILE_PATH + saveName;
                FileDownloadResult fdload = null;
                if(! FileUtil.exists(saveFullPath)) {
                    fdload = busiServerApiService.fileDownload("face", item.getFnOnServer());
                    if(0 == fdload.getState()) {
                        FileUtil.byte2File(fdload.getBody(), FilePaths.STORE_FACE_FILE_PATH, saveName);
                    }
                }
                item.setWebAccessName(FilePaths.WEB_FACE_FILE_ROOT + URLEncoder.encode(saveName, "utf-8"));
            } catch (Exception e) {
                log.error("FaceService getList exception:" + e.getMessage());
            }
        }
        pw.setTotal(faceMapper.getCount());
        pw.setData(list);
        return pw;

    }


    private int notifyDel(int id) {
        int ret = 0;
        Face face = faceMapper.get(id);
        if(face == null) {
            return ret;
        }

        User user = usersService.get(face.getUserId());
        UserForDevice u2d = new UserForDevice();
        SyncVOConvert.convert_User_UserForDevice(user, u2d);
        //u2d.setOpType("update");
        try {
            u2d.setImgB64("");
            FileUploadResult fr = busiServerApiService.fileUpload(BusiSvrUploadFileUsage.USER_INFO, JSON.toJSONString(u2d).getBytes("utf-8"));
            if(fr.getState() != 0) {
                throw new Exception(fr.getMsg());
            }

            List<String> listIds = new ArrayList<String>();
            List<Device> ld = null;
            if(!StringUtils.isEmpty(face.getDeviceIds())){
                ld = deviceService.batchGet(face.getDeviceIds());
            }
            if(!CollectionUtils.isEmpty(ld)) {
                for(Device d:ld) {
                    listIds.add(d.getAliaId());
                }
            }
            if(CollectionUtils.isEmpty(listIds)) {
                return ret;
            }
            log.info("face delete,shareFile to {}", "".join(",", listIds));
            BusiCommonResult sr = busiServerApiService.shareFile(fr.getFn(), "".join(",", listIds), null);
            if(sr.getState() != 0) {
                ret = 1;
                log.info("faceService add share to device failed, state={}", sr.getState());
            }
        } catch (Exception e) {
            log.info("faceService add share to device failed, exception={}", e.getMessage());
        }
        return ret;
    }


    private int notifyUpdate(Face face) {
        int ret = 0;
        Face faceOri = faceMapper.get(face.getId());
        if(face == null) {
            return ret;
        }
        String[] strArrOriIds = null;
        String[] strArrNewIds = null;
        List<Integer> listOriIds = new ArrayList<>();
        List<Integer> listNewIds = new ArrayList<>();
        if(!StringUtils.isEmpty(faceOri.getDeviceIds())) {
            strArrOriIds = faceOri.getDeviceIds().split(",");
            for(String strId:strArrOriIds) {
                listOriIds.add(Integer.valueOf(strId));
            }
        }
        if(!StringUtils.isEmpty(face.getDeviceIds())) {
            strArrNewIds = face.getDeviceIds().split(",");
            for(String strId:strArrNewIds) {
                listNewIds.add(Integer.valueOf(strId));
            }
        }
        if(CollectionUtils.isEmpty(listOriIds) &&
                CollectionUtils.isEmpty(listNewIds)) {
            return ret;
        }
        List<Integer> listAddIds = new ArrayList<>(listNewIds);

        List<Integer> listDelIds = new ArrayList<>(listOriIds);
        for(Integer idNew:listNewIds) {
            for(Integer idDel:listDelIds) {
                if(idNew.intValue() == idDel.intValue()) {
                    listDelIds.remove(idDel);
                    break;
                }
            }
        }
        if(!CollectionUtils.isEmpty(listAddIds)) {
            notifyAdd(face.getUserId(),
                    StringUtils.arrayToDelimitedString(listAddIds.toArray(), ","),
                    face.getFnOnServer());
        }
        if(!CollectionUtils.isEmpty(listDelIds)) {
            for(Integer idDel:listDelIds) {
                notifyDel(idDel.intValue());
            }
        }
        return ret;
    }


    private int notifyAdd(int userId,
                          String deviceIds,
                          String fnOnServer) {
        int ret = 0;
        if(StringUtils.isEmpty(deviceIds)) {
            return ret;
        }
        User user = usersService.get(userId);
       UserForDevice u2d = new UserForDevice();
        SyncVOConvert.convert_User_UserForDevice(user, u2d);
        //u2d.setOpType("update");
        FileDownloadResult fdload = busiServerApiService.fileDownload("face", fnOnServer);
        if(0 == fdload.getState()) {
            try {
                u2d.setImgB64(Coder.encryptBASE64(fdload.getBody()));
                FileUploadResult fr = busiServerApiService.fileUpload(BusiSvrUploadFileUsage.USER_INFO, JSON.toJSONString(u2d).getBytes("utf-8"));
                if(fr.getState() != 0) {
                    throw new Exception(fr.getMsg());
                }

                List<String> listIds = new ArrayList<String>();
                List<Device> ld = null;
                if(!StringUtils.isEmpty(deviceIds)) {
                    ld = deviceService.batchGet(deviceIds);
                }
                if(CollectionUtils.isEmpty(ld)) {
                    return ret;
                }
                for(Device d:ld) {
                    listIds.add(d.getAliaId());
                }
                if(CollectionUtils.isEmpty(listIds)) {
                    return ret;
                }
                log.info("face add,shareFile to {}", "".join(",", listIds));
                BusiCommonResult sr = busiServerApiService.shareFile(fr.getFn(), "".join(",", listIds), null);
                if(sr.getState() != 0) {
                    ret = 1;
                    log.info("faceService add share to device failed, state={}", sr.getState());
                }
            } catch (Exception e) {
                log.info("faceService add share to device failed, exception={}", e.getMessage());
            }
        }
        return ret;
    }
}
