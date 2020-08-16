package core.MessageTypes;

import library.Actor;
import library.Message;

public class RequestMessage extends Message {
	
	private int id;
	private int op;
	private Integer content;
	private Actor binaryTreeActor;
	private Actor client;
	private int lastIndex;

	public RequestMessage(int id, Actor sender, Actor binaryTreeActor, Actor client, int op, Integer content,int lastIndex) {
		super(sender);
		this.id = id;
		this.op = op;
		this.binaryTreeActor = binaryTreeActor;
		this.client = client;
		this.content = content;
		this.lastIndex = lastIndex;
	}

	
	
	public Actor getClient() {
		return client;
	}
	
	public int getId() {
		return id;
	}
	
	public Integer getOp() {
		return op;
	}

	public Integer getContent() {
		return content;
	}

	public Actor getBinaryTreeActor() {
		return binaryTreeActor;
	}

	public void setBinaryTreeActor(Actor binaryTreeActor) {
		this.binaryTreeActor = binaryTreeActor;
	}

	public int getLastIndex() {
		return lastIndex;
	}
	
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	
	
}
