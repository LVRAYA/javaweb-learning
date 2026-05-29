package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptLog {
    private Integer id;
    private String operName;
    private String content;
    private LocalDateTime createTime;
    private Short status;
}
