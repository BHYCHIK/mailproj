package gameserver;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public final class MessageSystem
{
	private static ConcurrentMap<Address, LinkedBlockingQueue<Msg>> messages = new ConcurrentHashMap<Address, LinkedBlockingQueue<Msg>>();
	
	public static void sendMessage(Msg message)
	{
		Queue<Msg> messageQueue;
		messageQueue = messages.get(message.getTo());		
		messageQueue.add(message);
	}
	
	public static void execForAbonent(Abonent abonent)
	{
		Queue<Msg> messageQueue = messages.get(abonent.getAddress());
		messageQueue = messages.get(abonent.getAddress());		
		while(!messageQueue.isEmpty())
		{
			Msg msg = messageQueue.poll();
			if(msg != null)
			{
				msg.exec(abonent);
			}
		}
	}
	
	public static void register(Address address)
	{
		LinkedBlockingQueue<Msg> messageQueue;
		messageQueue = messages.get(address);
		if(messageQueue == null)
		{
			messageQueue = new LinkedBlockingQueue<Msg>();
			messages.put(address, messageQueue);
		}
	}
}
