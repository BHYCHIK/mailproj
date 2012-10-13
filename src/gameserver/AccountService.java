package gameserver;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class AccountService implements Runnable, Abonent
{
	
	private Address address;
	private static ConcurrentMap<String, Integer> userDataBase;	
	private static AtomicInteger userIdGenerator;
	
	public AccountService()
	{
		address = new Address();
		MessageSystem.register(address);
		userDataBase = new ConcurrentHashMap<String, Integer>();
		userIdGenerator = new AtomicInteger();
	}

	@Override
	public void run()
	{
		while(!Thread.currentThread().isInterrupted())
		{
			MessageSystem.execForAbonent(this);
			try 
			{
				Thread.sleep(50);
			} 
			catch (InterruptedException e)
			{
			}			
		}
	}

	@Override
	public Address getAddress()
	{
		return address;
	}
	
	@SuppressWarnings("finally")
	public int findAccountId(String userName)
	{
		try
		{
			Thread.sleep(5000);
		}
		finally
		{
			Integer accountId;
			accountId = userDataBase.get(userName);
			if(accountId == null)
			{
				accountId = userIdGenerator.incrementAndGet();
				userDataBase.put(userName, accountId);
				return accountId;
			}
			else
			{
				return accountId;
			}
		}
	}
}
