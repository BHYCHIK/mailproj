package gameserver;

public final class UserSession
{
	private Integer sessionID;
	private Integer userID;
	private String userName;
	private boolean authIsNeeded;
	private Object locker;
	
	public UserSession(int sessionID)
	{
		this.sessionID = sessionID;
		userID = null;
		userName = null;
		authIsNeeded = true;
		locker = new Object();
	}
	
	public Integer getSessionId()
	{
		synchronized (locker) 
		{
			return sessionID;
		}		
	}
	
	public Integer getUserId()
	{
		synchronized (locker)
		{
			return userID;
		}
	}
	
	public boolean isAuthNeeded()
	{
		synchronized (locker)
		{
			return authIsNeeded;
		}		
	}
	
	public void AuthIsDoing()
	{
		synchronized (locker)
		{
			authIsNeeded = false;
		}		
	}
	
	public void setUserId(int userId)
	{
		synchronized (locker)
		{
			this.userID = userId;
		}		
	}
	
	public String getUserName()
	{
		synchronized (locker)
		{
			return userName;
		}
	}
	
	public void setUserName(String userName)
	{
		synchronized (locker)
		{
			this.userName = userName;
		}		
	}
}
