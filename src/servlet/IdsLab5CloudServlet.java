package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.*;


import data.ResourceManager_DataStoreService;
import data.Data_user;







@SuppressWarnings("serial")
public class IdsLab5CloudServlet extends HttpServlet
{
// ---------------------------------------------
// Attributes
// ---------------------------------------------
	public static final String	FILE_DIRECTORY_STATIC				= "resourceStatic/";

	public static final String	FILE_LOGIN_HTML						= "login.jsp";
	public static final String	FILE_LOGIN_FAIL_HTML				= "login_fail.jsp";
	public static final String	FILE_LOGIN_SUCCESSFULL_LOGOUT_HTML	= "login_successfulLogout.jsp";
	public static final String	FILE_CREATE_ACCOUNT_HTML			= "createAccount.jsp";
	public static final String	FILE_CREATE_ACCOUNT_FAIL_HTML		= "createAccount_fail.jsp";
	public static final String	FILE_USER_PAGE_HTML					= "userPage.jsp";

	public static final String	ATTRIBUTE_NAME_LOGIN				= "login";
	public static final String	ATTRIBUTE_NAME_PASSWORD				= "password";
	public static final String	ATTRIBUTE_NAME_PASSWORD_CONFIRMATION= "passwordConfirmation";
	public static final String	ATTRIBUTE_NAME_NAME					= "name";
	public static final String	ATTRIBUTE_NAME_SURNAME				= "surname";
	public static final String	ATTRIBUTE_NAME_AGE					= "age";
	public static final String	ATTRIBUTE_NAME_LOCATION				= "location";
	public static final String	ATTRIBUTE_NAME_INTEREST				= "interest";
	public static final String	ATTRIBUTE_NAME_FREIND_TO_ADD_LOGIN	= "freindToAddLogin";
	public static final String	ATTRIBUTE_NAME_FREIND_TO_REACH		= "freindToreach";
	public static final String	ATTRIBUTE_NAME_IS_FOREIGN_USER		= "isForeignUser";
	public static final String	ATTRIBUTE_NAME_FREIND_LOGIN_ERROR	= "freindLoginError";

	public static final String	ATTRIBUTE_SEPARATOR_INTEREST		= ",";
	public static final String	GET_PARAMETER_SEPARATOR				= "?";
	public static final String	GET_PARAMETER_VALUE_SEPARATOR		= "=";
	public static final String	FILE_SEPARATOR_INTEREST				= "/";
	public static final String	RESOURCE_DIR_STATIC					= "resourceStatic/";
	public static final String	RESOURCE_DIR_DYNAMIC				= "resourceDynamic/";

//	public static ResourceManager_NonPersistant		userSet	= new ResourceManager_NonPersistant();
	public static ResourceManager_DataStoreService	userSet = new ResourceManager_DataStoreService();

// ---------------------------------------------
// Entry method
// ---------------------------------------------
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		this.entryMethod(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.entryMethod(req, resp);
	}

