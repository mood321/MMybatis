package com.mood.SqlSession;

import com.mood.config.Function;
import com.mood.config.MapperBean;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MoodSession {
    private Excutor excutor ;

    private MoodSqlSessionFactory myConfiguration;

    public MoodSession(MoodSqlSessionFactory myConfiguration) {
        this.myConfiguration = myConfiguration;
        this.excutor=new MoodExcutor(myConfiguration);
    }

    public <T> T selectOne(Function statement, Object parameter) {
        return excutor.query(statement, parameter);
    }

    public <T> T getMapper(Class cla) {
        return (T) Proxy.newProxyInstance(cla.getClassLoader(), new Class[]{cla}, new MoodMapperProxy(myConfiguration, this, cla));
    }
}
