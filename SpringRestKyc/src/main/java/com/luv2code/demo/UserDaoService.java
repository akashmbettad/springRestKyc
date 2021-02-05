package com.allianz.clara.bff;
import java.util.ArrayList;  
import java.util.Date;  
import java.util.List;  
import org.springframework.stereotype.Component;  

@Component  
public class UserDaoService   
{  
 
	private static List<User> users=new ArrayList<>();  
	 
	static  
	{  
		//adding users to the list  
		users.add(new User(0001, "abc", new Date()));  
		users.add(new User(0002, "def", new Date()));  
	}  
 
	public List<User> findAll()  
	{  
		return users;  
	}  
 
	public User save(User user)  
	{  

		users.add(user);  
		return user;  
	}  

	public User findOne(int id)  
	{  
		for(User user:users)  
		{  
			if(user.getpolicyNumber()==id)  
				return user;  
		}  
		return null;  
	}  
} 