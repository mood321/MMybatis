package com.mood.mapper;


import com.mood.annaotation.Query;
import com.mood.bean.User;

public interface UserMapperAnnao {
	@Query(sql="select * from user where id = ?")
	public User getUserById(String id);
}
