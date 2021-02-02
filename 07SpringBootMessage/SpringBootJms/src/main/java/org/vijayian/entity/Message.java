package org.vijayian.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * message
 *
 * @author ViJay
 * @date 2021-01-30
 */
@Data
//>> TODO 需要实现序列化接口.
public class Message implements Serializable {
    private String name;
    private String content;
}
