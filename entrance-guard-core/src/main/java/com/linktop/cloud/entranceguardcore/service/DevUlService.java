package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.commonutils.BusiSvrUploadFileUsage;
import com.linktop.cloud.commonutils.FilePaths;
import com.linktop.cloud.entranceguardcore.dao.DevUlMapper;
import com.linktop.cloud.entranceguardcore.dao.GateEventMapper;
import com.linktop.cloud.entranceguardmodel.FileDownloadResult;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.DevUl;
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
public class DevUlService {
    private static Logger log = LoggerFactory.getLogger(DevUlService.class);

    @Autowired
    private BusiServerApiService busiServerApiService;
    @Autowired
    private DevUlMapper devUlMapper;

    public int add(String receipt,
                   String usage,
                   String fn) {
        int ret = 0;
        log.info("add, receipt:{}, usage:{}, fn:{}", receipt, usage, fn);
        boolean bIsVideo = usage.equalsIgnoreCase(BusiSvrUploadFileUsage.GATEEVENT_PHOTO);
        try {
            String saveName = URLEncoder.encode(fn, "utf-8");
            String saveFullPath = FilePaths.STORE_GATEEVENT_FILE_PATH + saveName;
            FileDownloadResult fdload = null;

            if(! FileUtil.exists(bIsVideo ? saveFullPath + ".mp4" : saveFullPath)) {
                fdload = busiServerApiService.fileDownload(usage, fn);
                if(0 == fdload.getState()) {
                    FileUtil.byte2File(fdload.getBody(), FilePaths.STORE_GATEEVENT_FILE_PATH, saveName);
                    if(bIsVideo) {
                        FileUtil.convertH264Mp4(saveFullPath, saveFullPath + ".mp4");
                        FileUtil.deleteFile(saveFullPath);
                    }
                }
            }
        } catch (Exception e) {
            log.error("add exception:{}", e);
        }
        ret = devUlMapper.add(receipt,
                usage + "," + fn);
        return ret;
    }

    public int add(DevUl devUl) {
        return add(devUl.getReceipt(), devUl.getUsage(), devUl.getFn());
    }

    public int delete(int id) {
        return devUlMapper.delete(id);
    }

    public int batchDel(List<Integer> ids) {
        return devUlMapper.batchDel(ids);
    }

}
