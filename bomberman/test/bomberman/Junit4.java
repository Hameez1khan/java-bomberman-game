package bomberman;
import game.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Junit4 {

	private Player player;
    private GameBoard gameBoard;
    private GameBoardPanel gameBoardPanel;

    @BeforeEach
    public void setUp() {
        // Initialize the game board and player
        gameBoard = new GameBoard(5, 5);
        gameBoardPanel = new GameBoardPanel(gameBoard);
        player = new Player(gameBoard, 3, gameBoardPanel); // 3 lives for the player
    }

    @Test
    public void testPlayerSerialization() {
        Player deserializedPlayer = null;
        try {
            // Serialize the player
            File tempFile = File.createTempFile("player", ".ser");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
                oos.writeObject(player);
            }

            // Deserialize the player
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFile))) {
                deserializedPlayer = (Player) ois.readObject();
            }

            // Delete the temporary file after test
            tempFile.delete();

        } catch (Exception e) {
            fail("Serialization or deserialization failed: " + e.getMessage());
        }

        // Assert that the deserialized player is not null
        assertNotNull(deserializedPlayer, "Deserialized player should not be null");

        // Assert that the deserialized player's properties match the original
        assertEquals(player.getX(), deserializedPlayer.getX(), "Player X position should match after deserialization");
        assertEquals(player.getY(), deserializedPlayer.getY(), "Player Y position should match after deserialization");
        assertEquals(player.getLives(), deserializedPlayer.getLives(), "Player lives should match after deserialization");
    }

    @Test
    public void testGameBoardSerialization() {
        GameBoard deserializedGameBoard = null;
        try {
            // Serialize the game board
            File tempFile = File.createTempFile("gameboard", ".ser");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
                oos.writeObject(gameBoard);
            }

            // Deserialize the game board
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFile))) {
                deserializedGameBoard = (GameBoard) ois.readObject();
            }

            // Delete the temporary file after test
            tempFile.delete();

        } catch (Exception e) {
            fail("Serialization or deserialization failed: " + e.getMessage());
        }

        // Assert that the deserialized game board is not null
        assertNotNull(deserializedGameBoard, "Deserialized game board should not be null");

        // Assert that the game board properties match
        assertEquals(gameBoard.getRows(), deserializedGameBoard.getRows(), "GameBoard rows should match after deserialization");
        assertEquals(gameBoard.getColumns(), deserializedGameBoard.getColumns(), "GameBoard columns should match after deserialization");
    }
}