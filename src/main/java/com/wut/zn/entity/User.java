package com.wut.zn.entity;

import lombok.Data;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "user",
        uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 10)
    private String sex;

    @Column(length = 20)
    private String phone;

    @Column(name = "create_time",
            updatable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}
