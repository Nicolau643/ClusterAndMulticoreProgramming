package test;

import core.BinaryTreeActor;

public class Main {
	
	public static void main(String[] args) {
		BinaryTreeActor tree = new BinaryTreeActor();
		tree.start();
		
		TestActor ta = new TestActor();
		ta.processMessage(new StartTestsMessage(null,tree));
		ta.start();
		
	}

}
