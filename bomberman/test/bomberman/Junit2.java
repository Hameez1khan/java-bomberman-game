package bomberman;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.*;

class Junit2 {

	private GameBoard gameBoard;
    private GameBoardPanel gameBoardPanel;
    private Player player;
    private ArrayList<Enemy> enemies;

    @BeforeEach
    public void setup() {
        // Initialize the game board
        gameBoard = new GameBoard(13, 13);

        // Create the game board panel
        gameBoardPanel = new GameBoardPanel(gameBoard);

        // Initialize the player
        player = new Player(gameBoard, 3, gameBoardPanel);

        // Initialize enemies and set it to the player
        enemies = new ArrayList<>();
        player.setEnemies(enemies); // Set the enemies list in the player
    }

    @Test
    public void testPlaceBombLimit() {
        // Act: Place bombs up to the limit
        player.placeBomb(); // 1st bomb
        player.moveDown();

        player.placeBomb(); // 2nd bomb
        player.moveDown();
        player.placeBomb(); // 3rd bomb
        player.moveDown();
        
        // Assert: The player should not be able to place more than 3 bombs
        ArrayList<Bomb> activeBombs = gameBoard.getActiveBombs();
        assertEquals(3, activeBombs.size(), "Player should only be able to place up to 3 bombs");

        // Attempt to place another bomb
        player.placeBomb();

        // Assert: The number of bombs should still be 3
        assertEquals(3, activeBombs.size(), "Player should not be able to place more than 3 bombs");
    }

}