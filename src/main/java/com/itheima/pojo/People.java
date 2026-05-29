package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class People {
    private Integer id;
    private String name;    // 姓名
    private Integer age;    // 年龄
    private String fileUrl; // 文件路径（存数据库）
    private LocalDateTime createTime;
}