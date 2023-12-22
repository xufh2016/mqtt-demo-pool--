package com.coolfish.demo.controller;

import com.coolfish.demo.annotation.FieldName;
import com.coolfish.demo.entity.Header;
import com.coolfish.demo.entity.StuResult;
import com.coolfish.demo.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: TestController
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/22
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public Object getStu() {
        Student student = new Student();
        student.setId(1L);
        student.setName("zhangsan");


        return student;
    }

    /**
     * 动态返回表头及内容
     *
     * @return
     */
    @GetMapping("/mix")
    public Object getMix() throws IllegalAccessException {
        /**
         * 45行到65行模拟从数据库里面查出数据
         */
        Student student = new Student();
        student.setId(1L);
        student.setName("zhangsan");
        student.setAge(20);
        Student student1 = new Student();
        student1.setId(2L);
        student1.setName("lisi");
        student1.setAge(18);
        Student student2 = new Student();
        student2.setId(3L);
        student2.setName("wangwu");
        student2.setAge(20);
        Student student3 = new Student();
        student3.setId(4L);
        student3.setName("zhangsan");
        student3.setAge(20);
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student1);
        list.add(student2);
        list.add(student3);

        Student student4 = list.get(0);
        Class<? extends Student> student4Class = student4.getClass();
        Field[] declaredFields = student4Class.getDeclaredFields();
        ArrayList<Header> list1 = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object o = declaredField.get(student4);
            if (o != null) {
                Header header = new Header();
                String name = declaredField.getName();
                header.setHeaderCode(name);
                FieldName annotation = declaredField.getAnnotation(FieldName.class);
                String value = annotation.value();
                header.setHeaderName(value);
                list1.add(header);
            }
        }
        StuResult stuResult = new StuResult();
        stuResult.setHeaders(list1);
        stuResult.setStudents(list);
        return stuResult;
    }
}
