package org.vijayian.repository1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vijayian.entity.User;

/**
 * user repository
 *
 * @author ViJay
 * @date 2021-01-25
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
