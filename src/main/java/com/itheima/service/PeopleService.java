package com.itheima.service;

import com.itheima.pojo.People;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface PeopleService {
    public void savePeople(String name, Integer age, MultipartFile file);

}
