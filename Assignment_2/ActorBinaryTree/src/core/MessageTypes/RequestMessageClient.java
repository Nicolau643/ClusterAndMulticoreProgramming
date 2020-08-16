package core.MessageTypes;

import library.Actor;
import library.Message;

public class RequestMessageClient extends Message {
	
	private int op;
	private Integer content;

	public RequestMessageClient(Actor sender, int op, Integer content) {
		super(sender);
		this.op = op;
		this.content = content;
	}
	
	public Integer getContent() {
		return content;
	}
	
	public int getOp() {
		return op;
	}
	

}
