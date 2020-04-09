package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.coder.Coder;
import com.linktop.cloud.commonutils.FilePaths;
import com.linktop.cloud.entranceguardcore.dao.BlacklistFaceMapper;
import com.linktop.cloud.entranceguardcore.dao.BlacklistICCardMapper;
import com.linktop.cloud.entranceguardmodel.FileDownloadResult;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.BlacklistFace;
import com.linktop.cloud.entranceguardmodel.database.BlacklistICCard;
import com.linktop.cloud.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.util.List;

@Service
public class BlacklistFaceService {
    private static Logger log = LoggerFactory.getLogger(BlacklistFaceService.class);
    @Autowired
    private BlacklistFaceMapper blacklistFaceMapper;
    @Autowired
    private BusiServerApiService busiServerApiService;

    public int add(int estateId,
                   int deviceId,
                   int iccardId) {
        return blacklistFaceMapper.add(estateId,
                                        deviceId,
                                        iccardId);
    }

    public int update(BlacklistFace blacklistface) {
        return blacklistFaceMapper.update(blacklistface.getId(),
                                        blacklistface.getEstateId(),
                                        blacklistface.getDeviceId(),
                                        blacklistface.getFaceId());
    }

    public int delete(int id) {
        return blacklistFaceMapper.delete(id);
    }

    public int batchDel(List<Integer> ids) {
        return blacklistFaceMapper.batchDel(ids);
    }

    public BlacklistFace get(int id) {
        return blacklistFaceMapper.get(id);
    }

    public List<BlacklistFace> getList(int deviceId) {
        return blacklistFaceMapper.getList(deviceId);
    }

    public PageWrapper<BlacklistFace> getPage(int page,
                                              int count,
                                              String filter,
                                              int deviceId) {
        PageWrapper pw = new PageWrapper();
        List<BlacklistFace> list = blacklistFaceMapper.getPage((page - 1) * count, count, filter, deviceId);
        for (BlacklistFace item:list) {
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
        pw.setTotal(blacklistFaceMapper.getCount(filter, deviceId));
        pw.setData(list);
        return pw;
    }
}
