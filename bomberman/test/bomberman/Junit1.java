package bomberman;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.*;

import java.util.ArrayList;

class Junit1 {

	private GameBoard gameBoard;
    private Player player;
    private GameBoardPanel gameBoardPanel;
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
        // Add three bombs to the game board
        Bomb bomb1 = new Bomb(1, 1, gameBoard, gameBoardPanel, player, enemies);
        Bomb bomb2 = new Bomb(2, 2, gameBoard, gameBoardPanel, player, enemies);
        Bomb bomb3 = new Bomb(3, 3, gameBoard, gameBoardPanel, player, enemies);
        
        gameBoard.addBomb(bomb1);
        gameBoard.addBomb(bomb2);
        gameBoard.addBomb(bomb3);

        // Try to place a fourth bomb
        player.placeBomb();
        
        // Assert that there are still only 3 bombs on the board
        assertEquals(3, gameBoard.getActiveBombs().size(), "Player should not be able to place more than 3 bombs");
    }
}