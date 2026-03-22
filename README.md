# Chat_application_java_project

# 💬 Java Real-Time Chat Application

## 📌 Problem Statement

Design and implement a **real-time chat application in Java** that allows multiple clients to connect to a central server and exchange messages instantly.

The system should:

* Allow multiple clients to connect simultaneously
* Enable real-time communication between users
* Use a client-server architecture
* Display messages in a simple interface

The application must use:

* **Socket Programming** for communication
* **Multithreading** for handling multiple clients
* **AWT (optional)** for graphical user interface

---

## 🎯 Objective

The objective of this project is to understand and implement:

* Client-Server Architecture
* Network communication using Java Sockets
* Multithreading for concurrent client handling
* Real-time message transmission

---

## ⚙️ Features

* 🔗 Multiple clients can connect to the server
* 💬 Real-time chat between users
* 👤 Username system
* 📢 Broadcast messages to all connected clients
* 🚪 User join and leave notifications
* ⚡ Fast and responsive communication

---

## 🏗️ Project Structure

```
Chat_application_java_project
│
├── ChatServer.java        # Server-side program
├── ClientHandler.java     # Handles each client using threads
├── ChatClient.java        # Client-side program
├── EventHandler.java      # (Optional - GUI events)
├── MessageManager.java    # (Optional - message handling)
└── README.md
```

---

## 🧠 How It Works

1. The server starts and listens for client connections
2. Clients connect to the server using IP and port
3. Each client sends a username
4. The server creates a separate thread for each client
5. Messages are broadcast to all connected clients
6. Join and leave messages are displayed

---

## 🔄 Communication Flow

```
Client → Server → All Clients
```

---

## ▶️ How to Run

### Step 1: Compile

```bash
javac ChatServer.java
javac ChatClient.java
```

### Step 2: Run Server

```bash
java ChatServer
```

### Step 3: Run Clients (open multiple terminals)

```bash
java ChatClient
```

---

## 🧪 Example Output

```
Vijay joined the chat
Rohan joined the chat

Vijay: Hello everyone
Rohan: Hi Vijay

Vijay left the chat
```

---

## 👥 Team Members & Roles

| Name      | Role                       |
| --------- | -------------------------- |
| Shrushti  | Server + Multithreading    |
| Vijay     | Client Networking          |
| Vaishnavi | Event Handling + Data Mgmt |
| Anjali    | AWT GUI                    |

---

## 🛠️ Technologies Used

* Java
* Socket Programming
* Multithreading
* AWT (optional)

---

## 📚 Learning Outcomes

* Understanding of network programming
* Handling multiple clients using threads
* Building real-time communication systems
* Team collaboration using GitHub

---

## 🚀 Future Enhancements

* GUI-based chat interface (AWT/Swing)
* Private messaging
* Online user list
* Message timestamps
* Chat history storage

---

## 📌 Conclusion

This project demonstrates how real-time communication systems work using Java. It provides practical experience in networking, concurrency, and collaborative development.
