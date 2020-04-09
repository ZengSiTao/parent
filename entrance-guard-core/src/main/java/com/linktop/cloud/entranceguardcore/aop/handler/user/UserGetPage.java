package com.linktop.cloud.entranceguardcore.aop.handler.user;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.commonutils.FilePaths;
import com.linktop.cloud.entranceguardcore.aop.intersector.HandlerIntersector;
import com.linktop.cloud.entranceguardcore.aop.vo.user.UserGetPageVO;
import com.linktop.cloud.entranceguardcore.service.BusiServerApiService;
import com.linktop.cloud.entranceguardmodel.FileDownloadResult;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import com.linktop.cloud.entranceguardmodel.database.User;
import com.linktop.cloud.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.List;

@Component
public class UserGetPage implements HandlerIntersector<UserGetPageVO> {
    private static Logger log = LoggerFactory.getLogger(UserGetPage.class);
    @Autowired
    private UserCommon userCommon;
    @Autowired
    private BusiServerApiService busiServerApiService;
    private User userBeforeModify;

    @Override
    public String getClassName() {
        return UserGetPageVO.class.getName();
    }

    @Override
    public boolean beforeHandle(UserGetPageVO userGetPageVO) {
        Object[] args = (Object[]) userGetPageVO.getArgObj();
        //int page, 0
        //int count, 1
        //String filter, 2
        //boolean black 3
        log.info("UserGetPage beforeHandle, userGetPageVO:{}", JSON.toJSONString(userGetPageVO));

        return false;
    }

    @Override
    public boolean afterHandle(UserGetPageVO userGetPageVO) {
        Object[] args = (Object[]) userGetPageVO.getArgObj();
        PageWrapper<User> pw = (PageWrapper<User>) userGetPageVO.getRetObj();
        log.info("UserGetPage afterHandle, userGetPageVO:{}", JSON.toJSONString(userGetPageVO));
        if(pw != null) {
            List<User> listUser =  pw.getData();
            for (User item:listUser) {
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
                    if(StringUtils.isEmpty(saveName)) {
                        saveName = "noface.jpeg";
                    }
                    item.setWebAccessName(FilePaths.WEB_FACE_FILE_ROOT + URLEncoder.encode(saveName, "utf-8"));
                } catch (Exception e) {
                    log.error("UserService getList exception:" + e.getMessage());
                }
            }
        }

        return false;
    }


}
