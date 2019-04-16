package com.mood.SqlSession;

import com.mood.config.Function;
import com.mood.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MoodMapperProxy implements InvocationHandler {
    private MoodSession session;
    private MoodSqlSessionFactory sessionFactory;
    private Class clz;

    public MoodMapperProxy(MoodSqlSessionFactory sessionFactory, MoodSession session,Class clz) {
        this.session = session;
        this.sessionFactory = sessionFactory;
        this.clz=clz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(clz.getName());
       // MapperBean readMapper = sessionFactory.getXMLMapperBean("UserMapper.xml");
        MapperBean readMapper = sessionFactory.mappers.get(clz.getName());

        //是否是xml文件对应的接口
        if(!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())){
            return null;
        }
        List<Function> list = readMapper.getList();
        if(list != null && list.size() > 0){
            for (Function function : list) {
                //id是否和接口方法名一样
                if(method.getName().equals(function.getFuncName())){
                    return session.selectOne(function, String.valueOf(args[0]));
                }
            }
        }
        return null;
    }
}
