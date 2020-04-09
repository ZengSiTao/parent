package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.DeviceConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceConfigMapper {
    @Insert("insert into dev_cfg(devId, fnOnServer) " +
            "values(#{devId}, #{fnOnServer})")
    int add(@Param("devId") int devId,
            @Param("fnOnServer") String fnOnServer);

    @Update("update dev_cfg set devId = #{devId}, " +
            "fnOnServer = #{fnOnServer} " +
            "where id = #{id}")
    int update(@Param("id") int id,
               @Param("devId") int devId,
               @Param("fnOnServer") String fnOnServer);

    @Update("update dev_cfg dc left join device d on dc.devId = d.id " +
            "set dc.fnOnServer = #{fnOnServer} " +
            "where d.aliaId = #{deviceAliaId}")
    int updateByDeviceAliaId(
               @Param("deviceAliaId") String deviceAliaId,
               @Param("fnOnServer") String fnOnServer);


    @Delete("delete from dev_cfg where id = #{id}")
    int delete(@Param("id") int id);

    @Select("select dc.id, dc.devId as devId, d.aliaId as deviceAliaId, " +
            "dc.fnOnServer as fnOnServer " +
            "from dev_cfg dc left join device d on d.id = dc.devId " +
            "where dc.id = #{id}")
    DeviceConfig get(@Param("id") int id);

    @Select("select dc.id, dc.devId as devId, d.aliaId as deviceAliaId, " +
            "dc.fnOnServer as fnOnServer " +
            "from dev_cfg dc left join device d on d.id = dc.devId " +
            "where dc.devId = #{devId}")
    DeviceConfig getByDevId(@Param("devId") int devId);

    @Select("select dc.id, dc.devId as devId, d.aliaId as deviceAliaId, " +
            "dc.fnOnServer as fnOnServer " +
            "from dev_cfg dc left join device d on d.id = dc.devId " +
            "where d.aliaId = #{deviceAliaId}")
    DeviceConfig getByDeviceAliaId(@Param("deviceAliaId") String deviceAliaId);

    @Select("select dc.id, dc.devId as devId, d.aliaId as deviceAliaId, " +
            "dc.fnOnServer as fnOnServer " +
            "from dev_cfg dc left join device d on d.id = dc.devId")
    List<DeviceConfig> getList();


    @Delete("<script>"
            + "DELETE FROM `dev_cfg`"
            + "<where>"
            + "id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDel(@Param("ids") List<Integer> ids);
}
