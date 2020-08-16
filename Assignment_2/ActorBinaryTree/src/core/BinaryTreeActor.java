package core;

import java.util.PriorityQueue;

import core.MessageTypes.RequestMessage;
import core.MessageTypes.RequestMessageClient;
import core.MessageTypes.ResponseMessage;
import core.MessageTypes.ResponseMessageClient;
import library.Actor;
import library.Message;

public class BinaryTreeActor extends Actor {
	
	private int cont = 0;
	
	private PriorityQueue<ResponseMessage> waitingMessages = new PriorityQueue<>();
	private int sendId = 0;
	
	public BinaryTreeActor() {
		super(null);
	}

	protected BinaryTreeActor(Actor supervisor) {
		super(supervisor);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void processMessage(Message m) {
		
		if (m instanceof RequestMessageClient) {
			RequestMessageClient rm = (RequestMessageClient) m;
			
			RequestMessage msgTree = new RequestMessage(cont,this,this,rm.getSender(),rm.getOp(),rm.getContent(),-1);
			cont++;
			if (getNumberOfChilds() == 0) {
				NodeActor na = new NodeActor(this);
				registerChild(na);
				na.receiveMessage(msgTree);
				na.start();
			}else {
				getChildren().get(0).receiveMessage(msgTree);
			}
			
		}else if(m instanceof ResponseMessage) {
			
			ResponseMessage rm = (ResponseMessage) m;
			
			if (rm.getId() == sendId) {
				ResponseMessageClient rmc = new ResponseMessageClient(rm.getSender(), rm.getOp(), rm.getContent(), rm.getResult()); 
				
				rm.getClient().receiveMessage(rmc);
				
				sendId++;
				
				while (waitingMessages.peek()!=null) {
						if (waitingMessages.peek().getId()!=sendId) {
							break;
						}else {
							ResponseMessage nextRm = waitingMessages.poll();
							ResponseMessageClient rmc2 = new ResponseMessageClient(nextRm.getSender(), nextRm.getOp(), nextRm.getContent(), nextRm.getResult()); 
							
							nextRm.getClient().receiveMessage(rmc2);
							
							sendId++;
						}
					}
				
			}else {
				waitingMessages.add(rm);
			}
			
			
		}
	}

}
