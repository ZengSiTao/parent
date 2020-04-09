package com.linktop.cloud.entranceguardcore.service;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.coder.Coder;
import com.linktop.cloud.commonutils.BusiSvrUploadFileUsage;
import com.linktop.cloud.commonutils.FilePaths;
import com.linktop.cloud.entranceguardcore.dao.GateEventMapper;
import com.linktop.cloud.entranceguardmodel.FileDownloadResult;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.GateEvent;
import com.linktop.cloud.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class GateEventService {
    private static Logger log = LoggerFactory.getLogger(GateEventService.class);


    @Autowired
    private GateEventMapper gateEventMapper;
    @Autowired
    private BusiServerApiService busiServerApiService;

    public int add(String lockCat,
                   Integer srcTs,
                   String media,
                   String userAliaId,
                   String deviceAliaId,
                   String receipt) {
        int ret = 0;
        /*for(String fn:fnList.split(";")) {
            String[] fnSplit = fn.split(",");

            boolean bIsVideo = fnSplit[0].equalsIgnoreCase(BusiSvrUploadFileUsage.GATEEVENT_VIDEO);

            try {
                String saveName = URLEncoder.encode(fnSplit[1], "utf-8");
                String saveFullPath = FilePaths.GATEEVENT_FILE_PATH + saveName;
                FileDownloadResult fdload = null;

                if(! FileUtil.exists(bIsVideo ? saveFullPath + ".mp4" : saveFullPath)) {
                    fdload = busiServerApiService.fileDownload(fnSplit[0], fnSplit[1]);
                    if(0 == fdload.getState()) {
                        FileUtil.byte2File(fdload.getBody(), FilePaths.GATEEVENT_FILE_PATH, saveName);
                        if(bIsVideo) {
                            convertH264Mp4(saveFullPath, saveFullPath + ".mp4");
                            FileUtil.deleteFile(saveFullPath);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("getPage exception:{}", e);
            }
        }*/
        log.info("GateEventService add, lockCat:{}, srcTs:{}, media:{}, userAliaId:{}, " +
                "deviceAliaId:{}, receipt:{}", lockCat, srcTs, media, userAliaId,
                deviceAliaId, receipt);
        ret = gateEventMapper.add(lockCat,
                                  srcTs,
                                  media,
                                  userAliaId,
                                  deviceAliaId,
                                  receipt);
        return ret;
    }

    public int update(GateEvent gateEvent) {
        return gateEventMapper.update(gateEvent.getId(),
                                      gateEvent.getLockCat(),
                                      gateEvent.getSrcTs(),
                                      gateEvent.getMedia(),
                                      gateEvent.getUserAliaId(),
                                      gateEvent.getDeviceAliaId(),
                                      gateEvent.getReceipt());
    }

    public int delete(int id) {
        return gateEventMapper.delete(id);
    }

    public int batchDel(List<Integer> ids) {
        return gateEventMapper.batchDel(ids);
    }

    public GateEvent get(int id) {
        return gateEventMapper.get(id);
    }

    public List<GateEvent> getList() {
        return gateEventMapper.getList();
    }

    public PageWrapper<GateEvent> getPage(int page,
                                             int count,
                                             String filter) {
        PageWrapper pw = new PageWrapper();
        List<GateEvent> list = gateEventMapper.getPage((page - 1) * count, count, filter);
        for(GateEvent ge:list) {
            String fnList = ge.getFnList();
            if(null == fnList) {
                continue;
            }
            List<String> listPictureName = new ArrayList<String>();
            List<String> listVideoName = new ArrayList<String>();
            ge.setListPictureName(listPictureName);
            ge.setListVideoName(listVideoName);
            for(String fn:fnList.split(";")) {
                String[] fnSplit = fn.split(",");

                boolean bIsVideo = fnSplit[0].equalsIgnoreCase(BusiSvrUploadFileUsage.GATEEVENT_VIDEO);

                try {
                    String saveName = URLEncoder.encode(fnSplit[1], "utf-8");
                    String saveFullPath = FilePaths.STORE_GATEEVENT_FILE_PATH + saveName;
                    FileDownloadResult fdload = null;

                    if(! FileUtil.exists(bIsVideo ? saveFullPath + ".mp4" : saveFullPath)) {
                        fdload = busiServerApiService.fileDownload(fnSplit[0], fnSplit[1]);
                        if(0 == fdload.getState()) {
                            FileUtil.byte2File(fdload.getBody(), FilePaths.STORE_GATEEVENT_FILE_PATH, saveName);
                            if(bIsVideo) {
                                FileUtil.convertH264Mp4(saveFullPath, saveFullPath + ".mp4");
                                FileUtil.deleteFile(saveFullPath);
                            }
                        }
                    }
                    saveName = FilePaths.WEB_GATEEVENT_FILE_ROOT + URLEncoder.encode(saveName, "utf-8");
                    if(bIsVideo) {
                        listVideoName.add(saveName + ".mp4");
                    } else {
                        listPictureName.add(saveName);
                    }
                } catch (Exception e) {
                    log.error("getPage exception:{}", e);
                }
            }
        }
        pw.setTotal(gateEventMapper.getCount());
        pw.setData(list);
        return pw;
    }


}
