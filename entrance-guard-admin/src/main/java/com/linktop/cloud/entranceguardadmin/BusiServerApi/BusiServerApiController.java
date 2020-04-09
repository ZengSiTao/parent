package com.linktop.cloud.entranceguardadmin.BusiServerApi;

import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardclient.feignclient.BusiServerApiClient;
import com.linktop.cloud.entranceguardmodel.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(VersionNumbers.SERVICE_VERSIONS_ADMIN)
@Api(value = "busiserverapi", description = "业务后台服务器API接口")
public class BusiServerApiController {
    @Autowired
    BusiServerApiClient busiServerApiClient;

    @RequestMapping(value = ServiceUrls.BusiServerApi.GETFILETOKEN, method = RequestMethod.POST)
    @ApiOperation(value = "获取文件上传/下载凭据", notes = "获取文件上传/下载凭据")
    GetFileUpDownTokenResult getFileUpDownToken(
            @ApiParam(name = "fn", value = "文件名（下载时需要该参数）", defaultValue = "")
            @RequestParam(value = "fn", required = false) String fn,
            @ApiParam(name = "m", value = "0：上传，1：下载", defaultValue = "")
            @RequestParam(value = "m", required = true) int m,
            @ApiParam(name = "usage", value = "用途（管理端和设备端自行协商）", defaultValue = "")
            @RequestParam(value = "usage", required = true) String usage
    ) {
        return busiServerApiClient.getFileUpDownToken(fn, m, usage);
    }


    @RequestMapping(value = ServiceUrls.BusiServerApi.FILEUPLOAD, method = RequestMethod.POST,
            consumes = MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "文件上传", notes = "文件上传")
    FileUploadResult fileUpload(
            @ApiParam(name = "usage", value = "由调用方自行设定，格式必须满足^[_a-z\\d]{1,10}$", defaultValue = "")
            @RequestParam(value = "usage", required = false) String usage,
            @ApiParam(name = "file", value = "选定文件", defaultValue = "")
            @RequestPart(value = "file") MultipartFile file
    ) {
        return busiServerApiClient.fileUpload(usage, file);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.FILEDOWNLOAD, method = RequestMethod.GET)
    @ApiOperation(value = "文件下载", notes = "文件下载")
    FileDownloadResult fileDownload(
            @ApiParam(name = "usage", value = "由调用方自行设定，格式必须满足^[_a-z\\d]{1,10}$", defaultValue = "")
            @RequestParam(value = "usage", required = true) String usage,
            @ApiParam(name = "fn", value = "上传时获得的文件名", defaultValue = "")
            @RequestParam(value = "fn", required = true) String fn
    ) {
        return busiServerApiClient.fileDownload(usage, fn);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.SHAREFILE, method = RequestMethod.POST)
    @ApiOperation(value = "管理端向多台设备下发人脸，以及黑名单等；即通过file_upload接口上传一个文件后，分发给多台设备",
            notes = "管理端向多台设备下发人脸，以及黑名单等；即通过file_upload接口上传一个文件后，分发给多台设备")
    BusiCommonResult shareFile(
            @ApiParam(name = "fn", value = "从file_upload接口返回的fn参数", defaultValue = "")
            @RequestParam(value = "fn", required = true) String fn,
            @ApiParam(name = "inc", value = "逗号分割的多个设备id，指需要下发的目标设备；与exc参数二选一；" +
                    "即要么发给inc(lude)设备，要么不发给exc(lude)设备", defaultValue = "")
            @RequestParam(value = "inc", required = false) String inc,
            @ApiParam(name = "exc", value = "逗号分割的多个设备id", defaultValue = "")
            @RequestParam(value = "exc", required = false) String exc
    ) {
        return busiServerApiClient.shareFile(fn, inc, exc);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.REMOVEILE, method = RequestMethod.POST)
    @ApiOperation(value = "管理端删除文件", notes = "管理端删除文件")
    BusiCommonResult rmFile(
            @ApiParam(name = "usage", value = "文件上传时的usage", defaultValue = "")
            @RequestParam(value = "usage", required = true) String usage,
            @ApiParam(name = "fns", value = "要删除的多个文件名，用|分割", defaultValue = "")
            @RequestParam(value = "fns", required = true) String fns
    ) {
        return busiServerApiClient.rmFile(usage, fns);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.LONGCONNSTATE, method = RequestMethod.GET)
    @ApiOperation(value = "长连接状态",
            notes = "长连接状态")
    LongConnStateResult longConnState() {
        return busiServerApiClient.longConnState();
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.GETPTUIDLIST, method = RequestMethod.GET)
    @ApiOperation(value = "获取设备ID列表",
            notes = "获取设备ID列表")
    GetPtuidListResult getPtuidList() {
        return busiServerApiClient.getPtuidList();
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.GETPTUIDCANDIDATELIST, method = RequestMethod.GET)
    @ApiOperation(value = "获取可选设备ID列表",
            notes = "获取可选设备ID列表")
    GetPtuidListResult getPtuidCandidateList() {
        return busiServerApiClient.getPtuidCandidateList();
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.GETCONNSTATE, method = RequestMethod.GET)
    @ApiOperation(value = "获取设备在线状态",
            notes = "获取设备在线状态")
    ConnStateResult getConnState(@ApiParam(name = "ptuid", value = "设备ID", defaultValue = "")
                                 @RequestParam(value = "ptuid", required = true) String ptuid) {
        return busiServerApiClient.getConnState(ptuid);
    }

    @RequestMapping(value = ServiceUrls.BusiServerApi.REMOVEILEBYPTUID, method = RequestMethod.POST)
    @ApiOperation(value = "删除指定设备的文件", notes = "删除指定设备的文件")
    BusiCommonResult rmFileByPtuid(
            @ApiParam(name = "ptuid", value = "设备ID", defaultValue = "")
            @RequestParam(value = "ptuid", required = true) String ptuid,
            @ApiParam(name = "usage", value = "文件上传时的usage", defaultValue = "")
            @RequestParam(value = "usage", required = true) String usage,
            @ApiParam(name = "fns", value = "要删除的多个文件名，用|分割", defaultValue = "")
            @RequestParam(value = "fns", required = true) String fns
    ) {
        return busiServerApiClient.rmFileByPtuid(ptuid, usage, fns);
    }
}
