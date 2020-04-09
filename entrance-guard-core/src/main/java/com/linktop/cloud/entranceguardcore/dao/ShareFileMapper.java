package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.ShareFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShareFileMapper {
    @Insert("insert into share_file(fileUsage, opType, deviceAliaId, userAliaId, fnOnServer, needDel) " +
            "values(#{fileUsage}, #{opType}, #{deviceAliaId}, #{userAliaId}, #{fnOnServer}, #{needDel})")
    int add(@Param("fileUsage") String usage,
            @Param("opType") String opType,
            @Param("deviceAliaId") String deviceAliaId,
            @Param("userAliaId") String userAliaId,
            @Param("fnOnServer") String fnOnServer,
            @Param("needDel") boolean needDel);

    @Update("update share_file set needDel = #{needDel} " +
            "where id = #{id}")
    int updateNeedDel(@Param("id") int id,
               @Param("needDel") boolean needDel);

    @Update("update share_file set needDel = #{needDel} " +
            " where fnOnServer = #{fnOnServer} and deviceAliaId = #{deviceAliaId} ")
    int updateNeedDelByDeviceAliaIdFn(@Param("deviceAliaId") String deviceAliaId,
                                      @Param("fnOnServer") String fnOnServer,
                                      @Param("needDel") boolean needDel);

    @Delete("delete from share_file  " +
            " where id = #{id}")
    int delete(@Param("id") int id);

    @Delete("<script>"
            + "DELETE from share_file "
            + "<where>"
            + "id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDel(@Param("ids") List<Integer> ids);

    @Delete("delete from share_file  " +
            " where fnOnServer = #{fnOnServer}")
    int deleteByFn(@Param("fnOnServer") String fnOnServer);

    @Delete("delete from share_file  " +
            " where fnOnServer = #{fnOnServer} and deviceAliaId = #{deviceAliaId} ")
    int deleteByDeviceAliaIdFn(@Param("deviceAliaId") String deviceAliaId,
            @Param("fnOnServer") String fnOnServer);

    @Select("select COUNT(*) from share_file  " +
            " where fnOnServer = #{fnOnServer} ")
    int getFnCount(@Param("fnOnServer") String fnOnServer);

    @Delete("<script>"
            + "DELETE from share_file "
            + "<where>"
            + "fnOnServer IN "
            + "<foreach item='item' collection='fnOnServers' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDelByFn(@Param("fnOnServer") List<String> fnOnServers);

    @Select("<script>" +
            "select sf.id, sf.fileUsage, sf.opType, sf.deviceAliaId, sf.userAliaId, " +
            "sf.fnOnServer, sf.needDel " +
            "from share_file sf " +
            "<where>" +
            "1 = 1" +
            "<if test='fileUsage != null'> and sf.fileUsage = #{fileUsage} </if>" +
            "<if test='deviceAliaId != null'> and sf.deviceAliaId = #{deviceAliaId} </if>" +
            "<if test='userAliaId != null'> and sf.userAliaId = #{userAliaId} </if>" +
            "</where>" +
            "</script>")
    List<ShareFile> getList(@Param("fileUsage") String usage,
                            @Param("deviceAliaId") String deviceAliaId,
                            @Param("userAliaId") String userAliaId);


    @Select("<script>" +
            "select sf.id, sf.fileUsage, sf.opType, sf.deviceAliaId, sf.userAliaId, " +
            "sf.fnOnServer, sf.needDel " +
            "from share_file sf " +
            "<where>" +
            "sf.needDel = #{bNeedDel} " +
            "<if test='fileUsage != null'> and sf.fileUsage = #{fileUsage} </if>" +
            "<if test='deviceAliaId != null'> and sf.deviceAliaId = #{deviceAliaId} </if>" +
            "</where>" +
            "</script>")
    List<ShareFile> getNeedDelList(@Param("fileUsage") String usage,
                                   @Param("deviceAliaId") String deviceAliaId,
                                   @Param("bNeedDel") boolean bNeedDel);

    @Select("<script>" +
            "select sf.id, sf.fileUsage, sf.opType, sf.deviceAliaId, sf.userAliaId, " +
            "sf.fnOnServer, sf.needDel " +
            "from share_file sf " +
            "<where>" +
            "sf.fileUsage = #{fileUsage} " +
            "</where>" +
            "group by sf.fnOnServer " +
            "</script>")
    List<ShareFile> getListGroupByFn(String fileUsage);

}
