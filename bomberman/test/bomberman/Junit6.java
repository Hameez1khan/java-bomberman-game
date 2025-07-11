package bomberman;


import game.*; 

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class Junit6 {

	private GameBoard gameBoard;

    @BeforeEach
    public void setUp() {
        // Initialize the game board with a size of 5x5 for testing
        gameBoard = new GameBoard(5, 5);
    }

    @Test
    public void testInitialBoardSetup() {
        // Check the initial setup of the game board
        assertEquals(5, gameBoard.getRows(), "GameBoard should have 5 rows");
        assertEquals(5, gameBoard.getColumns(), "GameBoard should have 5 columns");

        // Verify that the board is initialized correctly with default tiles
        for (int row = 0; row < gameBoard.getRows(); row++) {
            for (int col = 0; col < gameBoard.getColumns(); col++) {
                char tile = gameBoard.getTile(row, col);
                assertTrue(tile == 'W' || tile == 'E' || tile == 'B', "Tile should be 'W', 'E', or 'B'");
            }
        }
    }

    @Test
    public void testSetAndGetTile() {
        // Set a specific tile and verify it is updated correctly
        gameBoard.setTile(2, 2, 'P');
        assertEquals('P', gameBoard.getTile(2, 2), "Tile at (2, 2) should be set to 'P'");

        gameBoard.setTile(1, 1, 'B');
        assertEquals('B', gameBoard.getTile(1, 1), "Tile at (1, 1) should be set to 'B'");
    }

    @Test
    public void testAddAndRemoveBombs() {
        // Create dummy bombs
        Bomb bomb1 = new Bomb(2, 2, gameBoard, null, null, new ArrayList<>());
        Bomb bomb2 = new Bomb(3, 3, gameBoard, null, null, new ArrayList<>());

        // Add bombs to the game board
        gameBoard.addBomb(bomb1);
        gameBoard.addBomb(bomb2);

        // Verify that the bombs are added
        assertEquals(2, gameBoard.getActiveBombs().size(), "There should be 2 active bombs");

        // Remove one bomb and verify
        gameBoard.removeBomb(bomb1);
        assertEquals(1, gameBoard.getActiveBombs().size(), "There should be 1 active bomb");

        // Remove the other bomb and verify
        gameBoard.removeBomb(bomb2);
        assertEquals(0, gameBoard.getActiveBombs().size(), "There should be no active bombs");
    }

    @Test
    public void testGetTileOutOfBounds() {
        // Check that accessing out-of-bounds tiles returns '\0' or invalid
        assertEquals('\0', gameBoard.getTile(-1, -1), "Out-of-bounds tile should return '\\0'");
        assertEquals('\0', gameBoard.getTile(10, 10), "Out-of-bounds tile should return '\\0'");
    }

    @Test
    public void testPlayerAssignment() {
        // Assign a player to the game board
        Player player = new Player(gameBoard, 3, null);
        gameBoard.setPlayer(player);

        // Verify the player is assigned
        assertNotNull(gameBoard.getPlayer(), "Player should be assigned to the game board");
        assertEquals(player, gameBoard.getPlayer(), "The assigned player should match the retrieved player");
    }
}