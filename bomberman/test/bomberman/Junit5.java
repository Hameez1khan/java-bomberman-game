package bomberman;

import game.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Junit5 {

	private Player player;
    private GameBoard gameBoard;
    private GameBoardPanel gameBoardPanel;
    private ArrayList<Enemy> enemies;

    @BeforeEach
    public void setUp() {
        // Initialize the game board and player
        gameBoard = new GameBoard(5, 5);
        gameBoardPanel = new GameBoardPanel(gameBoard);
        player = new Player(gameBoard, 3, gameBoardPanel); // 3 lives for the player

        // Initialize enemies and link to the player
        enemies = new ArrayList<>();
        enemies.add(new Enemy(3, 3, gameBoard, player, "down", gameBoardPanel));
        player.setEnemies(enemies);
    }

    @Test
    public void testDeserialization() {
        try {
            // Serialize the objects
            File tempFile = File.createTempFile("game_save", ".ser");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {
                oos.writeObject(gameBoard);
                oos.writeObject(player);
                oos.writeObject(enemies);
            }

            // Deserialize the objects
            GameBoard deserializedGameBoard = null;
            Player deserializedPlayer = null;
            ArrayList<Enemy> deserializedEnemies = null;

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFile))) {
                deserializedGameBoard = (GameBoard) ois.readObject();
                deserializedPlayer = (Player) ois.readObject();
                deserializedEnemies = (ArrayList<Enemy>) ois.readObject();
            }

            // Delete the temporary file after test
            tempFile.delete();

            // Assertions to verify deserialization
            assertNotNull(deserializedGameBoard, "Deserialized GameBoard should not be null");
            assertNotNull(deserializedPlayer, "Deserialized Player should not be null");
            assertNotNull(deserializedEnemies, "Deserialized Enemies list should not be null");

            // Verify GameBoard properties
            assertEquals(gameBoard.getRows(), deserializedGameBoard.getRows(), "Rows should match after deserialization");
            assertEquals(gameBoard.getColumns(), deserializedGameBoard.getColumns(), "Columns should match after deserialization");

            // Verify Player properties
            assertEquals(player.getLives(), deserializedPlayer.getLives(), "Player lives should match after deserialization");
            assertEquals(player.getX(), deserializedPlayer.getX(), "Player X position should match after deserialization");
            assertEquals(player.getY(), deserializedPlayer.getY(), "Player Y position should match after deserialization");

            // Verify Enemy properties
            assertEquals(enemies.size(), deserializedEnemies.size(), "Enemy count should match after deserialization");
            for (int i = 0; i < enemies.size(); i++) {
                assertEquals(enemies.get(i).getX(), deserializedEnemies.get(i).getX(), "Enemy X position should match after deserialization");
                assertEquals(enemies.get(i).getY(), deserializedEnemies.get(i).getY(), "Enemy Y position should match after deserialization");
            }

        } catch (Exception e) {
            fail("Deserialization test failed: " + e.getMessage());
        }
    }
}