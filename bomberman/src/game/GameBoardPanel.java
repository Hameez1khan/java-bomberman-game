package game;

import javax.swing.*;
import java.awt.*;

public class GameBoardPanel extends JPanel {
    private GameBoard gameBoard;
    private static final int TILE_SIZE = 40;  // Size of each tile in pixels

    // Define colors for tiles (rendering colors)
    private static final Color WALL_COLOR = Color.BLACK;   // Indestructible Wall Color
    private static final Color EMPTY_COLOR = Color.WHITE;  // Empty Space Color
    private static final Color BREAKABLE_COLOR = new Color(139, 69, 19); // Breakable Wall Color
    private static final Color PLAYER_COLOR = Color.BLUE;   // Player Color
    private static final Color BOMB_COLOR = Color.GRAY;     // Bomb Color (Grey)
    private static final Color ENEMY_COLOR = Color.RED;
    
    public GameBoardPanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        setPreferredSize(new Dimension(gameBoard.getColumns() * TILE_SIZE, gameBoard.getRows() * TILE_SIZE));
    }
   
    private boolean gameOverDisplayed = false;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < gameBoard.getRows(); row++) {
            for (int col = 0; col < gameBoard.getColumns(); col++) {
                char tile = gameBoard.getTile(row, col);

                // Prioritize the player visually over other elements
                if (tile == 'P') {
                    g.setColor(PLAYER_COLOR); // Player
                } else if (tile == 'G') {
                    g.setColor(BOMB_COLOR); // Bomb
                } else if (tile == 'W') {
                    g.setColor(WALL_COLOR); // Indestructible Wall
                } else if (tile == 'B') {
                    g.setColor(BREAKABLE_COLOR); // Breakable Wall
                } else if (tile == 'A') {
                    g.setColor(ENEMY_COLOR);
                } else {
                    g.setColor(EMPTY_COLOR); // Empty space
                }

                // Draw the tile
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        
        Player player = gameBoard.getPlayer();
        if (player.getLives() <= 0 && !gameOverDisplayed) {
            gameOverDisplayed = true; // Prevent multiple game-over logic executions

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            String message = "GAME OVER";
            FontMetrics metrics = g.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(message)) / 2;
            int y = getHeight() / 2;
            g.drawString(message, x, y);

            // Return to the main menu after a delay
            javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
                SwingUtilities.getWindowAncestor(this).dispose(); // Close the game window
                Main.main(new String[0]); // Return to the main menu
            });
            timer.setRepeats(false); // Execute only once
            timer.start();
        }

        // Display player lives
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Lives: " + player.getLives(), 10, 30);
    }
}