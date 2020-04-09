package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.Device;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceMapper {
    @Insert("insert into device(buildingNum, unitNum, macAddr, type, estateId, aliaId) " +
            "values(#{buildingNum}, #{unitNum}, #{macAddr}, #{type}, #{estateId}, #{aliaId})")
    int add(@Param("buildingNum") String buildingNum,
            @Param("unitNum") String unitNum,
            @Param("macAddr") String macAddr,
            @Param("type") int type,
            @Param("estateId") int estateId,
            @Param("aliaId") String aliaId);

    @Update("update device set buildingNum = #{buildingNum}, unitNum = #{unitNum}, " +
            "macAddr = #{macAddr}, type = #{type}, estateId = #{estateId}, aliaId = #{aliaId} " +
            "where id = #{id}")
    int update(@Param("id") int id,
               @Param("buildingNum") String buildingNum,
               @Param("unitNum") String unitNum,
               @Param("macAddr") String macAddr,
               @Param("type") int type,
               @Param("estateId") int estateId,
               @Param("aliaId") String aliaId);

    @Delete("delete d.*, dc.* from device d left join dev_cfg dc on d.id = dc.devId where d.id = #{id}")
    int delete(@Param("id") int id);

    @Select("select d.id, d.buildingNum as buildingNum, d.unitNum as unitNum, " +
            "d.macAddr as macAddr, d.type as type, d.estateId as estateId, d.aliaId, " +
            "dc.id as devcfgId " +
            "from device d left join dev_cfg dc on d.id = dc.devId " +
            "where d.id = #{id}")
    Device get(@Param("id") int id);


    @Select("select d.id, d.buildingNum as buildingNum, d.unitNum as unitNum, " +
            "d.macAddr as macAddr, d.type as type, d.estateId as estateId, d.aliaId, " +
            "dc.id as devcfgId " +
            "from device d left join dev_cfg dc on d.id = dc.devId " +
            "where d.aliaId = #{aliaId}")
    Device getByAliaId(@Param("aliaId") String aliaId);

    @Select("select d.id, d.buildingNum as buildingNum, d.unitNum as unitNum, " +
            "d.macAddr as macAddr, d.type as type, d.estateId as estateId, d.aliaId, " +
            "dc.id as devcfgId " +
            "from device d left join dev_cfg dc on d.id = dc.devId ")
    List<Device> getList();


    @Select("<script>" +
            "select d.id, d.buildingNum as buildingNum, d.unitNum as unitNum, " +
            "d.macAddr as macAddr, d.type as type, d.estateId as estateId, d.aliaId, " +
            "dc.id as devcfgId " +
            "from device d left join dev_cfg dc on d.id = dc.devId " +
            "<where>" +
            "<if test='filter != null'>" +
            "<bind name='search' value=\"'%' + filter + '%'\" />" +
            "<if test='filter != null'>buildingNum like #{search} </if>" +
            "<if test='filter != null'>OR unitNum like #{search} </if>" +
            "<if test='filter != null'>OR macAddr like #{search} </if>" +
            "<if test='filter != null'>OR aliaId like #{search} </if>" +
            "</if>" +
            "</where>" +
            "limit #{start}, #{count}" +
            "</script>")
    List<Device> getPage(@Param("start") int start,
                               @Param("count") int count,
                               @Param("filter") String filter);
    @Select("<script>" +
            "select COUNT(*) from device " +
            "<where>" +
            "<if test='filter != null'>" +
            "<bind name='search' value=\"'%' + filter + '%'\" />" +
            "<if test='filter != null'>buildingNum like #{search} </if>" +
            "<if test='filter != null'>OR unitNum like #{search} </if>" +
            "<if test='filter != null'>OR macAddr like #{search} </if>" +
            "<if test='filter != null'>OR aliaId like #{search} </if>" +
            "</if>" +
            "</where>" +
            "</script>")
    int getCount(@Param("filter") String filter);

    @Delete("<script>" +
            "delete d.*, dc.* from device d left join dev_cfg dc on d.id = dc.devId " +
            "<where>" +
            "d.id IN " +
            "<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</where>" +
            "</script>")
    int batchDel(@Param("ids") List<Integer> ids);

    @Delete("delete d.*, dc.* from device d left join dev_cfg dc " +
            "on d.id = dc.devId " +
            "where d.aliaId = #{aliaId}")
    int deleteByAliaId(@Param("aliaId") String aliaId);

    @Delete("<script>" +
            "delete d.*, dc.* from device d left join dev_cfg dc " +
            "on d.id = dc.devId " +
            "<where>" +
            "d.aliaId IN " +
            "<foreach item='item' collection='aliaIds' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</where>" +
            "</script>")
    int batchDelByAliaId(@Param("aliaIds") List<String> aliaIds);

    @Select("<script>" +
            "select d.id, d.buildingNum as buildingNum, d.unitNum as unitNum, " +
            "d.macAddr as macAddr, d.type as type, d.estateId as estateId, d.aliaId, " +
            "dc.id as devcfgId " +
            "from device d left join dev_cfg dc on d.id = dc.devId " +
            "<where>" +
            "d.id IN " +
            "<foreach item='item' collection='ids' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</where>" +
            "</script>")
    List<Device> batchGet(@Param("ids") List<Integer> ids);

    @Select("<script>" +
            "select d.id, d.buildingNum as buildingNum, d.unitNum as unitNum, " +
            "d.macAddr as macAddr, d.type as type, d.estateId as estateId, d.aliaId, " +
            "dc.id as devcfgId " +
            "from device d left join dev_cfg dc on d.id = dc.devId " +
            "<where>" +
            "d.aliaId IN " +
            "<foreach item='item' collection='aliaIds' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "</where>" +
            "</script>")
    List<Device> batchGetByAliaId(@Param("aliaIds") List<String> aliaIds);

}
