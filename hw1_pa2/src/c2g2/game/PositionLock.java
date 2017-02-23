package c2g2.game;

public class PositionLock {

	private float moveStep = 0;

	public PositionLock(){
		
	}
	
	public void increment(float step){
		moveStep+=step;
	}
	
	public boolean canMove(){
		return (moveStep>-2.0f && moveStep<2.0f);
	}
	
	public boolean isMoving(){
		return moveStep!=0;
	}
	
	public void free(){
		moveStep = 0;
	}
}
