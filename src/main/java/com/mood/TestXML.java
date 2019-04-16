package com.mood;

import com.mood.SqlSession.MoodSession;
import com.mood.SqlSession.MoodSqlSessionFactory;
import com.mood.bean.User;
import com.mood.mapper.UserMapper;
import com.mood.mapper.UserMapperAnnao;

public class TestXML {
    public static void main(String[] args) {
        MoodSqlSessionFactory moodSqlSessionFactory = new MoodSqlSessionFactory("UserMapper.xml", "com.mood.mapper.UserMapperAnnao");

        MoodSession moodSession = moodSqlSessionFactory.getSession();
       /* UserMapper mapper = moodSession.getMapper(UserMapper.class);
        User userById = mapper.getUserById("1");
        System.out.println(userById);*/
        UserMapperAnnao mapper = moodSession.getMapper(UserMapperAnnao.class);
        User userById = mapper.getUserById("1");
        System.out.println(userById);

    }
}
