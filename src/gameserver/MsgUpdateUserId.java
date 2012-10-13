package gameserver;

public final class MsgUpdateUserId extends MsgToFrontend
{
	final private int sessionId;
	final private int userId;
	
	public MsgUpdateUserId(Address from, Address to, int sessionId, int userId)
	{
		super(from, to);
		this.sessionId = sessionId;
		this.userId = userId;
	}

	@Override
	public void exec(Frontend fronend)
	{
		fronend.updateUserId(sessionId, userId);		
	}
}
