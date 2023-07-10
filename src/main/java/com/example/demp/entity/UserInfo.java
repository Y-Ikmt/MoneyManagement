package com.example.demp.entity;
import java.io.Serializable;

import lombok.Data;
/**
 * ユーザー情報 Entity
 */
@Data
public class UserInfo implements Serializable {

    private Long userid;
    private String name;
    private String password;
    private String mokuhyou;
    private String shojikin;
}