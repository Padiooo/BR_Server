package controller;

import java.net.Socket;

import connection.C_Game;

public class Con_Connection {

	private C_Game c_game;
	private String room;
	private int room_number = 0;
	
	public Con_Connection() {
		newCon_Connection();
	}	

	public void addConnection(Socket socket) {
		if(!c_game.isAvailable()) {
			newCon_Connection();
		}
		c_game.addConnection(socket);
	}
	
	private void newCon_Connection() {
		room = "Room " + room_number;
		c_game = new C_Game(room);
		c_game.start();
		room_number++;
	}
	
}
