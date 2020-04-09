package com.linktop.cloud.entranceguardclient.feignclient;

import com.linktop.cloud.commonutils.ServiceNames;
import com.linktop.cloud.commonutils.ServiceUrls;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.entranceguardmodel.database.Face;
import com.linktop.cloud.entranceguardmodel.PageWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@FeignClient(name = ServiceNames.ENTRANCE_GUARD_CORE)
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
@ApiIgnore
public interface FaceClient {


    @RequestMapping(value = ServiceUrls.Face.LISTALL, method = RequestMethod.GET)
    @ApiOperation(value = "获取人脸列表", notes = "获取人脸列表")
    List<Face> getFaceList();

    @RequestMapping(value = ServiceUrls.Face.PAGE, method = RequestMethod.GET)
    @ApiOperation(value = "获取人脸列表分页", notes = "获取人脸列表分页")
    PageWrapper<Face> getFacePage(@ApiParam(name = "page", value = "第几页", defaultValue = "")
                                      @RequestParam("page") int page,
                                      @ApiParam(name = "count", value = "每页数量", defaultValue = "")
                                      @RequestParam("count") int count,
                                      @ApiParam(name = "filter", value = "查询过滤字符串", defaultValue = "")
                                      @RequestParam("filter") String filter);

    @RequestMapping(value = ServiceUrls.Face.GETBYID, method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取人脸", notes = "根据id获取人脸")
    Face getFaceById(@ApiParam(name = "id", value = "数据库id", defaultValue = "")
                         @RequestParam(value = "id", required = true) int id);

    @RequestMapping(value = ServiceUrls.Face.UPDATEBYID, method = RequestMethod.PUT)
    @ApiOperation(value = "更新人脸", notes = "更新人脸")
    String updateFace(@ApiParam(name = "face", value = "人脸实体", defaultValue = "")
                        @RequestBody(required = true) Face face);

    @RequestMapping(value = ServiceUrls.Face.DELBYID, method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除人脸", notes = "根据id删除人脸")
    String delete(@ApiParam(name = "id", value = "id", defaultValue = "")
                  @RequestParam(value = "id", required = true) int id);

    @RequestMapping(value = ServiceUrls.Face.BATCHDEL, method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除人脸", notes = "根据id删除人脸")
    String batchDelete(@ApiParam(name = "ids", value = "ids", defaultValue = "")
                       @RequestParam(value = "ids", required = true) List<Integer> ids);

    @RequestMapping(value = ServiceUrls.Face.ADD, method = RequestMethod.POST)
    @ApiOperation(value = "添加人脸", notes = "添加人脸")
    String addFace(@ApiParam(name = "face", value = "人脸实体", defaultValue = "")
                     @RequestBody(required = true) Face face);
}
