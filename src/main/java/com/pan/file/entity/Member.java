package com.pan.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 对象类
 */
@Data
public class Member {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    String username;
    String password;

    public Member() {}
    public Member(String username,String password) {
        this.username = username;
        this.password = password;
    }
}
