package org.vijayian.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * user
 *
 * @author ViJay
 * @date 2021-01-22
 */
@Data
@Entity()
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String dbsource;
}
