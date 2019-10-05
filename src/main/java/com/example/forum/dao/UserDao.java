package com.example.forum.dao;

import com.example.forum.entity.User;
import com.example.forum.entity.dto.GithubUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    @Insert("insert into user (name,account_id,token,create_time,modify_time)" +
            "values(#{name},#{accountId},#{token},#{createTime},#{modifyTime})")
     void insert(User user);
}
