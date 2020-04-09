package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {
    @Insert("insert into users_v2(aliaId, buildingNum, unitNum, roomNum, type, name, " +
            "identityCardNum, gender, estateId, deviceIds, fnOnServer, blackListType) " +
            "values(#{aliaId}, #{buildingNum}, #{unitNum}, #{roomNum}, #{type}, #{name}, " +
            "#{identityCardNum}, #{gender}, #{estateId}, #{deviceIds}, #{fnOnServer}, #{blackListType})")
    int add(@Param("aliaId") String aliaId,
            @Param("buildingNum") String buildingNum,
            @Param("unitNum") String unitNum,
            @Param("roomNum") String roomNum,
            @Param("type") Integer type,
            @Param("name") String name,
            @Param("identityCardNum") String identityCardNum,
            @Param("gender") Integer gender,
            @Param("estateId") Integer estateId,
            @Param("deviceIds") String deviceIds,
            @Param("fnOnServer") String fnOnServer,
            @Param("blackListType") Integer blackListType);

    @Update("update users_v2 set aliaId = #{aliaId}, buildingNum = #{buildingNum}, " +
            "unitNum = #{unitNum}, roomNum = #{roomNum}, type = #{type}, name = #{name}, " +
            "identityCardNum = #{identityCardNum}, gender = #{gender}, estateId = #{estateId}, " +
            "deviceIds = #{deviceIds}, fnOnServer = #{fnOnServer}, blackListType = #{blackListType} " +
            "where id = #{id}")
    int update(@Param("id") int id,
               @Param("aliaId") String aliaId,
               @Param("buildingNum") String buildingNum,
               @Param("unitNum") String unitNum,
               @Param("roomNum") String roomNum,
               @Param("type") Integer type,
               @Param("name") String name,
               @Param("identityCardNum") String identityCardNum,
               @Param("gender") Integer gender,
               @Param("estateId") Integer estateId,
               @Param("deviceIds") String deviceIds,
               @Param("fnOnServer") String fnOnServer,
               @Param("blackListType") Integer blackListType);

    @Delete("delete u.* from users_v2 u " +
            " where u.id = #{id}")
    int delete(@Param("id") int id);

    @Select("select * from users_v2 where id = #{id}")
    User get(@Param("id") int id);

    @Select("select * from users_v2")
    List<User> getList();


    @Select("<script>" +
            "SELECT * from (" +
                "SELECT u2.*, GROUP_CONCAT(d.buildingNum) as DbuildingNum, " +
                "GROUP_CONCAT(d.unitNum) as DunitNum FROM users_v2 u2 " +
                "left join device d on FIND_IN_SET(d.id, u2.deviceIds) " +
                " GROUP BY u2.id ) t" +
            "<where>" +
            "<if test='black != null and black eq true'>t.blackListType > 0</if>" +
            "<if test='filter != null'>" +
            "<bind name='search' value=\"'%' + filter + '%'\" />" +
            "<if test='black != null'> AND </if>" +
            "<if test='filter != null'>( t.buildingNum like #{search}</if>" +
            "<if test='filter != null'> OR t.unitNum like #{search}</if>" +
            "<if test='filter != null'> OR t.roomNum like #{search}</if>" +
            "<if test='filter != null'> OR t.name like #{search}</if>" +
            "<if test='filter != null'> OR t.identityCardNum like #{search}</if>" +
            "<if test='filter != null'> OR t.DbuildingNum like #{search}</if>" +
            "<if test='filter != null'> OR t.DunitNum like #{search} )</if>" +
            "</if>" +
            "</where> " +
            "limit #{start}, #{count}" +
            "</script>")
    List<User> getPage(@Param("start") int start,
                       @Param("count") int count,
                       @Param("filter") String filter,
                       @Param("black") boolean black);


    @Select("<script>" +
            "SELECT COUNT(*) from (" +
            "SELECT u2.*, GROUP_CONCAT(d.buildingNum) as DbuildingNum, " +
            "GROUP_CONCAT(d.unitNum) as DunitNum FROM users_v2 u2 " +
            "left join device d on FIND_IN_SET(d.id, u2.deviceIds) " +
            " GROUP BY u2.id ) t" +
            "<where>" +
            "<if test='black != null and black eq true'>t.blackListType > 0</if>" +
            "<if test='filter != null'>" +
            "<bind name='search' value=\"'%' + filter + '%'\" />" +
            "<if test='black != null'> AND </if>" +
            "<if test='filter != null'>( t.buildingNum like #{search}</if>" +
            "<if test='filter != null'> OR t.unitNum like #{search}</if>" +
            "<if test='filter != null'> OR t.roomNum like #{search}</if>" +
            "<if test='filter != null'> OR t.name like #{search}</if>" +
            "<if test='filter != null'> OR t.identityCardNum like #{search}</if>" +
            "<if test='filter != null'> OR t.DbuildingNum like #{search}</if>" +
            "<if test='filter != null'> OR t.DunitNum like #{search} )</if>" +
            "</if>" +
            "</where> " +
            "</script>")
    int getCount(
            @Param("filter") String filter,
            @Param("black") boolean black);

    @Delete("<script>"
            + "DELETE u.* from users_v2 u "
            + "<where>"
            + "u.id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDel(@Param("ids") List<Integer> ids);


    @Select("<script>"
            + "SELECT u.* from users_v2 u "
            + "<where>"
            + "u.id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    List<User> batchGet(@Param("ids") List<Integer> ids);

}
