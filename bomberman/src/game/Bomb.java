package game;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.io.Serializable;

import javax.swing.SwingUtilities;

public class Bomb implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private int x, y;  // Bomb's position on the grid
   
    private transient Timer timer;
    private int timeDuration; // Count down timer for the bomb (in seconds)
    private GameBoard gameBoard;
    private GameBoardPanel gameBoardPanel;
    private boolean exploded; // To check if the bomb exploded
    private Player player;
    private ArrayList<Enemy> enemies;
   
    //Constructor
    public Bomb(int x, int y, GameBoard gameBoard, GameBoardPanel gameBoardPanel , Player player, ArrayList<Enemy>  enemies) {
        this.x = x;
        this.y = y;
        this.gameBoard = gameBoard;
        this.gameBoardPanel = gameBoardPanel;
        this.timeDuration = 3000;  // Default bomb timer (3 seconds)
        this.exploded = false;
        this.player = player;
        this.enemies = enemies;
        startTimer();
    }
    
    
    public void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                explode();
                gameBoardPanel.repaint();
               
            }
        }, this.timeDuration);
    }
    
    
    
    public void setGameBoard(GameBoard gameBoard) {
    	this.gameBoard = gameBoard;
    }
    
    public void setGameBoardPanel(GameBoardPanel gameBoardPanel) {
    	this.gameBoardPanel = gameBoardPanel;
    }
    
    public void restartTimer() {
        if (timer != null) {
            timer.cancel(); // Cancel the previous timer
        }
        if (!exploded) {
            startTimer();
        }
    }
    
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }


    // Explodes the bomb
    public void explode() {
        if (exploded) return; // Prevent multiple explosions
        exploded = true;

        // Clear the bomb tile itself if no player is present
        if (gameBoard.getTile(x, y) == 'G') {
            gameBoard.setTile(x, y, 'E');
        }

        if (player.getX() == x && player.getY() == y) player.loseLife();

        // Process the blast in each direction
        processBlast(-1, 0); // UP
        processBlast(1, 0);  // DOWN
        processBlast(0, -1); // LEFT
        processBlast(0, 1);  // RIGHT

        // Remove this bomb from the active bombs list
        gameBoard.removeBomb(this);

        // Check if all enemies are destroyed
        if (enemies.isEmpty()) {
            displayWinMessage(gameBoardPanel);
        }
    }
    
    
    private void processBlast(int dx, int dy) {
        for (int i = 1; i <= 3; i++) {
            int targetX = x + dx * i;
            int targetY = y + dy * i;

            // Stop if the blast encounters an unbreakable wall
            if (gameBoard.getTile(targetX, targetY) == 'W') break;

            // Handle breakable walls
            if (gameBoard.getTile(targetX, targetY) == 'B') {
                gameBoard.setTile(targetX, targetY, 'G');
                gameBoardPanel.repaint();
                gameBoard.setTile(targetX, targetY, 'E');
                gameBoardPanel.repaint();
                break;
            }

            // Handle player in the blast radius
            if (gameBoard.getTile(targetX, targetY) == 'P') {
                player.loseLife();
                gameBoard.setTile(targetX, targetY, 'G');
                gameBoardPanel.repaint();
                gameBoard.setTile(targetX, targetY, 'P');
                gameBoardPanel.repaint();
            }

            // Handle enemies in the blast radius
            for (Enemy enemy : new ArrayList<>(enemies)) { // Avoid ConcurrentModificationException
                if (enemy.getX() == targetX && enemy.getY() == targetY) {
                    enemy.destroy();
                    enemies.remove(enemy);
                    gameBoard.setTile(targetX, targetY, 'G');
                    gameBoardPanel.repaint();
                    gameBoard.setTile(targetX, targetY, 'E');
                    gameBoardPanel.repaint();
                    break;
                }
            }
            
            
        }
    }


    private void displayWinMessage(GameBoardPanel gameBoardPanel) {
        // Use SwingUtilities.invokeLater to ensure the drawing happens on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            Graphics g = gameBoardPanel.getGraphics();
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            String message = "YOU WIN!";
            FontMetrics metrics = g.getFontMetrics();
            int x = (gameBoardPanel.getWidth() - metrics.stringWidth(message)) / 2;
            int y = gameBoardPanel.getHeight() / 2;
            g.drawString(message, x, y);

            // Schedule returning to the main menu after a delay
            javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
                SwingUtilities.getWindowAncestor(gameBoardPanel).dispose(); // Close the game window
                Main.main(new String[0]); // Restart the main menu
            });
            timer.setRepeats(false); // Ensure the timer runs only once
            timer.start();
        });
    }
   
}