package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.BlacklistFace;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlacklistFaceMapper {
    @Insert("insert into blacklist_face(estateId, deviceId, faceId) " +
            "values(#{estateId}, #{deviceId}, #{faceId})")
    int add(@Param("estateId") int estateId,
            @Param("deviceId") int deviceId,
            @Param("faceId") int faceId);

    @Update("update blacklist_face set estateId = #{estateId}, deviceId = #{deviceId} " +
            "faceId = #{faceId} " +
            "where id = #{id}")
    int update(@Param("id") int id,
               @Param("estateId") int estateId,
               @Param("deviceId") int deviceId,
               @Param("faceId") int faceId);

    @Delete("delete from blacklist_face where id = #{id}")
    int delete(@Param("id") int id);

    @Select("select bf.*, f.userId, f.fnOnServer, u.aliaId as userAliaId, u.buildingNum, u.unitNum, "
            + "u.roomNum, u.type, u.name as userName, u.identityCardNum, u.gender "
            + "from blacklist_face bf left join face f on bf.faceId = f.id "
            + "left join users_v2 u on f.userId = u.id "
            + "where bf.id = #{id}")
    BlacklistFace get(@Param("id") int id);

    @Select("select bf.*, f.userId, f.fnOnServer, u.aliaId as userAliaId, u.buildingNum, u.unitNum, "
            + "u.roomNum, u.type, u.name as userName, u.identityCardNum, u.gender "
            + "from blacklist_face bf left join face f on bf.faceId = f.id "
            + "left join users_v2 u on f.userId = u.id "
            + "where bf.deviceId = #{deviceId}")
    List<BlacklistFace> getList(@Param("deviceId") int deviceId);


    @Select("<script>"
            + "select bf.*, f.userId, f.fnOnServer, u.aliaId as userAliaId, u.buildingNum, u.unitNum, "
            + "u.roomNum, u.type, u.name as userName, u.identityCardNum, u.gender "
            + "from blacklist_face bf left join face f on bf.faceId = f.id "
            + "left join users_v2 u on f.userId = u.id "
            + "<where>"
            + "bf.deviceId = #{deviceId} "
            + "<if test='filter != null'>"
            + "<bind name='search' value=\"'%' + filter + '%'\" />"
            + "And (u.name like #{search} "
            + "OR u.buildingNum like #{search}"
            + "OR u.unitNum like #{search}"
            + "OR u.roomNum like #{search}"
            + "OR u.identityCardNum like #{search})"
            + "</if>"
            + "</where>"
            + "limit #{start}, #{count}"
            + "</script>")
    List<BlacklistFace> getPage(@Param("start") int start,
                                @Param("count") int count,
                                @Param("filter") String filter,
                                @Param("deviceId") int deviceId);

    @Select("<script>"
            + "select count(*) from ("
                + "select bf.*, f.userId, f.fnOnServer, u.aliaId as userAliaId, u.buildingNum, u.unitNum, "
                + "u.roomNum, u.type, u.name as userName, u.identityCardNum, u.gender "
                + "from blacklist_face bf left join face f on bf.faceId = f.id "
                + "left join users_v2 u on f.userId = u.id "
                + "<where>"
                + "bf.deviceId = #{deviceId} "
                + "<if test='filter != null'>"
                + "<bind name='search' value=\"'%' + filter + '%'\" />"
                + "And (u.name like #{search} "
                + "OR u.buildingNum like #{search}"
                + "OR u.unitNum like #{search}"
                + "OR u.roomNum like #{search}"
                + "OR u.identityCardNum like #{search})"
                + "</if>"
                + "</where>"
            + ") t"
            + "</script>")
    int getCount(@Param("filter") String filter,
                 @Param("deviceId") int deviceId);

    @Delete("<script>"
            + "DELETE FROM `blacklist_face`"
            + "<where>"
            + "id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDel(@Param("ids") List<Integer> ids);
}
