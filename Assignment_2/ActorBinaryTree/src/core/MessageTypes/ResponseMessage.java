package core.MessageTypes;

import library.Actor;
import library.Message;

public class ResponseMessage extends Message implements Comparable<ResponseMessage> {

	private int id;
	private int op;
	private Integer content;
	private String result;
	private Actor client;
	
	
	public ResponseMessage(Actor sender,Actor client, int id,int op, Integer content, String result) {
		super(sender);
		this.id = id;
		this.op = op;
		this.content = content;
		this.result = result;
		this.client = client;
	}


	public int getId() {
		return id;
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

	
	public Actor getClient() {
		return client;
	}


	@Override
	public int compareTo(ResponseMessage o) {
		return ((Integer)getId()).compareTo((Integer)(o.getId()));
	}


	

}
