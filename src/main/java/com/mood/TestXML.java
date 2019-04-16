package com.mood;

import com.mood.SqlSession.MoodSession;
import com.mood.bean.User;
import com.mood.mapper.UserMapper;

public class TestXML {
    public static void main(String[] args) {
        MoodSession moodSession = new MoodSession();
        UserMapper mapper = moodSession.getMapper(UserMapper.class);
        User userById = mapper.getUserById("1");
        System.out.println(userById);

    }
}
