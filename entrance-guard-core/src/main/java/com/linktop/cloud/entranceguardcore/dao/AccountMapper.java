package com.linktop.cloud.entranceguardcore.dao;

import com.linktop.cloud.entranceguardmodel.database.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


import java.util.List;

@Mapper
public interface AccountMapper {
    @Insert("insert into account(name, passwd) values(#{name}, #{password})")
    int add(@Param("name") String name, @Param("password") String password);

    @Update("update account set name = #{name}, passwd = #{password} where id = #{id}")
    int update(@Param("name") String name, @Param("password") String password, @Param("id") int  id);

    @Delete("delete from account where id = #{id}")
    int delete(int id);

    @Select("select id, name as name, passwd as password from account where id = #{id}")
    Account findAccount(@Param("id") int id);

    @Select("select id, name as name, passwd as password from account")
    List<Account> findAccountList();

    @Select("select name as name, passwd as password from account where name = #{name} and passwd = #{password}")
    Account findAccount1(@Param("name") String name, @Param("password") String password);

}
