package com.mood.SqlSession;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetHandler {
    //解析结果。生成实体
    public <T> T handler(ResultSet resultSet, Object obj) {

        //
        Class clazz = obj.getClass();
        //  Object obj = null;
        try {
             if (resultSet.next()){
                int i = 0;
                for (Field field: clazz.getDeclaredFields()){
                    setValue(obj,field,resultSet,i);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T)obj;


    }

    private void setValue(Object obj, Field field, ResultSet resultSet, int i) throws Exception {

        Method m =  obj.getClass().getMethod("set"+upperCapital(field.getName()),field.getType());

        m.invoke(obj,getResult(field,resultSet));

    }

    private Object getResult(Field field, ResultSet rs) throws SQLException {
        //bean属性的名字必须要和数据库column的名字一样
        Class<?> type = field.getType();
        if("int".equals(type.getName()) ||Integer.class == type ){
            return rs.getInt(field.getName());
        }
        if(String.class == type){
            return rs.getString(field.getName());
        }
        return rs.getString(field.getName());



    }

    private String upperCapital(String name) {

        char[] nameAry = name.toCharArray();
        nameAry[0] -= 32;
        return new String(nameAry);
    }
}
