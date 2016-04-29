package data;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;






public class ResourceManager_DataStoreService implements ResourceManager
{
// -------------------------------
// Attributes
// -------------------------------
	public static final Class<?>				KEY_USER				= Data_user.class;
	public static final Class<?>				KEY_AVAILABLE_CITY		= Data_city.class;
	public static final Class<?>				KEY_AVAILABLE_INTEREST	= Data_interest.class;
	public static final int						NBR_INITIAL_RANDOM_USER	= 10;

	private static final EntityManagerFactory	entityManagerFactory	= Persistence.createEntityManagerFactory("transactions-optional");
	private static boolean						isFirstInstance			= false;

	private final Object lock = new Object();

// -------------------------------
// Builder
// -------------------------------
	public ResourceManager_DataStoreService()
	{

		synchronized (lock)
		{
			for (String city: ResourceManager.initialAvailableCity)
			{
				Data_city entity = new Data_city(city);
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				entityManager.persist(entity);
				entityManager.close();
			}
			for (String interest: ResourceManager.initialAvailableInterest)
			{
				Data_interest entity = new Data_interest(interest);
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				entityManager.persist(entity);
				entityManager.close();
			}
			if (isFirstInstance)
			{
System.out.println("---------------- add all users");
				isFirstInstance = false;
				for (Data_user entity: Data_user.getRandomUserList(NBR_INITIAL_RANDOM_USER))
				{
					EntityManager entityManager = entityManagerFactory.createEntityManager();
					entityManager.persist(entity);
					entityManager.close();
System.out.println("\t User = " + entity);
				}
System.out.println("---------------- add all users");
			}
		}
	}

// -------------------------------
// Generic method to access the database
// -------------------------------
	@Override
	public void addEntity (DataInterface entity)
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		synchronized (lock)
		{
			entityManager.persist(entity);
			entityManager.close();
		}
	}

	@Override
	public void removeEntity(Class<?> tableKey, Object entityKey)
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		boolean found = false;

		try
		{
			synchronized (lock)
			{
				DataInterface entityToRemove = (DataInterface) entityManager.find(tableKey, entityKey);
				entityManager.remove(entityToRemove);
				found = true;
			}
		}
		finally
		{
			entityManager.close();
			if (!found)
			{
				throw new RuntimeException();
			}
		}
	}

	@Override
	public DataInterface getEntity(Class<? > tableKey, Object entityKey)
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		DataInterface result = null;

		try
		{
			synchronized (lock)
			{
				result = (DataInterface) entityManager.find(tableKey, entityKey);
			}
		}
		finally
		{
			entityManager.close();
		}
		return result;
	}

	/**
	 * Generic methods to access data base.</br>
	 * Generates a non relational query looking in the
	 *  table "tableKey" for the entries set at the values given by "conditionValue".</br>
	 *  The data base is pointed by entityManager.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataInterface> getEntity (Class<? > tableKey, HashMap<String, Object> property)
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String queryStr = "select t from " + tableKey.getCanonicalName() + " t";
		Query q = null;

		if ((property != null) && (!property.isEmpty()))
		{
			boolean isFirst = true;
			for (String entityKey: property.keySet())
			{
				if (isFirst)
				{
					queryStr += " where ";
				}
				else
				{
					queryStr += " and ";
				}
				queryStr += "t." + entityKey + " = :" + entityKey;
			}
			q = entityManager.createQuery(queryStr);
			for (String entityKey: property.keySet())
			{
				q.setParameter(entityKey, property.get(entityKey));
			}
		}
		else
		{
			q = entityManager.createQuery(queryStr);
		}
		synchronized (lock)
		{
			return q.getResultList();
		}
	}

	@Override
	public void setEntityProperty(Class<? > tableKey, Object entityKey, String propertyName, Object propertyValue, boolean removeCurrentValue)
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		boolean found = false;

		try
		{
			synchronized (lock)
			{
				DataInterface entityToChange = (DataInterface) entityManager.find(tableKey, entityKey);
				if (removeCurrentValue)
				{
					entityToChange.setProperty(propertyName, propertyValue);
				}
				else
				{
					entityToChange.addProperty(propertyName, propertyValue);
				}
				found = true;
			}
		}
		finally
		{
			entityManager.close();
			if (!found)
			{
				throw new RuntimeException();
			}
		}
	}

	@Override
	public boolean containsEntity(Class<?> tableKey, Object entityKey)
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		boolean found = false;

		try
		{
			synchronized (lock)
			{
				Object res = entityManager.find(tableKey, entityKey);
				found = (res != null);
			}
		}
		finally
		{
			entityManager.close();
		}

		return found;
	}


// -------------------------------
// Wrappers to access specific data base table
// For Alisa ;)
// -------------------------------
	@Override
	public Data_user getUser(String login)
	{
		return (Data_user) this.getEntity(KEY_USER, login);
	}

	@Override
	public void addUser(Data_user user)
	{
		this.addEntity(user);
	}

	@Override
	public void removeUser (String login)
	{
		this.removeEntity(KEY_USER, login);
	}

	@Override
	public void setUserIP (String login, String ip)
	{
		this.setEntityProperty(KEY_USER, login, "ip", ip, true);
	}

	@Override
	public void addUserFreind(String login, String freindLogin)
	{
		Data_user currentUser = getUser(login);
		if (!currentUser.isFreind(freindLogin))
		{
			this.setEntityProperty(KEY_USER, login, "freind", freindLogin, false);
		}
	}

	@Override
	public boolean containsUser (String login)
	{
		return this.containsEntity(KEY_USER, login);
	}
}
