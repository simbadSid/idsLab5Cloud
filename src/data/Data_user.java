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
	private static final String[] attributesToPrint = {"login", "name", "surname", "age", "location", "ip", "interests"};
	private static final String[] attributesToStore	= {"login", "password", "name", "surname", "age", "location", "ip", "interests", "freind"};

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
	public String				getKey		(){return new String(this.login);}
	public String				getPassword		(){return new String(this.password);}
	public String				getName			(){return new String(this.name);}
	public String				getSurname		(){return new String(this.surname);}
	public Long					getAge			(){return this.age;}
	public String				getLocation		(){return new String(this.location);}
	public String				getIP			(){return (this.ip == null)? null : new String(this.location);}
	public LinkedList<String>	getInterests	(){return new LinkedList<String>(this.interests);}
	public LinkedList<String>	getFreind		(){return new LinkedList<String>(this.freind);}
	public String				getPictureName	()
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
	public void setIp(String ip)
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

	public String getUserFile(ResourceManager userSet, boolean isForeignPage, String freindLoginError)
	{
		String res = "";

		res += "<!DOCTYPE html>";
		res += "<html lang=\"en\">";
		res += "<head>";
		res += "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">";
		res += "		<meta charset=\"utf-8\">";
		res += "		<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">";
		res += "		<title>Social Network: user page</title>";
		res += "			<link rel=\"stylesheet\" type=\"text/css\" href=\"login_fichiers/login.html\">";
		res += "</head>";
		res += "<body>";
		res += "	<section class=\"container\">";
		res += "		<div align=\"center\">";
		res += "		<h1>Social network: " + this.login + "'s page (" + ((!isForeignPage)? "loged user" : "Freind") + ")</h1>";
		res += "	</section>";
		res += "	<section class=\"container\">";
		res += "		<div align=\"left\">";
		res += "		<table class=\"lamp\">";
		res += "			<tr>";
		res += "				<th>";
		res += "					<img src=\"" + this.getPictureName()+ "\" alt=\"Note\" style=\"height:100px;width:100px\">";
		res += "				</th>";
		res += "			</tr>";
		res += "			<tr>";
		res += "				<td>";
		if (!isForeignPage)
		{
			res += "					<a href=\"logout?login=" + this.getKey() + "\">Logout (" + this.getKey() + ")</a>";
		}
		res += "				</td>";
		res += "			</tr>";
		res += "		</table>";
		res += "	</section>";
		res += "<p></p>";
		res += "	<section class=\"container\">";
		res += "		<div align=\"left\">";
		res += "		<table class=\"lamp\">";

		for (String fieldName: attributesToPrint)
		{
			Field field = null;
			try
			{
				field = Data_user.class.getDeclaredField(fieldName);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				continue;
			}
			String attributeName	= field.getName();
			String attributeValue	= null;
			try
			{
				attributeValue = field.get(this).toString();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			res += "			<tr>";
			res += "				<th>";
			res += "					" + attributeName;
			res += "				</th>";
			res += "				<td>";
			if (isForeignPage)
			{
				res += "					" + attributeValue + "\n";
			}
			else
			{
				res += "					<a href=\"#### URL to change the label ####\"> " + attributeValue + "</a>";	
			}
			res += "				</td>";
			res += "			</tr>";
		}
		res += "		</table>";
		res += "	</section>";

		res += "	<section class=\"container\">";
		res += "		<div align=\"right\">";
		if (!isForeignPage)
		{
			res += "			<h1>Find New Freinds</h1>";
			res += "			<form method=\"post\" action=\"addFreind?login=" + this.getKey() + "\">";
			res += "					<input name=\"freindToAddLogin\" placeholder=\"Type a login\">";
			res += "					<input name=\"submit\" value=\"Search\" type=\"submit\">";
			res += "			</form>";
			res += "			<p></p>";
			res += "		<table class=\"lamp\">";
		}

		if (freindLoginError != null)
		{
			res += "	<section class=\"container\">";
			res += "		<div align=\"right\">";
			res += "			<h1>Can't find the freind \"" + freindLoginError + "\"</h1>";
			res += "			<p></p>";
			res += "	</section>";
		}
		if (!this.freind.isEmpty())
		{
			res += "			<h1>" + this.getKey()+ "'s Freinds:</h1>";
		}

		for (String freindLogin: this.freind)
		{
			Data_user freindUser= userSet.getUser(freindLogin);
			res += "				<tr>";
			res += "					<th>";
			res += "						<img src=\"" + freindUser.getPictureName()+ "\" alt=\"Note\" style=\"height:100px;width:100px\">";
			res += "					</th>";
			res += "					<td>";
			res += "					<a href=\"getFreindPage?freindToreach=" + freindLogin + "\"> " + freindLogin + "</a>";
			res += "					</td>";
			res += "				</tr>";
		}
		res += "		</div>";
		res += "	</section>";
		res += "</body>";
		res += "</html>";

		return res;
	}
}