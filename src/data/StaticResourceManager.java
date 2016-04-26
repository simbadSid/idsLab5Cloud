package data;

import java.io.File;






public class StaticResourceManager
{
// -------------------------------
// Attributes
// -------------------------------
	public final static String	defaultResourceDir		= "war/";
	public final static String	defaultPicture			= "resourceStatic/userPicture/default.jpeg";

// -------------------------------
// Local method
// -------------------------------
	public static boolean isKnownResource(String resource)
	{
		if (resource.startsWith(defaultResourceDir))
		{
			return true;
		}

		try
		{
			File f = new File(resource);
			return ((f.exists()) && (!f.isDirectory()));
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static String MIME_type(String fileName)
	{
		if (fileName.endsWith(".jsp"))	return "magnus-internal/jsp";
		if (fileName.endsWith(".html"))	return "text/html";
		return null;
	}
}