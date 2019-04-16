package com.mood.SqlSession;

import com.mood.config.Function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementHandler {
    private MoodSqlSessionFactory sqlSessionFactory;
    private ResultSetHandler resultSetHandler = new ResultSetHandler();

    public StatementHandler(MoodSqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public <T> T query(Function function, Object parameter) {
        Connection conn = this.getConnection();
        try {
            String format = String.format(function.getSql(),
                    Integer.parseInt(String.valueOf(parameter)));
            PreparedStatement p = conn.prepareStatement(
                    format
            );
            p.setObject(1, parameter);
            p.execute();
            return resultSetHandler.handler(p.getResultSet(), function.getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        try {
            Connection connection = sqlSessionFactory.bulider("config.xml");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
