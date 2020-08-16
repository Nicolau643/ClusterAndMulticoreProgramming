package test;

import library.Actor;
import library.Message;

public class StartTestsMessage extends Message {

	private Actor binaryTree;
	
	protected StartTestsMessage(Actor sender,Actor binnaryTree) {
		super(sender);
		this.binaryTree = binnaryTree;
		// TODO Auto-generated constructor stub
	}

	public Actor getBinaryTree() {
		return binaryTree;
	}

}
