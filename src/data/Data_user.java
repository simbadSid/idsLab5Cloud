package data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;






/**
 * Model class: used to store an entry of the user table into the data base
 * @author kassuskley
 *
 */



@Entity
public class Data_user implements DataInterface
{
// -------------------------------
// Attributes
// -------------------------------
	public static final String[] attributesToPrint = {"login", "name", "surname", "age", "location", "ip", "interests"};
	public static final String[] attributesToStore	= {"login", "password", "name", "surname", "age", "location", "ip", "interests", "freind"};

	@Id
	@PrimaryKey
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//TODO ********************** Do not change the name of the attributes ***************************
//TODO ********************** Do not change the name of the attributes ***************************
//TODO ********************** Do not change the name of the attributes ***************************
	private String				login		= "";
	private String				password	= "";
	private String				name		= "";
	private String				surname		= "";
	private	Long				age			= (long) -1;
	private String				location	= "";
	private String				ip			= "";
	private ArrayList<String>	interests	= new ArrayList<String>();;
	private ArrayList<String>	freind		= new ArrayList<String>();;

// -------------------------------
// Builder
// -------------------------------
	private Data_user()
	{
		
	}

	public Data_user(String login, String password, String name, String surname, int age, String location, String[] interests)
	{
		this.login		= new String(login);
		this.password	= new String(password);
		this.name		= new String(name);
		this.surname	= new String(surname);
		this.age		= new Long(age);
		this.location	= new String(location);
		this.ip			= null;
		this.freind		= new ArrayList<String>();
		this.interests	= new ArrayList<String>();
		for (String str:interests)
			this.interests.add(str);
	}

	public static Data_user newInstenceToStore(Map<String, Object> attributes)
	{
		Data_user		res			= new Data_user();
		int			nbrAttribute= attributes.keySet().size();

		for (String attributeName: attributesToStore)
		{
			try
			{
				Field field = Data_user.class.getDeclaredField(attributeName);
				Object attribute = attributes.get(attributeName);
				if (attribute != null)
				{
					field.set(res, attribute);
				}
				nbrAttribute --;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}

		if (nbrAttribute > 0)
		{
			throw new RuntimeException("Extra attributes: " + attributes.keySet());
		}
		return res;
	}

// -------------------------------
// Getter
// -------------------------------
	public String				getKey			(){return new String(this.login);}
	public String				get_login		(){return new String(this.login);}
	public String				get_password	(){return new String(this.password);}
	public String				get_name		(){return new String(this.name);}
	public String				get_surname		(){return new String(this.surname);}
	public Long					get_age			(){return this.age;}
	public String				get_location	(){return new String(this.location);}
	public String				get_ip			(){return (this.ip == null)? null : new String(this.location);}
	public LinkedList<String>	get_interests	(){return new LinkedList<String>(this.interests);}
	public LinkedList<String>	get_freind		(){return new LinkedList<String>(this.freind);}
	public String				get_pictureName	()
	{
//TODO change by a check in the picture directory
		return StaticResourceManager.defaultPicture;
	}

	public HashMap<String, Object> getAttributesToStore()
	{
		HashMap<String, Object> res = new HashMap<String, Object> ();

		for (String attributeName: attributesToStore)
		{
			try
			{
				Field field = Data_user.class.getDeclaredField(attributeName);
				res.put(field.getName(), field.get(this));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(0);
			}
		}

		return res;
	}

	public boolean isFreind(String freindLogin)
	{
		return this.freind.contains(freindLogin);
	}

// -------------------------------
// Setter
// -------------------------------
	public void set_ip(String ip)
	{
		this.ip = (ip == null) ? null : new String(ip);
	}

	public void addFreind(String freindLogin)
	{
		if (this.freind.contains(freindLogin))
		{
			return;
		}
		this.freind.add(freindLogin);
	}

// -------------------------------
// Local method
// -------------------------------
	@Override
	public void setProperty(String propertyName, Object propertyValue)
	{
		try
		{
			Field field = this.getClass().getDeclaredField(propertyName);
			field.set(this, propertyValue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addProperty(String propertyName, Object propertyValue)
	{
		try
		{
			Field field = this.getClass().getDeclaredField(propertyName);
			List listToUpdate = (List) field.get(this);
			listToUpdate.add(propertyValue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
}