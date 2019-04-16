package com.mood.SqlSession;

import com.mood.bean.User;
import com.mood.config.Function;
import com.mood.config.MapperBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoodExcutor implements Excutor {
    private MoodSqlSessionFactory xmlConfiguration = new MoodSqlSessionFactory();

    @Override
    public <T> T query(Function sql, Object parameter) {
        StatementHandler handler = new StatementHandler(xmlConfiguration);
        return  handler.query(sql, parameter);
    }


}
