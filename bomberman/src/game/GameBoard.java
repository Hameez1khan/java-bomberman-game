package game;

import java.io.Serializable;
import java.util.ArrayList;

public class GameBoard implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private int rows;
    private int columns;
    private Player player;
    private char[][] board;  // Map data structure
    private ArrayList<Bomb> activeBombs;  // List of active bombs on the board

    // Constructor to initialize the game board with a given size
    public GameBoard(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new char[rows][columns];
        this.activeBombs = new ArrayList<>();
        createMap();
    }

    // Creates the map dynamically based on the provided map layout
    private void createMap() {
        // Define the hardcoded layout based on your provided map
        char[][] predefinedMap = {
            {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'},
            {'W', 'E', 'B', 'B', 'E', 'E', 'B', 'B', 'B', 'E', 'E', 'E', 'W'},
            {'W', 'E', 'W', 'W', 'B', 'W', 'B', 'W', 'E', 'W', 'E', 'E', 'W'},
            {'W', 'E', 'B', 'E', 'B', 'W', 'B', 'E', 'B', 'B', 'E', 'E', 'W'},
            {'W', 'E', 'W', 'E', 'W', 'E', 'W', 'E', 'W', 'E', 'W', 'E', 'W'},
            {'W', 'E', 'B', 'E', 'B', 'E', 'B', 'E', 'B', 'E', 'E', 'E', 'W'},
            {'W', 'E', 'W', 'B', 'W', 'B', 'W', 'B', 'W', 'E', 'W', 'E', 'W'},
            {'W', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'W'},
            {'W', 'E', 'W', 'E', 'W', 'E', 'W', 'E', 'W', 'B', 'W', 'E', 'W'},
            {'W', 'E', 'B', 'B', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'W'},
            {'W', 'E', 'W', 'E', 'W', 'E', 'W', 'E', 'W', 'E', 'W', 'E', 'W'},
            {'W', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'W'},
            {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'}
        };

        // Fill the board with the predefined map
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = predefinedMap[i][j];
            }
        }
    }

    // Adds a bomb to the activeBombs list
    public void addBomb(Bomb bomb) {
        activeBombs.add(bomb);
    }
    
    public void removeBomb(Bomb bomb) {
        activeBombs.remove(bomb);
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }

    // Returns the tile type at the given position
    public char getTile(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < columns) {
            return board[row][col];
        }
        return '\0'; // Invalid position
    }

    // Getter for map size (rows x columns)
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    // Getter for active bombs
    public ArrayList<Bomb> getActiveBombs() {
        return activeBombs;
    }

    // Set the tile at the given position (used for bomb explosion)
    public void setTile(int row, int col, char tileType) {
        if (row >= 0 && row < rows && col >= 0 && col < columns) {
            board[row][col] = tileType;
        }
    }
}