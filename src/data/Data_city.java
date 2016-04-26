package data;

import java.lang.reflect.Field;

import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;






/**
 * Model class: used to store an entry of the city table into the data base
 * @author kassuskley
 *
 */

@Entity
public class Data_city implements DataInterface
{
// -------------------------------
// Attributes
// -------------------------------
	@Id
	@PrimaryKey
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String cityId;

// -------------------------------
// Builder
// -------------------------------
	public Data_city(String cityId)
	{
		this.cityId = (cityId == null) ? null : new String(cityId);
	}

// -------------------------------
// Local methods
// -------------------------------
	public String getKey()
	{
		return new String(this.cityId);
	}

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

	@Override
	public void addProperty(String propertyName, Object propertyValue)
	{
		throw new RuntimeException("The class " + this.getClass().getCanonicalName() + " contains no extendable attribute");
	}

	@Override
	public String toString()
	{
		return new String (this.cityId);
	}
}