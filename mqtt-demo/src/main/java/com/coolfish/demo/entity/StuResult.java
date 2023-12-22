package com.coolfish.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * @className: StuResult
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/22
 */
@Data

public class StuResult {
    private List<Student> students;

    private List<Header> headers;
}
