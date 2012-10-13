package gameserver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
 
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
 
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
 
public class Frontend extends AbstractHandler implements Abonent, Runnable
{
	private static AtomicInteger sessionIdGenerator = new AtomicInteger();
	private static ConcurrentMap<Integer, UserSession> sessionIdToUserSession;
	private Address address;
	private static AccountServiceBalancer accountServices;
	
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {    
    	Integer sessionId;
    	UserSession userSession;
    	String idFromRequest;
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        idFromRequest = request.getParameter("sessionId");
        String userNameFromRequest = request.getParameter("userName");
        if(idFromRequest == null)
        {
        	sessionId = sessionIdGenerator.getAndIncrement();
        }
        else
        {
        	sessionId = Integer.parseInt(idFromRequest);
        }
        userSession = sessionIdToUserSession.get(sessionId);
        if(userSession == null)
        {
        	userSession = new UserSession(sessionId);
        	sessionIdToUserSession.put(sessionId, userSession);
        }
        if(userSession.getUserName() == null)
        {
        	userSession.setUserName(userNameFromRequest);
        }
        baseRequest.setHandled(true);
        response.getWriter().println(PageGenerator.GeneratePage(userSession));
        makeAuthProgress(userSession);
    }
    
    private void makeAuthProgress(UserSession userSession)
    {
    	if (userSession.getUserName() != null && userSession.isAuthNeeded())
    	{
    		userSession.AuthIsDoing();
    		MsgFindUserId msg = new MsgFindUserId(getAddress(), accountServices.getService(userSession).getAddress(), userSession.getSessionId(), userSession.getUserName());
    		MessageSystem.sendMessage(msg);
    	}
    }
    
    public void updateUserId(int sessionId, int userId)
    {
    	sessionIdToUserSession.get(sessionId).setUserId(userId);
    }
    
    public Frontend()
    {
    	address = new Address();
    	MessageSystem.register(address);
    }
 
    public static void main(String[] args) throws Exception
    {  	
    	sessionIdToUserSession = new ConcurrentHashMap<Integer, UserSession>();
    	accountServices = new AccountServiceBalancer(2);
    	Frontend frontend = new Frontend();
    	(new Thread(frontend)).start();
    	
    	Server server = new Server(8081);
        server.setHandler(frontend);
 
        server.start();
        server.join();
    }

	@Override
	public Address getAddress()
	{
		return address;
	}

	@Override
	public void run() 
	{
		while(!Thread.currentThread().isInterrupted())
		{
			MessageSystem.execForAbonent(this);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}