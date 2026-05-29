package com.itheima.service.impl;

import com.itheima.mapper.DeptLogMapper;
import com.itheima.service.DeptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
@Service
public class DeptLogServiceImpl implements DeptLogService {
    @Autowired
    DeptLogMapper deptLogmapper;
    @Override
    public void saveDeptLog(Integer id, String content) {
        deptLogmapper.insertDeptLog(id, content);
    }
}
