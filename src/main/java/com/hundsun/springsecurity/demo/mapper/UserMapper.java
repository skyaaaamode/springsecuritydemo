package com.hundsun.springsecurity.demo.mapper;
import com.hundsun.springsecurity.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username=#{username}")
    User findByUserName(@Param("username") String username);

}
