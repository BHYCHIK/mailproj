package gameserver;

public final class AccountServiceBalancer
{
	private static AccountService[] pool;
	public AccountServiceBalancer(int size)
	{
		pool = new AccountService[size];
		for(int i = 0; i < size; ++i)
		{
			pool[i] = new AccountService();
			(new Thread(pool[i])).start();
		}
	}
	public AccountService getService(UserSession userSession)
	{
		return pool[userSession.getSessionId() % pool.length];
	}
}
