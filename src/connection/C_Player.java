package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import controller.Con_Game;
import model.IPlayer;

public class C_Player extends Thread {

	private BufferedReader reader;
	private BufferedWriter writer;
	private Socket socket;
	
	private IPlayer player;

	private int id_player;
	private String room;
	private boolean isConnected;
	private boolean isReady;

	public C_Player(Socket socket, int id_player, String room) {
		this.room = room;
		this.id_player = id_player;
		this.isConnected = true;
		this.isReady = false;
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		player = Con_Game.getPlayer(id_player);
		setUp();
		this.start();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				update(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void update(String update) {
		
	}

	public void setUp() {
		String message = room;
		send(message);
	}

	public void send(String message) {
		if (isConnected) {
			try {
				writer.write(message);
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				isConnected = false;
			}
		}
	}
	
	private void ready() {
		isReady = true;
	}
	
	//------------------------------------------------------

	public int getId_player() {
		return this.id_player;
	}
	
	public boolean isReady() {
		return this.isReady;
	}

	public boolean isConnected() {
		return this.isConnected;
	}

}
