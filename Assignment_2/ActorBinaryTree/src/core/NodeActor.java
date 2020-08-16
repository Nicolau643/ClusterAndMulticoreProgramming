package core;

import core.MessageTypes.CanSwapMessage;
import core.MessageTypes.GiveMeLastLessSonMessage;
import core.MessageTypes.JustSuicide;
import core.MessageTypes.KillMeMessage;
import core.MessageTypes.PleaseHaveMyGreaterSonMessage;
import core.MessageTypes.PleaseHaveMyLessSonMessage;
import core.MessageTypes.RequestMessage;
import core.MessageTypes.ResponseMessage;
import library.Actor;
import library.Message;
import library.messagetypes.KillMessage;

public class NodeActor extends Actor{
	
	
	private boolean isDeleted;
	
	private int greater = -1;
	private int less = -1;
	
	private Integer content;

	protected NodeActor(Actor supervisor) {
		super(supervisor);
		content = null;
		isDeleted = false;
	}

	@Override
	protected void processMessage(Message m) {
		
		if (m instanceof RequestMessage) {
			RequestMessage rm = (RequestMessage) m;
			
			if (isDeleted) {
				rm.getSender().receiveMessage(rm);
				return;
			}
			
			if (rm.getOp() == 0) { //se for insert
				
				if (content == null || content.compareTo(rm.getContent()) == 0 ) { //novo nó ou content igual
					content = rm.getContent();
					ResponseMessage responseM = new ResponseMessage(this, rm.getClient(), rm.getId(), 
												rm.getOp(), rm.getContent(), "Introduzido com sucesso! :D" );
					
					rm.getBinaryTreeActor().receiveMessage(responseM);
					
					return;
					
				}
				
				if (rm.getContent().compareTo(content) < 0) { //se rm.content mais pequeno
					
					if (less == -1) {
						
						NodeActor na = new NodeActor(this);
						registerChild(na);
						na.receiveMessage(rm);
						na.start();
						if (greater == -1) {less = 0;}
						else {less = 1;}
	
					}else {
						getChildren().get(less).receiveMessage(new RequestMessage(rm.getId(), this, rm.getBinaryTreeActor(),
								rm.getClient(), rm.getOp(), rm.getContent(),-1));
					}
					
					
				}else {//se rm.content for maior
					
					if (greater == -1) {
						
						NodeActor na = new NodeActor(this);
						registerChild(na);
						na.receiveMessage(rm);
						na.start();
						if (less == -1) {greater = 0;}
						else {greater = 1;}
	
					}else {
						getChildren().get(greater).receiveMessage(new RequestMessage(rm.getId(), this, rm.getBinaryTreeActor(),
													rm.getClient(), rm.getOp(), rm.getContent(),-1));
					}
					
				}
				
				
			}else if (rm.getOp() == 1) { // delete
				
				
				if (content.compareTo(rm.getContent()) == 0) {
					
					isDeleted = true;
					
					if (less == -1 && greater == -1) { //se for folha
						
						KillMeMessage km = new KillMeMessage(this,rm.getLastIndex(),rm.getId(),rm.getOp(),rm.getContent(),rm.getBinaryTreeActor(),rm.getClient());
						rm.getSender().receiveMessage(km);
						
					}else if(less != -1 && greater == -1) {//se so tiver less
						
						rm.getSender().receiveMessage(new PleaseHaveMyLessSonMessage(this,getChildren().get(less),rm.getId(),rm.getOp(),rm.getContent(),rm.getBinaryTreeActor(),rm.getClient()));
						
					}else if(less == -1 && greater != -1) { //se so tiver greater
						
						
						rm.getSender().receiveMessage(new PleaseHaveMyGreaterSonMessage(this,getChildren().get(greater),rm.getId(),rm.getOp(),rm.getContent(),rm.getBinaryTreeActor(),rm.getClient()));
						
						
					}else { //se tiver os 2
						
						getChildren().get(greater).receiveMessage(new GiveMeLastLessSonMessage(this,this,rm.getId(),rm.getOp(),rm.getContent(),rm.getBinaryTreeActor(),rm.getClient()));
						
						
						
					}
					
				}else if(rm.getContent().compareTo(content) < 0){ 
						
					if (less == -1) {
						ResponseMessage rms = new ResponseMessage(this, rm.getClient(), rm.getId(), 
											rm.getOp(), rm.getContent(), "Não existe para apagar.. :'( ");
						rm.getBinaryTreeActor().receiveMessage(rms);
					}else {
						rm.setLastIndex(less);
						getChildren().get(less).receiveMessage(new RequestMessage(rm.getId(), this, rm.getBinaryTreeActor(),
								rm.getClient(), rm.getOp(), rm.getContent(),less));
					}
					
					
				}else {
					
					if (greater == -1) {
						ResponseMessage rms = new ResponseMessage(this, rm.getClient(), rm.getId(), 
								rm.getOp(), rm.getContent(), "Não existe para apagar.. :'( ");
						rm.getBinaryTreeActor().receiveMessage(rms);
					}else {
						rm.setLastIndex(greater);
						
						getChildren().get(greater).receiveMessage(new RequestMessage(rm.getId(), this, rm.getBinaryTreeActor(),
								rm.getClient(), rm.getOp(), rm.getContent(),greater));
					}
				}
				
				
				
				
			}else if(rm.getOp() == 2) { //contains
				
				if (content.compareTo(rm.getContent()) == 0) {
					
					ResponseMessage resp = new ResponseMessage(this, rm.getClient(), rm.getId(),
															rm.getOp(), rm.getContent(), "Encontrei! 0:)");
					
					rm.getBinaryTreeActor().receiveMessage(resp);
					
				}else if (rm.getContent().compareTo(content) < 0) {
					
					if (less == -1) {
						
						ResponseMessage resp = new ResponseMessage(this, rm.getClient(), rm.getId(),
								rm.getOp(), rm.getContent(), "Não encontrei.. :(");
						
						rm.getBinaryTreeActor().receiveMessage(resp);
						
					}else {
						getChildren().get(less).receiveMessage(new RequestMessage(rm.getId(), this, rm.getBinaryTreeActor(),
								rm.getClient(), rm.getOp(), rm.getContent(),-1));
					}
					
					
				}else {
					
					if (greater == -1) {
						
						ResponseMessage resp = new ResponseMessage(this, rm.getClient(), rm.getId(),
								rm.getOp(), rm.getContent(), "Não encontrei.. :(");
						
						rm.getBinaryTreeActor().receiveMessage(resp);
						
					}else {
						getChildren().get(greater).receiveMessage(new RequestMessage(rm.getId(), this, rm.getBinaryTreeActor(),
								rm.getClient(), rm.getOp(), rm.getContent(),-1));
					}
					
				}
				
			}
			
		}
		
		if (m instanceof KillMessage) {
			die();
		}
		
		if (m instanceof KillMeMessage) {
			KillMeMessage km = (KillMeMessage) m;
			if (km.getAutorIndex() == greater) {
				greater = -1;
			}else if(km.getAutorIndex() == less) {
				less = -1;
				
			}
			if (km.getId() != -1) {
				ResponseMessage resp = new ResponseMessage(this, km.getClient(), km.getId(), km.getOp(), km.getContent(), "Eliminado! :)");
				km.getBinaryTree().receiveMessage(resp);
			}
			
			getChildren().get(km.getAutorIndex()).receiveMessage(new KillMessage(this));
			
		}
		
		if (m instanceof PleaseHaveMyLessSonMessage) {
			PleaseHaveMyLessSonMessage lsm = (PleaseHaveMyLessSonMessage) m;
			lsm.getSender().receiveMessage(new JustSuicide(this));
			setChild(less,lsm.getSon());
			ResponseMessage resp = new ResponseMessage(this, lsm.getClient(), lsm.getId(), lsm.getOp(), lsm.getContent(), "Eliminado! :)");
			lsm.getBinaryTree().receiveMessage(resp);
		}
		
		if (m instanceof PleaseHaveMyGreaterSonMessage) {
			PleaseHaveMyGreaterSonMessage gsm = (PleaseHaveMyGreaterSonMessage) m;
			gsm.getSender().receiveMessage(new JustSuicide(this));
			setChild(greater,gsm.getSon());
			if (gsm.getId()!=-1) {
				ResponseMessage resp = new ResponseMessage(this, gsm.getClient(), gsm.getId(), gsm.getOp(), gsm.getContent(), "Eliminado! :)");
				gsm.getBinaryTree().receiveMessage(resp);
			}
			
		}
		
		if (m instanceof JustSuicide ) {
			justDie();
		}
		
		if (m instanceof GiveMeLastLessSonMessage) {
			
			GiveMeLastLessSonMessage llsm = (GiveMeLastLessSonMessage) m;
			
			if (isDeleted) {
				m.getSender().receiveMessage(m);
			}else if(less != -1) {
				getChildren().get(less).receiveMessage(llsm);
			}else {
				CanSwapMessage swpm = new CanSwapMessage(this,content,llsm.getId(),llsm.getOp(),llsm.getContent(),llsm.getBinaryTree(),llsm.getClient());
				llsm.getToDelete().receiveMessage(swpm);
				
			}
			
		}
		
		if (m instanceof CanSwapMessage) {
			
			CanSwapMessage csm = (CanSwapMessage) m;
			
			content = csm.getContentToSwap();
			
			isDeleted = false;
			
			getChildren().get(greater).receiveMessage(new RequestMessage(-1,this,null,null,1,content,-1));
			
			ResponseMessage resp = new ResponseMessage(this, csm.getClient(), csm.getId(), csm.getOp(), csm.getContent(), "Eliminado! :)");
			csm.getBinaryTree().receiveMessage(resp);
			
		}
		
	}

}
