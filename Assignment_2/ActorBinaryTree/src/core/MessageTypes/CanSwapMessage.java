package core.MessageTypes;

import library.Actor;
import library.Message;

public class CanSwapMessage extends Message{

	private Integer contentToSwap;
	private int id;
	private int op;
	private Integer content;
	private Actor binaryTree;
	private Actor client;
	
	public CanSwapMessage(Actor sender,Integer contentToSwap, int id, int op, Integer content, Actor binaryTree, Actor client) {
		super(sender);
		this.contentToSwap = contentToSwap;
		this.id = id;
		this.op = op;
		this.content = content;
		this.binaryTree = binaryTree;
		this.client = client;
	}
	
	public Integer getContentToSwap() {
		return contentToSwap;
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
