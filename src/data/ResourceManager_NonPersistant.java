package data;

import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;









public class ResourceManager_NonPersistant // TODO extends ResourceManager
{
// -------------------------------
// Attributes
// -------------------------------
	private HashMap<String, Data_user>	userSet;
	private LinkedList<String>		availableCity;
	private LinkedList<String>		availableInterest;

// -------------------------------
// Builder
// -------------------------------
	public ResourceManager_NonPersistant()
	{
		this.userSet			= new HashMap<String, Data_user>();
		this.availableCity		= new LinkedList<String>();
		this.availableInterest	= new LinkedList<String>();

		for (String city: ResourceManager.initialAvailableCity)
		{
			this.availableCity.add(city);
		}
		for (String city: ResourceManager.initialAvailableInterest)
		{
			this.availableInterest.add(city);
		}
	}

// -------------------------------
// Local methods
// -------------------------------
	public void addUser(Data_user user)
	{
		String login = user.getKey();

		this.userSet.put(login, user);
	}

	public void removeUser(String login)
	{
		this.userSet.remove(login);
	}

	public Data_user getUser(String login)
	{
		return this.userSet.get(login);
	}

	public void setUserIP(String login, String ip)
	{
		Data_user user = this.userSet.get(login);
		user.setIp(ip);
	}

	public void addUserFreind(String login, String freindLogin)
	{
		Data_user user = this.userSet.get(login);
		user.addFreind(freindLogin);
	}

	public boolean containsUser(String login)
	{
		return this.userSet.containsKey(login);
	}

	public List<Object> getAvailableCity()
	{
		return new LinkedList<Object>(this.availableCity);
	}

	public List<Object> getAvailableInterest()
	{
		return new LinkedList<Object>(this.availableInterest);
	}
}