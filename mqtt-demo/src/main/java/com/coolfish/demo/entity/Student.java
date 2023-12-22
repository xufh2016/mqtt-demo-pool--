package com.coolfish.demo.entity;

import com.coolfish.demo.annotation.FieldName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: Student
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student implements Serializable {
    @FieldName("编号")
    private Long id;
    @FieldName("姓名")
    private String name;
    @FieldName("年龄")
    private Integer age;
    @FieldName("数学成绩")
    private Integer math;
    @FieldName("语文成绩")
    private Integer chinese;

}
