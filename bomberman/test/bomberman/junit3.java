package bomberman;
import game.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class junit3 {

	private GameBoard gameBoard;
    private GameBoardPanel gameBoardPanel;
    private Player player;
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        // Initialize a 5x5 game board
        gameBoard = new GameBoard(13, 13);

        // Initialize the game board panel
        gameBoardPanel = new GameBoardPanel(gameBoard);

        // Place the player at a fixed position (this is optional depending on the test)
        player = new Player(gameBoard, 3, gameBoardPanel);

        // Place the enemy at position (2, 2) moving "up"
        enemy = new Enemy(11, 1, gameBoard, player, "up", gameBoardPanel);
    }

    @Test
    public void testEnemyMovesInSpecifiedDirection() {
        // Act: Move the enemy
        enemy.move();

        // Assert: The enemy should move up (row decreases)
        assertEquals(10, enemy.getX(), "Enemy should move up to row 1");
        assertEquals(1, enemy.getY(), "Enemy should remain in the same column (2)");
    }

    @Test
    public void testEnemyChangesDirectionWhenBlocked() {
    	
    	enemy = new Enemy(11, 1, gameBoard, player, "down", gameBoardPanel);
        // Arrange: Place a wall above the enemy
        gameBoard.setTile(1, 2, 'W'); // 'W' represents a wall

        // Act: Move the enemy
        enemy.move();

        // Assert: The enemy should change direction to "down" and move down
        assertEquals(11, enemy.getX(), "Enemy should move down to row 3 after encountering a wall");
        assertEquals(1, enemy.getY(), "Enemy should remain in the same column (2)");
    }

    @Test
    public void testEnemyDoesNotMoveToInvalidPosition() {
    	
    	enemy = new Enemy(11, 1, gameBoard, player, "down", gameBoardPanel);
        // Arrange: Place a wall above and below the enemy
        gameBoard.setTile(10, 1, 'W'); // 'W' represents a wall
        gameBoard.setTile(3, 2, 'W'); // 'W' represents a wall

        // Act: Move the enemy
        enemy.move();

        // Assert: The enemy should remain in the same position (2, 2)
        assertEquals(11, enemy.getX(), "Enemy should not move when blocked on all sides");
        assertEquals(1, enemy.getY(), "Enemy should remain in column 2");
    }

    @Test
    public void testEnemyDestroysPlayer() {
        // Arrange: Place the player directly above the enemy
    	gameBoard.setTile(10, 1, 'E');
        player = new Player(gameBoard, 3, gameBoardPanel);
        player.updatePosition(10, 1); // Set player position to (1, 2)
        enemy = new Enemy(11, 1, gameBoard, player, "up", gameBoardPanel);

        // Act: Move the enemy
        enemy.move();
        enemy.move();

        // Assert: The player should lose a life
        assertEquals(2, player.getLives(), "Player should lose a life when touched by the enemy");
    }
}