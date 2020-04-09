package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.ICCard;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICCardMapper {
    @Insert({"insert into ic_card(serial, userId, deviceIds) " +
            "values(#{serial}, #{userId}, #{deviceIds})"})
    int add(@Param("serial") String serial,
            @Param("userId") int userId,
            @Param("deviceIds") String deviceIds);

    @Update("update ic_card set serial = #{serial}, userId = #{userId}, deviceIds = #{deviceIds} " +
            "where id = #{id}")
    int update(@Param("id") int id,
               @Param("serial") String serial,
               @Param("userId") int userId,
               @Param("deviceIds") String deviceIds);

    @Delete("delete c.*, bc.* from ic_card c left join blacklist_iccard bc on bc.iccardId=c.id" +
            " where c.id = #{id}")
    int delete(@Param("id") int id);

    @Select("select c.*, u.name as userName, u.*" +
            "from ic_card c left join users_v2 u on c.userId=u.id " +
            "where c.id = #{id}")
    ICCard get(@Param("id") int id);

    @Select("select c.id, c.serial, c.userId, c.deviceIds, u.name as userName, " +
            "u.aliaId, u.buildingNum, u.unitNum, u.roomNum, u.type, u.identityCardNum, u.gender, " +
            "u.estateId " +
            "from ic_card c left join users_v2 u on c.userId=u.id ")
    List<ICCard> getList();


    @Select("<script>"
            + "SELECT c.id, c.serial, c.userId, c.deviceIds, u.name as userName, "
            + "u.aliaId, u.buildingNum, u.unitNum, u.roomNum, u.type, u.identityCardNum, u.gender, "
            + "u.estateId "
            + "from ic_card c left join users_v2 u on c.userId=u.id "
            + "<where>"
            + "<if test='filter != null'>"
            + "<bind name='search' value=\"'%' + filter + '%'\" />"
            + "<if test='filter != null'>c.serial like #{search}</if>"
            + "<if test='filter != null'>OR u.name like #{search}</if>"
            + "<if test='filter != null'>OR u.buildingNum like #{search}</if>"
            + "<if test='filter != null'>OR u.unitNum like #{search}</if>"
            + "<if test='filter != null'>OR u.roomNum like #{search}</if>"
            + "<if test='filter != null'>OR u.identityCardNum like #{search}</if>"
            + "</if>"
            + "</where>"
            + "limit #{start}, #{count}"
            + "</script>")
    List<ICCard> getPage(@Param("start") int start,
                               @Param("count") int count,
                               @Param("filter") String filter);

    @Select("<script>"
            + "SELECT COUNT(*) FROM `ic_card` "
            + "</script>")
    int getCount();

    @Delete("<script>"
            + "DELETE c.*, bc.* FROM `ic_card` c left join `blacklist_iccard` bc on bc.iccardId=c.id"
            + "<where>"
            + "c.id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDel(@Param("ids") List<Integer> ids);
}
