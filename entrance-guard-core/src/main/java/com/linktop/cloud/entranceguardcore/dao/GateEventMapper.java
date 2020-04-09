package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.GateEvent;
import com.linktop.cloud.entranceguardmodel.database.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GateEventMapper {
    @Insert("insert into gate_event_v2(lockCat, srcTs, media, userAliaId, deviceAliaId, receipt) " +
            "values(#{lockCat}, #{srcTs}, #{media}, #{userAliaId}, #{deviceAliaId}, #{receipt}) ")
    int add(@Param("lockCat") String lockCat,
            @Param("srcTs") Integer srcTs,
            @Param("media") String media,
            @Param("userAliaId") String userAliaId,
            @Param("deviceAliaId") String deviceAliaId,
            @Param("receipt") String receipt);

    @Update("update gate_event_v2 set lockCat = #{lockCat}, srcTs = #{srcTs}, " +
            "media = #{media}, userAliaId = #{userAliaId}, deviceAliaId = #{deviceAliaId}, " +
            "receipt = #{receipt} " +
            "where id = #{id}")
    int update(@Param("id") int id,
               @Param("lockCat") String lockCat,
               @Param("srcTs") Integer srcTs,
               @Param("media") String media,
               @Param("userAliaId") String userAliaId,
               @Param("deviceAliaId") String deviceAliaId,
               @Param("receipt") String receipt);

    @Delete("delete * from gate_event_v2  " +
            " where id = #{id}")
    int delete(@Param("id") int id);

    @Select("select ge.id, ge.lockCat, ge.srcTs, ge.media, ge.userAliaId, ge.receipt, " +
            "u.name as userName, u.buildingNum as userBuildingNum, u.unitNum as userUnitNum, " +
            "u.roomNum as userRoomNum, d.buildingNum as deviceBuildingNum, d.unitNum as deviceUnitNum " +
            "group_concat(du.usageDotFn separator ';') as fnList" +
            "from gate_event_v2 ge left join users_v2 u on ge.userAliaId = u.aliaId " +
            "left join device d on ge.deviceAliaId = d.aliaId " +
            "left join dev_ul du on ge.receipt = du.receipt " +
            "where ge.id = #{id}")
    GateEvent get(@Param("id") int id);

    @Select("select ge.id, ge.lockCat, ge.srcTs, ge.media, ge.userAliaId, ge.receipt, " +
            "u.name as userName, u.buildingNum as userBuildingNum, u.unitNum as userUnitNum, " +
            "u.roomNum as userRoomNum, d.buildingNum as deviceBuildingNum, d.unitNum as deviceUnitNum, " +
            "group_concat(du.usageDotFn separator ';') as fnList " +
            "from gate_event_v2 ge left join users_v2 u on ge.userAliaId = u.aliaId " +
            "left join device d on ge.deviceAliaId = d.aliaId " +
            "left join dev_ul du on ge.receipt = du.receipt " +
            "group by du.receipt " +
            "order by ge.srcTs DESC "
            )
    List<GateEvent> getList();


    @Select("<script>" +
            "select ge.id, ge.lockCat, ge.srcTs, ge.media, ge.userAliaId, ge.receipt, " +
            "u.name as userName, u.buildingNum as userBuildingNum, u.unitNum as userUnitNum, " +
            "u.roomNum as userRoomNum, d.buildingNum as deviceBuildingNum, d.unitNum as deviceUnitNum, " +
            "du.fnList as fnList " +
            "from gate_event_v2 ge left join users_v2 u on ge.userAliaId = u.aliaId " +
            "left join device d on ge.deviceAliaId = d.aliaId " +
            "left join " +
            "  (select receipt, group_concat(usageDotFn separator ';') as fnList from dev_ul group by receipt) du " +
            "on ge.receipt = du.receipt " +
            "<where>" +
            "<if test='filter != null'>" +
            "<bind name='search' value=\"'%' + filter + '%'\" />" +
            "<if test='filter != null'>u.buildingNum like #{search}</if>" +
            "<if test='filter != null'>OR u.unitNum like #{search}</if>" +
            "<if test='filter != null'>OR u.roomNum like #{search}</if>" +
            "<if test='filter != null'>OR u.name like #{search}</if>" +
            "<if test='filter != null'>OR d.buildingNum like #{search}</if>" +
            "<if test='filter != null'>OR d.unitNum like #{search}</if>" +
            "</if>" +
            "</where>" +
            "order by ge.srcTs DESC " +
            "limit #{start}, #{count}" +
            "</script>")
    List<GateEvent> getPage(@Param("start") int start,
                       @Param("count") int count,
                       @Param("filter") String filter);


    @Select("<script>"
            + "SELECT COUNT(*) FROM `gate_event_v2`"
            + "</script>")
    int getCount();

    @Delete("<script>"
            + "DELETE * from gate_event_v2 "
            + "<where>"
            + "id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDel(@Param("ids") List<Integer> ids);

}
