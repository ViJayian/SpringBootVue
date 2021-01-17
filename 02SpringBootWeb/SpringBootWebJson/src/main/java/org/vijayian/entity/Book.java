package org.vijayian.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * book
 *
 * @author ViJay
 * @date 2021-01-16
 */
@Data
public class Book {
    private String name;

    private String author;

    //>> TODO Json格式化时间注解.
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;

    //>> TODO jackson Json序列化时忽略此属性.
    @JsonIgnore
    //>> TODO Gson不序列化protected.
    protected Integer age;

}
