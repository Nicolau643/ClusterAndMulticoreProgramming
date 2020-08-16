package core.MessageTypes;

import library.Actor;
import library.Message;

public class PleaseHaveMyLessSonMessage extends Message {

	private Actor son;
	private int id;
	private int op;
	private Integer content;
	private Actor binaryTree;
	private Actor client;
	
	public PleaseHaveMyLessSonMessage(Actor sender,Actor son, int id, int op, Integer content, Actor binaryTree, Actor client) {
		super(sender);
		this.son = son;
		this.id = id;
		this.op = op;
		this.content = content;
		this.binaryTree = binaryTree;
		this.client = client;
	}
	
	public Actor getSon() {
		return son;
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
