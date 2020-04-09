package com.linktop.cloud.entranceguardcore.dao;


import com.linktop.cloud.entranceguardmodel.database.HardUser;
import org.apache.ibatis.annotations.*;


/**
 * @创建人 baojielei
 * @创建时间 2020/3/25
 * @描述
 */
@Mapper
public interface HardUserMapper {


    @Insert("insert into harduser(rowId,userCardNumber,cardType,openDoorTimeNum,openDoorLevel,sercret1,sercret2) " +
            "values(#{rowId},#{userCardNumber},#{cardType},#{openDoorTimeNum},#{openDoorLevel},#{sercret1},#{sercret2})")
    int add(HardUser hardUser);

//    int add(@Param("userCardNumber")String userCardNumber,
//                    @Param("cardType")String cardType,
//                    @Param("openDoorTimeNum")String openDoorTimeNum,
//                    @Param("openDoorLevel")String openDoorLevel,
//                    @Param("sercret1")String sercret1,
//                    @Param("sercret2")String sercret2);


    @Select("select * from harduser where userCardNumber = #{userCardNumber} and cardType = #{cardType}")
    HardUser getHardUser(HardUser hardUser);

    @Select("select * from harduser where userCardNumber = #{userCardNumber} and cardType = #{cardType}")
    HardUser getUser(@Param("userCardNumber") String userCardNumber, @Param("cardType") String cardType);

    @Delete( "DELETE from harduser where userCardNumber = #{userCardNumber} and cardType = #{cardType}" )
    int del(@Param("userCardNumber") String userCardNumber, @Param("cardType") String cardType);

    @Select("select * from harduser where rowId = #{rowId}")
    HardUser getId(@Param("rowId")Integer userid);
}
