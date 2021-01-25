package org.vijayian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vijayian.entity.User;

/**
 * repository
 *
 * @author ViJay
 * @date 2021-01-23
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
