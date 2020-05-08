package com.real.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.real.springboot.domain.User;

@Mapper
public interface UserMapper {

	@Insert("insert students(id,name) values(#{id},#{userName})")
	void insert(User u);
	
	@Select("select id,name as user_name from students where id=#{id} ")
	User findById(@Param("id")String id);
	
	//注：方法名和要UserMapper.xml中的id一致
	List<User> query(@Param("userName")String userName);
	
}
