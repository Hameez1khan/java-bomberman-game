package game;


import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private int x, y;  // Player's current position
    private int lives; // Number of lives the player has
    private transient GameBoard gameBoard;
    private transient GameBoardPanel gameBoardPanel;
    private ArrayList<Enemy> enemies;

    // Constructor to initialise the player with a starting position
    public Player(GameBoard gameBoard, int startLives , GameBoardPanel gameBoardPanel ) {
        this.gameBoard = gameBoard;
        this.lives = startLives;
        this.gameBoardPanel = gameBoardPanel;
        

        // Initialize player at the top-left corner (1, 1) and mark it on the board
        this.x = 1;
        this.y = 1;
        gameBoard.setTile(x, y, 'P'); // 'P' represents the player
    }
    
    public void reinitialize(GameBoard gameBoard, GameBoardPanel gameBoardPanel) {
        this.gameBoard = gameBoard;
        this.gameBoardPanel = gameBoardPanel;
    }
    
    public ArrayList<Enemy> getEnemies() {
        return enemies; 
    }

    // Getters and Setters for position and lives
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }
    
   public Player getPlayer() {
	   return this;
   }
   
   public void setEnemies( ArrayList<Enemy> enemies ) {
	   this.enemies = enemies;
   }
   
    // Update player's position and mark it on the board
    public void updatePosition(int newX, int newY) {
        ArrayList<Bomb> activeBombs = gameBoard.getActiveBombs();
        
        // Check and restore the bomb tile if a bomb is present at the current position
        boolean isBombHere = false;
        for (Bomb bomb : activeBombs) {
            if (bomb.getX() == x && bomb.getY() == y) {
                isBombHere = true;
                break;
            }
        }

        // If there's a bomb, keep the tile as 'G', otherwise mark it as empty
        if (isBombHere) {
            gameBoard.setTile(x, y, 'G');
        } else {
            gameBoard.setTile(x, y, 'E');
        }

        // Update to the new position
        this.x = newX;
        this.y = newY;
        gameBoard.setTile(x, y, 'P'); // Mark the new position as the player
    }

    // Move the player up
    public void moveUp() {
        if (y > 0 && gameBoard.getTile(x - 1, y) == 'E') {
            updatePosition(x - 1, y); // Decrease x to move up
        }
        if (y > 0 && gameBoard.getTile(x - 1, y) == 'A') loseLife();
    }

    // Move the player down
    public void moveDown() {
        if (y < gameBoard.getRows() - 1 && gameBoard.getTile(x + 1, y) == 'E') {
            updatePosition(x + 1, y); // Increase x to move down
        }
        if (y > 0 && gameBoard.getTile(x + 1, y) == 'A') loseLife();
    }

    // Move the player left
    public void moveLeft() {
        if (x > 0 && gameBoard.getTile(x, y - 1) == 'E') {
            updatePosition(x, y - 1); // Decrease y to move left
        }
        if (y > 0 && gameBoard.getTile(x, y - 1) == 'A') loseLife();
    }

    // Move the player right
    public void moveRight() {
        if (x < gameBoard.getColumns() - 1 && gameBoard.getTile(x, y + 1) == 'E') {
            updatePosition(x , y + 1); // Increase x to move right
        }
        if (y > 0 && gameBoard.getTile(x , y + 1) == 'A') loseLife();
    }

    // Place a bomb at the current position
    public void placeBomb() {
    	ArrayList<Bomb> activeBombs = gameBoard.getActiveBombs();
    	
    	for(Enemy enemy : enemies){
    		if( enemy.getX() == x && enemy.getY() == y) {
    			return;
    		}
    	}
    	
    	if(activeBombs.size() >= 3) return;
    	
    	for (Bomb bomb : activeBombs) {
            if (bomb.getX() == x && bomb.getY() == y) {
                return;
            }
        }
    	
        Bomb bomb = new Bomb(x, y, gameBoard, gameBoardPanel, this , enemies);
        gameBoard.addBomb(bomb);  // Adds the bomb to the game board
        
        //gameBoard.removeBomb(bomb);
    }
}