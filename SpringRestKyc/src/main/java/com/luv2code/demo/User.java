package com.allianz.clara.bff;
import java.util.Date;  
public class User   
{  
	
	private Integer policyNumber;  
	private String name;  
	private Date dob;  
	
	public User(Integer id, String name, Date dob)   
	{  
		super();  
		this.policyNumber = policyNumber;  
		this.name = name;  
		this.dob = dob;  
	}  
	
	public Integer getpolicyNumber()   
	{  
		return policyNumber;  
	}  
	public void setpolicyNumber(Integer policyNumber)   
	{  
		this.policyNumber = policyNumber;  
	}  
	public String getName()   
	{  
		return name;  
	}  
	public void setName(String name)   
	{  
		this.name = name;  
	}  
	public Date getDob()   
	{  
		return dob;  
	}  
	public void setDob(Date dob)   
	{  
		this.dob = dob;  
	}  
	@Override  
	public String toString()   
	{  

		return String.format("User [policyNumber=%s, name=%s, dob=%s]", policyNumber, name, dob);  
	}  
}  
