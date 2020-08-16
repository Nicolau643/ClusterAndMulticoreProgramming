package core.MessageTypes;

import library.Actor;
import library.Message;

public class GiveMeLastLessSonMessage extends Message{
	
	private Actor toDelete;
	private int id;
	private int op;
	private Integer content;
	private Actor binaryTree;
	private Actor client;

	public GiveMeLastLessSonMessage(Actor sender,Actor toDelete, int id, int op, Integer content, Actor binaryTree, Actor client) {
		super(sender);
		this.toDelete = toDelete;
		this.id = id;
		this.op = op;
		this.content = content;
		this.binaryTree = binaryTree;
		this.client = client;
	}
	
	public Actor getToDelete() {
		return toDelete;
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