	private void entryMethod(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
//TODO
System.out.println("+++++++++++++++++++++++++++++++++++++");
System.out.println(IdsLab5CloudServlet.userSet.getEntity(ResourceManager_DataStoreService.keyAvailableCity, null));
System.out.println("+++++++++++++++++++++++++++++++++++++");
		PrintWriter requestWritter = resp.getWriter();
		String methodName = parseMethodName(req);
System.out.println("+++++ Method: " + methodName);

		try
		{
			Class<?>[]	parametersType = {HttpServletRequest.class, HttpServletResponse.class, PrintWriter.class};
			Object[]	parameters		= {req, resp, requestWritter};
			Method m = IdsLab5CloudServlet.class.getMethod(methodName, parametersType);
			m.invoke(this, parameters);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			requestWritter.println(e);
		}
	}

// ---------------------------------------------
// Request specific methods
// ---------------------------------------------
	public void loginSubmit(HttpServletRequest req, HttpServletResponse resp, PrintWriter requestWritter) throws IOException
	{
		String login	= null;
		String password	= null;
//TODO why using a new data base (non relational) instead of using the relational existing ones:
//TODO 	answer = scalability
		try
		{
			login	= req.getParameter(ATTRIBUTE_NAME_LOGIN);
			password= req.getParameter(ATTRIBUTE_NAME_PASSWORD);
			if ((login == null) || (password == null))
			{
				throw new Exception();
			}
			userSet.setUserIP(login, req.getRemoteHost());
			Data_user user = userSet.getUser(login);
			if (!password.equals(user.get_password()))
			{
				throw new Exception();
			}

			// Redirect to the specific page of the user
			redirectToUserPage(req, resp, user.getKey(), false, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Login    = " + login);
			System.out.println("Password = " + password);
			this.redirectToFile(req, resp, FILE_LOGIN_FAIL_HTML, null);
		}
	}

	public void createAccountSubmit(HttpServletRequest req, HttpServletResponse resp, PrintWriter requestWritter) throws IOException
	{
		String login				= null;
		String password				= null;
		String passwordConfirmation	= null;
		String name					= null;
		String surname				= null;
		String location				= null;
		String interest				= null;
		Integer age					= null;

		try
		{
			login					= req.getParameter(ATTRIBUTE_NAME_LOGIN);
			password				= req.getParameter(ATTRIBUTE_NAME_PASSWORD);
			passwordConfirmation	= req.getParameter(ATTRIBUTE_NAME_PASSWORD_CONFIRMATION);
			name					= req.getParameter(ATTRIBUTE_NAME_NAME);
			surname					= req.getParameter(ATTRIBUTE_NAME_SURNAME);
			age						= Integer.parseInt(req.getParameter(ATTRIBUTE_NAME_AGE));
			location				= req.getParameter(ATTRIBUTE_NAME_LOCATION);
			interest				= req.getParameter(ATTRIBUTE_NAME_INTEREST);
			String []interestTab	= interest.split(ATTRIBUTE_SEPARATOR_INTEREST);
			Data_user user = new Data_user(login, password, name, surname, age, location, interestTab);

			if (userSet.containsUser(login))
			{
				throw new Exception();
			}
			if (!password.equals(passwordConfirmation))
			{
				throw new Exception();
			}
			user.setIp(req.getRemoteHost());
			userSet.addUser(user);
			// Redirect to the specific page of the user
			redirectToUserPage(req, resp, user.getKey(), false, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Login            = " + login);
			System.out.println("Password         = " + password);
			System.out.println("Password Confirm = " + passwordConfirmation);
			System.out.println("Name             = " + name);
			System.out.println("Surname          = " + surname);
			this.redirectToFile(req, resp, FILE_CREATE_ACCOUNT_FAIL_HTML, null);
		}
	}
	public void logout(HttpServletRequest req, HttpServletResponse resp, PrintWriter requestWritter) throws IOException
	{
		String login = req.getParameter(ATTRIBUTE_NAME_LOGIN);
		userSet.setUserIP(login, null);
		this.redirectToFile(req, resp, FILE_LOGIN_SUCCESSFULL_LOGOUT_HTML, null);
	}

	public void addFreind(HttpServletRequest req, HttpServletResponse resp, PrintWriter requestWritter) throws IOException
	{
		String	freindLogin	= null;
		String	currentLogin= null;
		Data_user	currentUser	= null;

		try
		{
			currentLogin	= req.getParameter(ATTRIBUTE_NAME_LOGIN);
			freindLogin		= req.getParameter(ATTRIBUTE_NAME_FREIND_TO_ADD_LOGIN);
			if ((currentLogin == null) || (freindLogin == null) || (!userSet.containsUser(currentLogin)) || (!userSet.containsUser(freindLogin)))
			{
				throw new Exception();
			}
			userSet.addUserFreind(currentLogin, freindLogin);
			userSet.addUserFreind(freindLogin, currentLogin);
			currentUser		= userSet.getUser(currentLogin);
			// Redirect to the specific page of the user
			redirectToUserPage(req, resp, currentUser.getKey(), false, null);
		}
		catch(Exception e)
		{
			currentUser = userSet.getUser(currentLogin);
			if ((currentUser != null) && (freindLogin != null))
			{
				// Redirect to the specific page of the user
				redirectToUserPage(req, resp, currentUser.getKey(), false, freindLogin);
			}
			else
			{
				e.printStackTrace();
			}
		}
	}

	public void getFreindPage(HttpServletRequest req, HttpServletResponse resp, PrintWriter requestWritter) throws IOException
	{
		String	freindLogin	= null;
		Data_user	freindUser	= null;

		try
		{
			freindLogin		= req.getParameter(ATTRIBUTE_NAME_FREIND_TO_REACH);
			freindUser		= userSet.getUser(freindLogin);
			if ((freindLogin == null) || (freindUser == null))
			{
				throw new Exception();
			}
			// Redirect to the specific page of the user
			redirectToUserPage(req, resp, freindUser.getKey(), true, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

// ---------------------------------------------
// Private methods
// ---------------------------------------------
	private static String parseMethodName(HttpServletRequest req)
	{
		String res = req.getRequestURI();

		if (res.charAt(0) == '/')
		{
			res = res.substring(1, res.length());
		}

		if (res.contains(GET_PARAMETER_SEPARATOR))
		{
			String[] resTab = res.split(GET_PARAMETER_SEPARATOR);
			res = resTab[0];
		}

		return res;
	}

	/**
	 * Send to the user an HTTP code 301: redirect to the given url
	 */
	private void redirectToFile(HttpServletRequest req, HttpServletResponse resp, String fileName, HashMap<String, String> parameter) throws IOException
	{
		String	host	= req.getServerName();
		int		port	= req.getServerPort();
		String	url		= "http://" + host + ":" + port + "/" + fileName;

		if (parameter != null)
		{
			boolean isFirst = true;
			for (String parameterName: parameter.keySet())
			{
				String parameterValue = parameter.get(parameterName);
				if (isFirst)
				{
					url += GET_PARAMETER_SEPARATOR + parameterName + GET_PARAMETER_VALUE_SEPARATOR + parameterValue;
					isFirst = false;
				}
				else
				{
					url += ATTRIBUTE_SEPARATOR_INTEREST + parameterName + GET_PARAMETER_VALUE_SEPARATOR + parameterValue;
				}
			}
		}
		resp.sendRedirect(url);
	}

	private void redirectToUserPage(HttpServletRequest req, HttpServletResponse resp, String login, boolean isForeignUser, String freindLoginError) throws IOException
	{
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put(ATTRIBUTE_NAME_LOGIN, login);
		parameter.put(ATTRIBUTE_NAME_IS_FOREIGN_USER, ""+isForeignUser);
		parameter.put(ATTRIBUTE_NAME_FREIND_LOGIN_ERROR, freindLoginError);
		this.redirectToFile(req, resp, FILE_USER_PAGE_HTML, parameter);
	}
}