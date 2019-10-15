package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import controller.PositionPlayer;
import info.Info;
import model.GameModel;
import model.IPlayer;

public class C_Player extends Thread {

	private BufferedReader reader;
	private BufferedWriter writer;
	private Socket socket;

	private String delims = "[ ]+";

	private IPlayer player;

	private int id_player;
	private String room;
	private boolean isConnected;
	private boolean isReady;

	public C_Player(Socket socket, int id_player, String room, GameModel game_model) {
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
		player = PositionPlayer.getPlayer(id_player);
		game_model.addPlayer(player);
		setUp();
	}

	@Override
	public void run() {
		while (player.isAlive() && isConnected()) {
			try {
				update(reader.readLine());
			} catch (IOException e) {
				System.out.println("Player " + id_player + " left");
				isConnected = false;
			}
		}
		send("DIE");
		send("DEATH_RECAP " + player.getDeathRecap());
	}

	private void update(String update) {
		String[] updates = update.split(delims);
		switch (updates[0]) {
		case "READY":
			ready();
			break;
		case "move":
			player.move(Integer.valueOf(updates[1]), Integer.valueOf(updates[2]));
			break;
		case "shoot":
			player.shoot(Integer.valueOf(updates[1]), Integer.valueOf(updates[2]), Integer.valueOf(updates[3]));
			break;
		case "reload":
			player.reload(Integer.valueOf(updates[1]));
			break;
		default:
			break;
		}
	}

	public void setUp() {
		//@formatter:off
		String message = "SETUP " + id_player + " " + Info.WINDOW_SIZE_X + " " + Info.WINDOW_SIZE_Y + " " + Info.CLOCK_TIME + " " + Info.PLAYER_SIZE + " " + Info.BALL_SIZE;
		//@formatter:on
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

	// ------------------------------------------------------

	public IPlayer getPlayer() {
		return player;
	}

	public int getId_player() {
		return this.id_player;
	}

	public boolean isReady() {
		return this.isReady;
	}

	public synchronized boolean isConnected() {
		return this.isConnected;
	}

}
