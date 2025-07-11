# 💣 Java Bomberman Game

A desktop-based Bomberman-style game built using **Java Swing**. This project replicates the core gameplay mechanics of the classic Bomberman, with real-time player controls, AI-driven enemies, timed bomb explosions, destructible terrain, and save/load functionality. Built to demonstrate OOP, GUI programming, game logic, and Java serialization.

---

## 📑 Table of Contents

- [📸 Screenshots](#-screenshots)
- [🎮 Gameplay Features](#-gameplay-features)
- [🧠 Object-Oriented Design](#-object-oriented-design)
- [🛠 Technologies Used](#-technologies-used)
- [🚀 Getting Started](#-getting-started)
- [🧪 Testing](#-testing)
- [💾 Save/Load Instructions](#-saveload-instructions)
- [🎯 Learning Outcomes](#-learning-outcomes)
- [📂 Project Structure](#-project-structure)
- [🙋 About the Developer](#-about-the-developer)
- [📫 Contact](#-contact)
- [📃 License](#-license)

---

## 📸 Screenshots

### 🎯 Main Menu
<img width="481" height="363" alt="Screenshot 2025-07-11 153013" src="https://github.com/user-attachments/assets/6c7b8c38-8734-4146-b955-346559a4b50b" />

### 💣 Gameplay
<img width="649" height="712" alt="Screenshot 2025-07-11 153024" src="https://github.com/user-attachments/assets/a99ba2e9-4961-4943-94d7-2dbd29b76cb0" />
---

## 🎮 Gameplay Features

- **Grid-Based Game Board**:  
  13x13 layout with different tile types:
  - `W` – Indestructible walls
  - `B` – Breakable walls
  - `A` – Enemies
  - `P` – Player
  - `E` – Empty spaces

- **Player Controls**:
  - `W` `A` `S` `D` → Move
  - `Spacebar` → Place 💣 (up to 3 active bombs)

- **💣 Bomb Mechanics**:
  - Explodes in all four directions after 3 seconds
  - Damages enemies, player, and breakable walls
  - Explosion radius: 3 tiles per direction

- **Enemy AI**:
  - Moves in preset directions
  - Reverses when hitting walls or obstacles
  - Damages the player on collision

- **Game States**:
  - **Victory**: All enemies destroyed
  - **Game Over**: Player loses all lives

- **Save/Load Game**:
  - Save current state using serialization
  - Load saved games to resume progress

- **User Interface**:
  - Java Swing GUI with menu, grid board, and HUD
  - `JTable`-based start menu
  - Display for remaining lives

---

## 🧠 Object-Oriented Design

| Class            | Role                                                                 |
|------------------|----------------------------------------------------------------------|
| `Main`           | Launches menu, handles user choices, game state load/save            |
| `GameBoard`      | Holds map, tile logic, bombs, and player reference                   |
| `GameBoardPanel` | Draws the board using `Graphics`, manages UI updates                 |
| `Player`         | Controls movement, life count, and bomb placement                    |
| `Enemy`          | Autonomous timed movement and interaction with obstacles/player      |
| `Bomb`           | Timer-based explosion logic, affects surroundings                    |

---

## 🛠 Technologies Used

- **Language**: Java
- **GUI Toolkit**: Java Swing
- **Testing**: JUnit 5
- **Persistence**: Java Serialization (Save/Load)

---

## 🚀 Getting Started

### ✅ Prerequisites
- Java 8 or higher
- Eclipse or another Java IDE

### ✅ Prerequisites
- Java JDK 8 or higher
- [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/)
- Git (optional, if cloning via terminal)

### ▶️ Running the Game

1. **Clone the Repository** (or download the ZIP):
   ```bash
   git clone https://github.com/Hameez1khan/java-bomberman-game.git
   ```

2. **Open Eclipse**  
   Go to:
   ```
   File → Import → Existing Projects into Workspace
   ```

3. **Browse to the cloned folder** and select it as the root directory.  
   Ensure the checkbox for your project is selected, then click **Finish**.

4. **Run `Main.java`**  
   In the `game` package, right-click `Main.java` → **Run As → Java Application**

---

## 🧪 Testing

Unit tests written with **JUnit 5** validate:
- Bomb logic
- Player movement
- Enemy pathing
- Map tile updates
- Save/load functionality

> Note: GUI launcher (`Main.java`) is excluded from tests.

---

## 💾 Save/Load Instructions

- To save: Click `File → Save Game and Exit`
- To load: Select **"Load Saved Game"** from the main menu

All game state data is saved into `saved_game.ser`.

---

## 🎯 Learning Outcomes

This project demonstrates:
- Java OOP best practices
- Event-driven GUI design with Swing
- Game loop logic with timers
- AI-like movement behavior
- Real-time collision detection
- File I/O using serialization

---

## 📂 Project Structure

```
/game
├── Main.java
├── GameBoard.java
├── GameBoardPanel.java
├── Player.java
├── Enemy.java
├── Bomb.java
└── package-info.java
```

---

## 🙋 About the Developer

**👨‍💻 Muhammad Hameez Khan**   
🎓 Aspiring software developer with a passion for building interactive applications and games using Java and modern frameworks.

---

## 📫 Contact

- 📧 Email: hameezkhan993@gmail.com
- 💼 [LinkedIn](https://www.linkedin.com/in/muhammad-hameez-khan-b24bb623b/)
- 💻 [GitHub](https://github.com/Hameez1khan)

---

## 📃 License

This project is for educational/demo purposes and does not reuse assets from the original Bomberman game.  
Use it freely with credit.

---
