package com.mood.SqlSession;

import com.mood.config.Function;

public interface Excutor {
    public <T> T query(Function statement, Object parameter);
}
