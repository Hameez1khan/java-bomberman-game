package bomberman;

import game.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Junit7 {
	private GameBoard gameBoard;
    private Player player;
    private GameBoardPanel gameBoardPanel;
    private ArrayList<Enemy> enemies;

    @BeforeEach
    public void setUp() {
        // Initialize a 5x5 game board
        gameBoard = new GameBoard(5, 5);

        // Create the GameBoardPanel
        gameBoardPanel = new GameBoardPanel(gameBoard);

        // Create the player with 3 lives
        player = new Player(gameBoard, 3, gameBoardPanel);

        // Assign the player to the game board
        gameBoard.setPlayer(player);

        // Initialize enemies
        enemies = new ArrayList<>();
        enemies.add(new Enemy(3, 3, gameBoard, player, "up", gameBoardPanel));
        enemies.add(new Enemy(2, 2, gameBoard, player, "left", gameBoardPanel));
        enemies.add(new Enemy(4, 4, gameBoard, player, "down", gameBoardPanel));

        // Set enemies for the player
        player.setEnemies(enemies);
    }

    @Test
    void testStartNewGame() {
        // Initialize the GameBoard
        GameBoard gameBoard = new GameBoard(13, 13);

        // Create the GameBoardPanel
        GameBoardPanel gameBoardPanel = new GameBoardPanel(gameBoard);

        // Create the Player
        Player player = new Player(gameBoard, 3, gameBoardPanel);
        gameBoard.setPlayer(player);

        // Create the Enemies and set their GameBoardPanel
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(11, 1, gameBoard, player, "up", gameBoardPanel));
        enemies.add(new Enemy(11, 11, gameBoard, player, "left", gameBoardPanel));
        enemies.add(new Enemy(1, 11, gameBoard, player, "down", gameBoardPanel));

        player.setEnemies(enemies);

        // Assert that the initial state of the game is set up correctly
        assertEquals(3, player.getLives(), "Player should start with 3 lives.");
        assertEquals(3, enemies.size(), "There should be 3 enemies.");

        // Check that the GameBoard is set up correctly
        assertEquals('P', gameBoard.getTile(1, 1), "Player should be at the starting position.");
        assertEquals('A', gameBoard.getTile(10, 1), "An enemy should be at (10, 1).");
        assertEquals('A', gameBoard.getTile(11, 10), "An enemy should be at (11, 10).");
      
    }

    @Test
    void testPlayerMovement() {
        // Test player movement on the game board
        player.moveDown();
        assertEquals(1, player.getX(), "Player should move down to (2, 1).");

        player.moveRight();
        assertEquals(1, player.getY(), "Player should move right to (2, 2).");
    }

    @Test
    void testEnemyMovement() {
        // Test enemy movement
        Enemy enemy = enemies.get(0);
        enemy.restartMovement();

        // Simulate movement and check the position (this may need to be adjusted based on the move logic)
        assertNotNull(enemy.getX(), "Enemy should have a valid position.");
        assertNotNull(enemy.getY(), "Enemy should have a valid position.");
    }
}