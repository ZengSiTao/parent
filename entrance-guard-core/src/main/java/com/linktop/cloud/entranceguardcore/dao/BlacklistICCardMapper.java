package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.BlacklistICCard;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlacklistICCardMapper {
    @Insert("insert into blacklist_iccard(estateId, deviceId, iccardId) " +
            "values(#{estateId}, #{deviceId}, #{iccardId})")
    int add(@Param("estateId") int estateId,
            @Param("deviceId") int deviceId,
            @Param("iccardId") int iccardId);

    @Update("update blacklist_iccard set estateId = #{estateId}, deviceId = #{deviceId} " +
            "iccardId = #{iccardId} " +
            "where id = #{id}")
    int update(@Param("id") int id,
               @Param("estateId") int estateId,
               @Param("deviceId") int deviceId,
               @Param("iccardId") int iccardId);

    @Delete("delete from blacklist_iccard where id = #{id}")
    int delete(@Param("id") int id);

    @Select("SELECT bc.*, c.serial, c.userId, u.aliaId as userAliaId, u.buildingNum, u.unitNum, "
            + "u.roomNum, u.type, u.name as userName, u.identityCardNum, u.gender "
            + "from blacklist_iccard bc left join ic_card c on bc.iccardId = c.id "
            + "left join users_v2 u on c.userId = u.id and bc.estateId = u.estateId "
            + "where bc.id = #{id}")
    BlacklistICCard get(@Param("id") int id);

    @Select("SELECT bc.*, c.serial, c.userId, u.aliaId as userAliaId, u.buildingNum, u.unitNum, "
            + "u.roomNum, u.type, u.name as userName, u.identityCardNum, u.gender "
            + "from blacklist_iccard bc left join ic_card c on bc.iccardId = c.id "
            + "left join users_v2 u on c.userId = u.id and bc.estateId = u.estateId "
            + "where bc.deviceId = #{deviceId}")
    List<BlacklistICCard> getList(int deviceId);


    @Select("<script>"
            + "SELECT bc.*, c.serial, c.userId, u.aliaId as userAliaId, u.buildingNum, u.unitNum, "
            + "u.roomNum, u.type, u.name as userName, u.identityCardNum, u.gender "
            + "from blacklist_iccard bc left join ic_card c on bc.iccardId = c.id "
            + "left join users_v2 u on c.userId = u.id and bc.estateId = u.estateId "
            + "<where> "
            + "bc.deviceId = #{deviceId}"
            + "<if test='filter != null'>"
            + "<bind name='search' value=\"'%' + filter + '%'\" />"
            + "AND ("
                + "c.serial like #{search} "
                + " OR u.name like #{search} "
                + " OR u.buildingNum like #{search} "
                + " OR u.unitNum like #{search} "
                + " OR u.roomNum like #{search} "
                + " OR u.identityCardNum like #{search} "
            + ")"
            + "</if>"
            + "</where>"
            + "limit #{start}, #{count}"
            + "</script>")
    List<BlacklistICCard> getPage(@Param("start") int start,
                           @Param("count") int count,
                           @Param("filter") String filter,
                           @Param("deviceId") int deviceId);

    @Select("<script>"
            + "SELECT COUNT(*) from ("
                + "SELECT bc.*, c.serial, c.userId, u.aliaId as userAliaId, u.buildingNum, u.unitNum, "
                + "u.roomNum, u.type, u.name as userName, u.identityCardNum, u.gender "
                + "from blacklist_iccard bc left join ic_card c on bc.iccardId = c.id "
                + "left join users_v2 u on c.userId = u.id and bc.estateId = u.estateId "
                + "<where>"
                + "bc.deviceId = #{deviceId} "
                + "<if test='filter != null'>"
                + "<bind name='search' value=\"'%' + filter + '%'\" />"
                + "AND ("
                + "c.serial like #{search} OR u.name like #{search} "
                + " OR u.buildingNum like #{buildingNum} "
                + " OR u.unitNum like #{unitNum} "
                + " OR u.roomNum like #{roomNum} "
                + " OR u.identityCardNum like #{identityCardNum} "
                + ")"
                + "</if>"
                + "</where>"
            + ") t"
            + "</script>")
    int getCount(@Param("filter") String filter,
                 @Param("deviceId") int deviceId);

    @Delete("<script>"
            + "DELETE FROM `blacklist_iccard`"
            + "<where>"
            + "id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDel(@Param("ids") List<Integer> ids);
}
