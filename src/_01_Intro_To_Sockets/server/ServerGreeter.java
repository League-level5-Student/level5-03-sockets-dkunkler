package _01_Intro_To_Sockets.server;

import java.net.*;
import java.io.*;



public class ServerGreeter extends Thread {
	//1. Create an object of the ServerSocket class
	ServerSocket serverSocket;
	public ServerGreeter() throws IOException {
		int serverPort = 8080;
		serverSocket = new ServerSocket(serverPort);
		serverSocket.setSoTimeout(10000);
		System.out.println(serverSocket.getInetAddress());
	}

	public void run() {
		System.out.println("Running server");
		boolean b = true;
		while(b){	
			try {
				System.out.println("Server waiting for client to connect");
				Socket connection = serverSocket.accept();
				System.out.println("Client has connected");
				DataInputStream diStream = new DataInputStream(connection.getInputStream());
				System.out.println(diStream.readUTF());
				DataOutputStream doStream = new DataOutputStream(connection.getOutputStream());
				doStream.writeUTF("Sending message to client");
				serverSocket.close();
			} catch(SocketTimeoutException e) {
				System.out.println("Timout watching for connection");
				b = false;
			}
			catch(IOException e) {
				System.out.println("IOException error");
				b = false;
			}
		}
		System.out.println("Server exiting");
	}

	public static void main(String[] args) {
		System.out.println("Starting Server");
		Thread serverThread = new Thread(() -> {
			try {
				@SuppressWarnings("unused")
				ServerGreeter sGreeter = new ServerGreeter();
				sGreeter.run();
			} catch(IOException e) {
				e.printStackTrace();
			}
		});
		System.out.println("Server thread start");
		serverThread.start();
	}
}
