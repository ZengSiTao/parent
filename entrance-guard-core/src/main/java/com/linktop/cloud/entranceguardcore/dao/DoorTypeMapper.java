package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.DoorType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DoorTypeMapper {
    @Insert("insert into doortype (rowId,doorId,doorType) values(#{rowId},#{doorId},#{doorType})")
    int addDoorType(DoorType doorType);

    @Select("select * from doortype where doorId = #{doorId} and doorType = #{doorType}")
    DoorType getDoorType(DoorType doorType);

    @Select("select * from doortype where rowId = #{rowId}")
    DoorType getRowId(@Param("rowId") String rowId);
}
