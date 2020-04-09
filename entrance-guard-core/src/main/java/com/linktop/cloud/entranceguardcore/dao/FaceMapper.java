package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.Face;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FaceMapper {
    @Insert("insert into face(userId, deviceIds, fnOnServer) " +
            "values(#{userId}, #{deviceIds}, #{fnOnServer})")
    int add(@Param("userId") int userId,
            @Param("deviceIds") String deviceIds,
            @Param("fnOnServer") String fnOnServer);

    @Update("update face set userId = #{userId}, deviceIds = #{deviceIds}, " +
            "fnOnServer = #{fnOnServer} " +
            "where id = #{id}")
    int update(@Param("id") int id,
               @Param("userId") int userId,
               @Param("deviceIds") String deviceIds,
               @Param("fnOnServer") String fnOnServer);

    @Delete("delete f.*, bf.* from face f left join blacklist_face bf on bf.faceId=f.id where f.id = #{id}")
    int delete(@Param("id") int id);

    @Select("select f.*, u.name as userName, u.buildingNum, u.unitNum, u.roomNum, u.identityCardNum " +
            "from face f left join users_v2 u on f.userId = u.id" +
            " where f.id = #{id}")
    Face get(@Param("id") int id);

    @Select("select f.*, u.name as userName, u.buildingNum, u.unitNum, u.roomNum, u.identityCardNum " +
            "from face f left join users_v2 u on f.userId = u.id" +
            " where u.id = #{userId}")
    Face getByUserId(@Param("userId") int userId);

    @Select("select f.*, u.name as userName, u.* " +
            "from face f left join users_v2 u on f.userId = u.id")
    List<Face> getList();


    @Select("<script>"
            + "SELECT f.*, u.name as userName, u.buildingNum, u.unitNum, u.roomNum, u.identityCardNum"
            + " from face f left join users_v2 u on f.userId = u.id"
            + "<where>"
            + "<if test='filter != null'>"
            + "<bind name='search' value=\"'%' + filter + '%'\" />"
            + "<if test='filter != null'>OR u.name like #{search}</if>"
            + "<if test='filter != null'>OR u.buildingNum like #{search}</if>"
            + "<if test='filter != null'>OR u.unitNum like #{search}</if>"
            + "<if test='filter != null'>OR u.roomNum like #{search}</if>"
            + "<if test='filter != null'>OR u.identityCardNum like #{search}</if>"
            + "</if>"
            + "</where>"
            + "limit #{start}, #{count}"
            + "</script>")
    List<Face> getPage(@Param("start") int start,
                                  @Param("count") int count,
                                  @Param("filter") String filter);

    @Select("<script>"
            + "SELECT COUNT(*) FROM `face`"
            + "</script>")
    int getCount();

    @Delete("<script>"
            + "DELETE f.*, bf.* FROM `face` f left join `blacklist_face` bf on bf.faceId=f.id"
            + "<where>"
            + "f.id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDel(@Param("ids") List<Integer> ids);
}
