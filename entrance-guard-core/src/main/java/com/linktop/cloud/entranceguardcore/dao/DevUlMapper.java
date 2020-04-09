package com.linktop.cloud.entranceguardcore.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DevUlMapper {
    @Insert("insert into dev_ul(receipt, usageDotFn) " +
            "values(#{receipt}, #{usageDotFn}) ")
    int add(@Param("receipt") String receipt,
            @Param("usageDotFn") String usageDotFn);

    @Update("update dev_ul set receipt = #{receipt}, usageDotFn = #{usageDotFn} " +
            "where id = #{id}")
    int update(@Param("id") int id,
               @Param("receipt") String receipt,
               @Param("usageDotFn") String usageDotFn);

    @Delete("delete * from dev_ul  " +
            " where id = #{id}")
    int delete(@Param("id") int id);

    @Delete("<script>"
            + "DELETE * from dev_ul "
            + "<where>"
            + "id IN "
            + "<foreach item='item' collection='ids' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</where>"
            + "</script>")
    int batchDel(@Param("ids") List<Integer> ids);

}
