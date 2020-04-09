package com.linktop.cloud.entranceguardcore.controller;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardcore.service.BusiServerApiService;
import com.linktop.cloud.entranceguardmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class BusiServerApiController {
    @Autowired
    BusiServerApiService busiServerApiService;

    @RequestMapping(value = ServiceUrls.BusiServerApi.GETFILETOKEN, method = RequestMethod.POST)
    GetFileUpDownTokenResult getFileUpDownToken(@RequestParam(value = "fn", required = false) String fn,
                                                @RequestParam(value = "m", required = true) int m,
                                                @RequestParam(value = "usage", required = true) String usage) {
        fn = fn.replace(' ','+');
        return busiServerApiService.getFileUpDownToken(fn, m, usage);
    }


    @RequestMapping(value = ServiceUrls.BusiServerApi.FILEUPLOAD, method = RequestMethod.POST)
    FileUploadResult fileUpload(
            @RequestParam(value = "usage", required = false) String usage,
            @RequestPart(value = "file", required = true) MultipartFile file) {
        return busiServerApiService.fileUpload(usage, file);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.FILEDOWNLOAD, method = RequestMethod.GET)
    FileDownloadResult fileDownload(
            @RequestParam(value = "usage", required = true) String usage,
            @RequestParam(value = "fn", required = true) String fn) {
        fn = fn.replace(' ','+');
        return busiServerApiService.fileDownload(usage, fn);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.SHAREFILE, method = RequestMethod.POST)
    BusiCommonResult shareFile(
            @RequestParam(value = "fn", required = true) String fn,
            @RequestParam(value = "inc", required = false) String inc,
            @RequestParam(value = "exc", required = false) String exc) {
        fn = fn.replace(' ','+');
        return busiServerApiService.shareFile(fn, inc, exc);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.REMOVEILE, method = RequestMethod.POST)
    BusiCommonResult rmFile(
            @RequestParam(value = "usage", required = true) String usage,
            @RequestParam(value = "fns", required = true) String fns) {
        return busiServerApiService.rmFile(usage, fns);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.LONGCONNSTATE, method = RequestMethod.GET)
    LongConnStateResult longConnState() {
        return busiServerApiService.longConnState();
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.GETPTUIDLIST, method = RequestMethod.GET)
    GetPtuidListResult getPtuidList() {
        return busiServerApiService.getPtuidList();
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.GETPTUIDCANDIDATELIST, method = RequestMethod.GET)
    GetPtuidListResult getPtuidCandidateList() {
        return busiServerApiService.getPtuidCandidateList();
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.GETCONNSTATE, method = RequestMethod.GET)
    ConnStateResult getConnState(String ptuid) {
        return busiServerApiService.getConnState(ptuid);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.REMOVEILEBYPTUID, method = RequestMethod.POST)
    BusiCommonResult rmFileByPtuid(
            @RequestParam(value = "ptuid", required = true) String ptuid,
            @RequestParam(value = "usage", required = true) String usage,
            @RequestParam(value = "fns", required = true) String fns
    ) {
        return busiServerApiService.rmFileByPtuid(ptuid, usage, fns);
    }
}
