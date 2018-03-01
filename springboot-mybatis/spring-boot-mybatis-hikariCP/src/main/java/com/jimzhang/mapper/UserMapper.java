package com.jimzhang.mapper;

import com.jimzhang.entity.UserEntity;
import com.jimzhang.enums.UserSexEnum;
import com.jimzhang.param.UserParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 	@Select 是查询类的注解，所有的查询均使用这个
 	@Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
 	@Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
 	@Update 负责修改，也可以直接传入对象
 	@delete 负责删除

	了解更多属性参考这里：<p>http://www.mybatis.org/mybatis-3/zh/java-api.html</p>

	建议使用#，使用$有 SQL 注入的可能性
 */
public interface UserMapper {
	
	@Select("SELECT * FROM users")
	@Results({
		@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
		@Result(property = "nickName", column = "nick_name")
	})
	List<UserEntity> getAll();

	/**
	 * 动态 SQL：写动态的 SQL，或者需要写复杂的 SQL，全部写在注解中会比较麻烦，使用这种方式
	 *  type ：动态生成 SQL 的类
	    method ： 类中具体的方法名
	 * @param userParam
	 * @return
	 */
	@SelectProvider(type = UserSql.class, method = "getList")
	List<UserEntity> getList(UserParam userParam);

	@SelectProvider(type = UserSql.class, method = "getCount")
	int getCount(UserParam userParam);
	
	@Select("SELECT * FROM users WHERE id = #{id}")
	@Results({
		@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
		@Result(property = "nickName", column = "nick_name")
	})
	UserEntity getOne(Long id);

	@Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
	void insert(UserEntity user);

	@Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(UserEntity user);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);

}