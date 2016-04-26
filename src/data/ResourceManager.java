package data;

import java.util.HashMap;
import java.util.List;







public interface ResourceManager
{
// -------------------------------
// Attributes
// -------------------------------
	public final static String[]initialAvailableCity	= {"Algiers", "New-York", "Paris", "Tokyo", "Minsk", "Moscow"};
	public final static String[]initialAvailableInterest= {"Sport", "Pets", "Blabla", "Something else"};

// -------------------------------
// Generic method to access the database
// -------------------------------
	public void					addEntity			(DataInterface entity);
	public void					removeEntity		(Class<?> tableKey, Object entityKey);
	public DataInterface 		getEntity			(Class<?> tableKey, Object entityKey);
	public List<DataInterface > getEntity			(Class<?> tableKey, HashMap<String, Object> property);
	public void					setEntityProperty	(Class<?> tableKey, Object entityKey, String propertyName, Object propertyValue, boolean removeCurrentValue);
	public boolean				containsEntity		(Class<?> tableKey, Object entityKey);

// -------------------------------
// Wrappers to access specific data base table
// For Alisa ;)
// -------------------------------
	public Data_user	getUser			(String login);
	public void			addUser			(Data_user user);
	public void			removeUser		(String login);
	public void			setUserIP		(String login, String ip);
	public void			addUserFreind	(String login, String freindLogin);
	public boolean		containsUser	(String login);
}