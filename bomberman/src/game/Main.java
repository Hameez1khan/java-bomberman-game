package game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create the starting menu frame
        JFrame menuFrame = new JFrame("Bomberman Game Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(400, 300);

        // Create a JTable with menu options
        String[] columnNames = {"Options"};
        Object[][] data = {
            {"Start New Game"},
            {"Load Saved Game"},
            {"Exit Game"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };

        JTable menuTable = new JTable(tableModel);
        menuTable.setRowHeight(40); // Set row height for better visibility
        menuTable.setFont(new Font("Arial", Font.PLAIN, 18));
        menuTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));

        // Add a mouse listener to handle row selection
        menuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = menuTable.getSelectedRow();
                if (selectedRow == 0) {
                    menuFrame.dispose();
                    startNewGame();
                } else if (selectedRow == 1) {
                    menuFrame.dispose();
                    loadGame();
                } else if (selectedRow == 2) {
                    System.exit(0);
                }
            }
        });

        // Add the table to a scroll pane (though it doesn't scroll in this case)
        JScrollPane scrollPane = new JScrollPane(menuTable);

        // Set up the layout
        menuFrame.add(scrollPane);
        menuFrame.setLocationRelativeTo(null); // Center the frame
        menuFrame.setVisible(true);
    }
    
    public static void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saved_game.ser"))) {
            GameBoard gameBoard = (GameBoard) ois.readObject();
            Player player = (Player) ois.readObject();
            ArrayList<Enemy> enemies = (ArrayList<Enemy>) ois.readObject();
            ArrayList<Bomb> activeBombs = gameBoard.getActiveBombs();
            
            GameBoardPanel gameBoardPanel = new GameBoardPanel(gameBoard);
            gameBoard.setPlayer(player);
            player.reinitialize(gameBoard, gameBoardPanel);
            player.setEnemies(enemies);
            
            for (Enemy enemy : enemies) {
                enemy.setGameBoard(gameBoard); // Reassign the gameBoard
                enemy.setGameBoardPanel(gameBoardPanel); // Reassign the panel
                enemy.restartMovement(); // Restart the Timer tasks
            }
            
            for (Bomb bomb : activeBombs) {
                bomb.setGameBoard(gameBoard);
                bomb.setGameBoardPanel(gameBoardPanel);
                bomb.restartTimer(); // Restart bomb timers
            }

            JFrame frame = new JFrame("Bomberman Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenuItem saveAndExitItem = new JMenuItem("Save Game and Exit");
            saveAndExitItem.addActionListener(e -> saveGameAndExit(gameBoard, player, enemies));
            fileMenu.add(saveAndExitItem);
            menuBar.add(fileMenu);
            frame.setJMenuBar(menuBar);
            
            
            frame.add(gameBoardPanel);
            frame.pack();
            frame.setVisible(true);

            setupPlayerControls(player, gameBoardPanel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to load game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            main(null); // Restart main menu
        }
    }

    // Method to start a new game
    public static void startNewGame() {
        // Create a 13x13 game board
        GameBoard gameBoard = new GameBoard(13, 13);

        // Create the game board panel to render the map
        GameBoardPanel gameBoardPanel = new GameBoardPanel(gameBoard);

        // Create the player at the top-left corner with 3 lives
        Player player = new Player(gameBoard, 3, gameBoardPanel);

        // Link the player to the game board
        gameBoard.setPlayer(player);
        
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(11, 1, gameBoard, player, "up", gameBoardPanel));
        enemies.add(new Enemy(11, 11, gameBoard, player, "left", gameBoardPanel));
        enemies.add(new Enemy(1, 11, gameBoard, player, "down", gameBoardPanel));
        player.setEnemies(enemies);
        
        
        // Create the frame to display the game
        JFrame frame = new JFrame("Bomberman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveAndExitItem = new JMenuItem("Save Game and Exit");
        saveAndExitItem.addActionListener(e -> saveGameAndExit(gameBoard, player, enemies));
        fileMenu.add(saveAndExitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        
        // Add the GameBoardPanel to the frame
        frame.add(gameBoardPanel);

        // Fit the frame to the preferred size of the panel
        frame.pack();

        // Set the frame to be visible
        frame.setVisible(true);

        // Set up player controls (WASD keys for movement, spacebar for placing bombs)
        setupPlayerControls(player, gameBoardPanel);
    }
    
    
    public static void saveGameAndExit(GameBoard gameBoard, Player player, ArrayList<Enemy> enemies) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saved_game.ser"))) {
            oos.writeObject(gameBoard);
            oos.writeObject(player);
            oos.writeObject(enemies);
            JOptionPane.showMessageDialog(null, "Game saved successfully!", "Save Game", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to save game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Set up player controls (keyboard event handling)
    private static void setupPlayerControls(Player player, GameBoardPanel gameBoardPanel) {
        gameBoardPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                // Check keyCode for WASD keys
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_W: // Move up
                        player.moveUp();
                        break;
                    case java.awt.event.KeyEvent.VK_S: // Move down
                        player.moveDown();
                        break;
                    case java.awt.event.KeyEvent.VK_A: // Move left
                        player.moveLeft();
                        break;
                    case java.awt.event.KeyEvent.VK_D: // Move right
                        player.moveRight();
                        break;
                    case java.awt.event.KeyEvent.VK_SPACE: // Place bomb
                        player.placeBomb();
                        break;
                }
                gameBoardPanel.repaint(); // Redraw the board after the player's move
            }
        });

        gameBoardPanel.setFocusable(true); // Ensure the panel can capture keyboard focus
        gameBoardPanel.requestFocusInWindow();
    }
}