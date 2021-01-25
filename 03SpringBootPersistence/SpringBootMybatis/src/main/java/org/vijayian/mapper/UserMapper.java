package org.vijayian.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.vijayian.entity.User;

import java.util.List;

/**
 * userMapper
 *
 * @author ViJay
 * @date 2021-01-23
 */
//>> TODO 方式1：在mapper接口上使用@Mapper指定接口是一个mybatis的mapper.
@Mapper
public interface UserMapper {
    int addUser(User user);

    int deleteUserById(Integer id);

    int updateUser(User user);

    User findUserById(Integer id);

    List<User> findAll();
}
