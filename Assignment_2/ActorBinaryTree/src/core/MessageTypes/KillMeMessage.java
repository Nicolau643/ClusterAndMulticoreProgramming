package core.MessageTypes;

import library.Actor;
import library.Message;

public class KillMeMessage extends Message{
	
	private int autorIndex;
	private int id;
	private int op;
	private Integer content;
	private Actor binaryTree;
	private Actor client;

	public KillMeMessage(Actor sender,int autorIndex,  int id, int op, Integer content, Actor binaryTree, Actor client) {
		super(sender);
		this.autorIndex = autorIndex;
		this.id = id;
		this.op = op;
		this.content = content;
		this.binaryTree = binaryTree;
		this.client = client;
		
	}
	
	public int getAutorIndex() {
		return autorIndex;
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

	public Actor getBinaryTree() {
		return binaryTree;
	}
	
	public Actor getClient() {
		return client;
	}

}
