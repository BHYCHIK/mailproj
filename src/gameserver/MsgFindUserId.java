package gameserver;

public final class MsgFindUserId extends MsgToAccountService
{
	final private String userName;
	final private int sessionId;
	
	public MsgFindUserId(Address from, Address to, int sessionId, String userName) 
	{
		super(from, to);
		this.userName = userName;
		this.sessionId = sessionId;
	}

	public void exec(AccountService accountService)
	{
		int userId = accountService.findAccountId(userName);
		MsgUpdateUserId reply = new MsgUpdateUserId(this.getTo(), this.getFrom(), sessionId, userId);
		MessageSystem.sendMessage(reply);
	}
}
