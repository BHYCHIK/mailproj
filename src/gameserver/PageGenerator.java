package gameserver;
public class PageGenerator {
	public static String GeneratePage(UserSession userSession)
	{
		if(userSession.getUserName() == null)
		{
			return GenerateEnterNamePage(userSession);
		}
		
		if(userSession.getUserId() == null)
		{
			return GenerateWait(userSession); 
		}
		
		return GenerateFinished(userSession);
	}
	
	private static String GenerateEnterNamePage(UserSession userSession)
	{
		StringBuilder page = new StringBuilder();
		
		page.append("<html>\n");
		page.append("<body>\n");
		page.append("<br>Please enter your name: \n");
		page.append("<form name='form' method='POST' action='/'>\n");
		page.append("<input name='sessionId' type='hidden' value='" + userSession.getSessionId().toString() + "' >\n");
		page.append("Name:<input name='userName' type='text'><br>\n");
		page.append("<input type='submit'\n");
		page.append("</form>\n");
		page.append("</body>\n");
		page.append("</html>\n");
		
		return page.toString();
	}
	
	private static String GenerateWait(UserSession userSession)
	{
		StringBuilder page = new StringBuilder();
		
		page.append("<html>\n");
		page.append("<body>\n");
		page.append(userSession.getUserName());
		page.append(", please wait for auth<br>\n");
		page.append("<form name='form' method='POST'>\n");
		page.append("<input name='sessionId' type='hidden' value='" + userSession.getSessionId().toString() + "' >\n");
		page.append("</form>\n");
		page.append("<script type='text/javascript'>\n");
		page.append("setTimeout(function(){document.forms['form'].submit()},1000)\n");
		page.append("</script>\n");
		page.append("</body>\n");
		page.append("</html>\n");
		
		return page.toString();		
	}
	
	private static String GenerateFinished(UserSession userSession)
	{
		StringBuilder page = new StringBuilder();
		
		page.append("<html>\n");
		page.append("<body>\n");
		page.append("Hello, " + userSession.getUserName() + ". Your userId is: " + userSession.getUserId().toString() + "<br>\n");
		page.append("<form name='form' method='POST'>\n");
		page.append("<input name='sessionId' type='hidden' value='" + String.valueOf(userSession.getSessionId()) + "' >\n");
		page.append("</form>\n");
		page.append("<script type='text/javascript'>\n");
		page.append("setTimeout(function(){document.forms['form'].submit()},1000)\n");
		page.append("</script>\n");
		page.append("</body>\n");
		page.append("</html>\n");
		
		return page.toString();			
	}
}