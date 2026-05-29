package com.itheima.service.impl;

import com.itheima.mapper.PeopleMapper;
import com.itheima.pojo.People;
import com.itheima.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    PeopleMapper peopleMapper;
    @Override
    public void savePeople(String name, Integer age, MultipartFile file) {
        People people = new People();
        people.setName(name);
        people.setAge(age);
        people.setFileUrl(file.getOriginalFilename());
        peopleMapper.savePeople(people);
    }
}
