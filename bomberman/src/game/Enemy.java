package game;

import java.util.Timer;
import java.util.TimerTask;
import java.io.Serializable;

public class Enemy  implements Serializable {
	
	private static final long serialVersionUID = 1L; 
    private int x, y; // Enemy's position on the board
    private GameBoard gameBoard;
    private GameBoardPanel gameBoardPanel;
    private Player player; 
    private boolean destroyed;
    private String direction;
    private transient Timer timer;

    public Enemy(int startX, int startY, GameBoard gameBoard ,Player player ,String direction , GameBoardPanel gameBoardPanel) {
        this.x = startX;
        this.y = startY;
        this.gameBoard = gameBoard;
        this.player = player;
        this.direction = direction;
        this.gameBoardPanel = gameBoardPanel;
        destroyed = false;
        // Place the enemy on the game board
        gameBoard.setTile(x, y, 'A'); // 'A' represents the enemy
        move();
    }

    public void setGameBoard(GameBoard gameBoard) {
    	this.gameBoard = gameBoard;
    }
    
    public void setGameBoardPanel(GameBoardPanel gameBoardPanel) {
    	this.gameBoardPanel = gameBoardPanel;
    }
    
    
    public void restartMovement() {
        if (!destroyed) {
            move(); // Restart the movement logic
        }
    }
    
    // Getter methods for position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void destroy(){
    	if (!destroyed) {
            destroyed = true;
            
          
            
            gameBoard.setTile(x, y, 'E');
            gameBoardPanel.repaint();
        }
    }
    public void move() {
	    timer = new Timer();
	    TimerTask task = new TimerTask() {
	    	@Override 
	    	public void run() {
	    		if(destroyed) { 
	    			gameBoard.setTile(x, y, 'E');
	    			gameBoardPanel.repaint();
	    			this.cancel();
	    			timer.cancel();
	    			return;
	    		}
	    		
	    		else {
		    		switch (direction) {
		            case "up":
		            	moveUp();
		            	gameBoardPanel.repaint();
		                break; 
		            case "down":
		            	moveDown();
		            	gameBoardPanel.repaint();
		                break; 
		            case "left":
		            	moveLeft();
		            	gameBoardPanel.repaint();
		                break; 
		            case "right":
		            	moveRight();
		            	gameBoardPanel.repaint();
		                break; 
		        	}
	    		}
	    		
	    	}
	    	
	    };
	    timer.scheduleAtFixedRate(task, 0 , 1000);
    }
    
    
    private void updatePosition(int newX, int newY) {
        if(player.getX() == x && player.getY() == y) gameBoard.setTile( x, y , 'P' );
        else gameBoard.setTile( x, y , 'E' );
        

        this.x = newX;
        this.y = newY;
        gameBoard.setTile(x, y, 'A');
    }
    
    private void moveUp() {
    	if(gameBoard.getTile(x - 1, y) == 'E' ) 
    		updatePosition(x-1 , y);
    	else if (gameBoard.getTile(x - 1, y) == 'P') {
    		player.loseLife();
    		updatePosition(x-1 , y);
    	}
    	
    	
    	else {
    		changeDirection();
    	}
    	
    }
    
    private void moveDown() {
    	if(gameBoard.getTile(x + 1, y) == 'E' ) 
    		updatePosition(x + 1 , y);
    	else if (gameBoard.getTile(x + 1, y) == 'P') {
    		player.loseLife();
    		updatePosition(x + 1 , y);
    	}
    	else {
    		changeDirection();
    	}
    }
    
    private void moveRight() {
    	if(gameBoard.getTile(x , y + 1) == 'E' ) 
    		updatePosition(x , y + 1);
    	else if (gameBoard.getTile(x , y + 1) == 'P') {
    		player.loseLife();
    		updatePosition(x, y + 1);
    	}
    	else {
    		changeDirection();
    	}
    }
    
    private void moveLeft() {
    	if(gameBoard.getTile(x , y - 1 ) == 'E' ) 
    		updatePosition(x , y - 1);
    	else if (gameBoard.getTile(x, y - 1 ) == 'P') {
    		player.loseLife();
    		updatePosition(x, y -1 );
    	}
    	else {
    		changeDirection();
    	}
    }
    
    private void changeDirection() {
    	switch (direction) {
        case "up":
        	direction = "down";
            
            break; 
        case "down":
        	direction = "up";
         
            break; 
        case "left":
        	direction = "right";
         
            break; 
        case "right":
        	direction = "left";
         
            break; 
    	}
    }
}