package com.jimzhang.mapper;

import com.jimzhang.entity.UserEntity;
import com.jimzhang.param.UserParam;

import java.util.List;


public interface UserMapper {

	List<UserEntity> getAll();

	List<UserEntity> getList(UserParam userParam);

	int getCount(UserParam userParam);

	UserEntity getOne(Long id);

	void insert(UserEntity user);

	int update(UserEntity user);

	int delete(Long id);

}