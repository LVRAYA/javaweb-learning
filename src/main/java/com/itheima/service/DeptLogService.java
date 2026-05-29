package com.itheima.service;

import com.itheima.pojo.DeptLog;
import org.springframework.stereotype.Service;


public interface DeptLogService {
    public void saveDeptLog(Integer id, String content);
}
