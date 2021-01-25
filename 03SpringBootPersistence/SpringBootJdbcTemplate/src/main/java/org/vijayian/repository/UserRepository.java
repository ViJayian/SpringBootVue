package org.vijayian.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.vijayian.entity.User;

import java.util.List;

/**
 * repository
 *
 * @author ViJay
 * @date 2021-01-22
 */
@Repository
public class UserRepository {
    /**
     * 增删改 主要使用：update和batchUpdate
     * 查询：query和queryForObject
     * execute 执行任意的sql
     *
     * 执行查询时：需要将列和实体进行对应，如果一致，可以直接使用BeanPropertyRowMapper，不同需要自己实现RowMapper接口
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addUser(User user) {
        jdbcTemplate.update("insert into t_user(username,dbsource) values (?,?)", user.getUsername(), user.getDbsource());
    }

    public void updateUser(User user) {
        jdbcTemplate.update("update t_user set username = ?,dbsource = ? where id = ?",
                user.getUsername(), user.getDbsource(), user.getId());
    }

    public User getUserById(Integer id) {
        return jdbcTemplate.queryForObject("select * from t_user where id = ?", new BeanPropertyRowMapper<>(User.class), id);

    }

    public void deleteUserById(Integer id) {
        jdbcTemplate.update("delete from t_user where id = ?", id);
    }

    public List<User> allUserLists() {
        return jdbcTemplate.query("select * from t_user", new BeanPropertyRowMapper<>(User.class));
    }
}
