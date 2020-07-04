package com.luoyenot.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author luoyenot
 * @create 2020-05-15-9:37
 */
@Table(name="comment")
@Data
public class Comment {

    @Id
    private Integer id;

    private String commentKey;

    private Integer commentValue;

}
