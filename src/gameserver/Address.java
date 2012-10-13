package gameserver;

import java.util.concurrent.atomic.AtomicInteger;

public final class Address
{
	public static AtomicInteger abonentIdGenerator = new AtomicInteger();
	final private int abonentId;
	
	public Address()
	{
		this.abonentId = abonentIdGenerator.incrementAndGet();
	}
	
	public int getAbonentId()
	{
		return abonentId;
	}

}
