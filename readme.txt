# Tic-Tac-Toe Project

## 📜 Overview
Create a client-server system that allows players to engage in exciting Tic-Tac-Toe matches against one another! This project includes comprehensive documentation to guide users and developers alike.

## 📖 Detailed Description
### ⚙️ How It Works:
1. **Client Connection**: 
   - When a new client connects to the server, they are placed in a waiting state until another client is ready to start a game. 🤝
   
2. **Gameplay**:
   - Once matched, the two clients begin their game by sending their moves to the server. Each game can involve an arbitrary number of clients, but each client can only participate in one game at a time. 🎲
   
3. **Game Conclusion**:
   - After a game concludes, the clients are returned to the pool of available players, allowing them to be matched with others for new games. 🔄

### 🏆 Optional Feature
- Clients can view a leaderboard showcasing the current standings of players. Players are identified by a chosen nickname and ranked by:
  - **Games Won** 🥇
  - **Games Drawn** 🤝

## 🔍 General Rules
### 💻 Server:
- **Language**: C
- **Platform**: UNIX/Linux
- **Requirements**:
  - Utilize UNIX system calls and the standard C library only.
  - Implement a concurrent server to handle multiple clients simultaneously.
  - Log key operations (new connections, disconnections, client requests) to standard output. 📜

### 📱 Client:
- **Language**: Java
- **Platform**: Android
- **Requirements**:
  - Communication with the server via TCP or UDP sockets.
  - Use only standard APIs (`java.net.*`) for communication. 📡

## 🚀 Getting Started
1. **Clone the Repository**: 
   ```bash
   git clone https://github.com/yourusername/tic-tac-toe.git
   ```
2. **Set Up the Server**:
   - Compile the C server code and run it on a UNIX/Linux environment.

3. **Launch the Client**:
   - Open the Android project in your favorite IDE and run the application.

4. **Play & Enjoy!** 🎉

## 📄 Documentation
For detailed documentation on the code structure, features, and further instructions, refer to the `docs` folder in this repository.

## 🤝 Contributing
We welcome contributions! Please fork the repository and submit a pull request with your changes. 
