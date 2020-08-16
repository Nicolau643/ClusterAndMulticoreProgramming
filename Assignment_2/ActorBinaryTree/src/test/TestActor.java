package test;

import core.MessageTypes.RequestMessageClient;
import core.MessageTypes.ResponseMessageClient;
import library.Actor;
import library.Message;

public class TestActor extends Actor{
	
	private static final int INSERT = 0;
	private static final int DELETE = 1;
	private static final int CONTAINS = 2;
	
	public TestActor() {
		super(null);
	}

	protected TestActor(Actor supervisor) {
		super(supervisor);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processMessage(Message m) {
		
		if (m instanceof StartTestsMessage) { //escrever os testes 
			
			System.out.println("testActor"+this);
			
			int insert[] = {10,5,8,7,15,16,13,14,3};
			
			for (int i : insert) {
				insert(m, i);
			}
			
			delete(m,15);
			
			contains(m,15);
			
			contains(m,16);
			
			contains(m,13);

			contains(m,10);
			
			delete(m,5);
			
			contains(m,8);
			
			contains(m,7);
			
			contains(m,3);
			
			contains (m,5);

			
		}else if(m instanceof ResponseMessageClient) {
			
			ResponseMessageClient rm = (ResponseMessageClient) m;
			
			System.out.println("--------------------------");
			
			if (rm.getOp() == INSERT) {
				System.out.println("Insert");
			}else if (rm.getOp() == DELETE) {
				System.out.println("Delete");
			}else {
				System.out.println("Contains");
			}
			
			System.out.println(rm.getContent());
			System.out.println(rm.getResult());
			System.out.println("--------------------------");
			
		}
		
	}
	
	private void insert(Message m,Integer c) {
		((StartTestsMessage) m).getBinaryTree().receiveMessage(new RequestMessageClient(this, INSERT, c));
	}
	
	private void delete(Message m,Integer c) {
		((StartTestsMessage) m).getBinaryTree().receiveMessage(new RequestMessageClient(this, DELETE, c));
	}

	private void contains(Message m,Integer c) {
		((StartTestsMessage) m).getBinaryTree().receiveMessage(new RequestMessageClient(this, CONTAINS, c));
	}
}
