package core.MessageTypes;

import library.Actor;
import library.Message;

public class ResponseMessageClient extends Message{

	private int op;
	private Integer content;
	private String result;
	
	public ResponseMessageClient(Actor sender,int op, Integer content,String result) {
		super(sender);
		this.op = op;
		this.content = content;
		this.result = result;
	}

	public int getOp() {
		return op;
	}

	public Integer getContent() {
		return content;
	}

	public String getResult() {
		return result;
	}
	
	

}
