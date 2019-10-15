package connection;

import java.net.Socket;
import java.util.ArrayList;

import controller.Con_Game;
import controller.CountDown;
import info.Info;
import model.GameModel;

public class C_Game extends Thread {

	private ArrayList<C_Player> c_players = new ArrayList<C_Player>();

	private GameModel game_model;
	private Con_Game con_game;

	private int id_players = 0;
	private String room;
	private boolean isAvailable;
	private CountDown countDown;

	public C_Game(String room) {
		this.room = room;
		countDown = new CountDown();
		countDown.start();
		game_model = new GameModel();
		con_game = new Con_Game(game_model);
		isAvailable = true;
	}

	@Override
	public void run() {
		System.out.println("-----1-----");
		while (!isAllPlayersReady() || !countDown.isCountDownFinished()) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("-----2-----");
		isAvailable = false;
		con_game.start();
		for (C_Player c_player : c_players) {
			c_player.send("START");
		}
		while (con_game.isAlive()) {
			String update = game_model.getUpdate();
			removeLostConnection();
			try {
				Thread.sleep(Info.CLOCK_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (C_Player c_player : c_players) {
				c_player.send(update);
			}
		}
		System.out.println("-----3-----");
	}

	private void removeLostConnection() {
		ArrayList<C_Player> c_lost_players = new ArrayList<C_Player>();

		for (C_Player c_player : c_players) {
			if (!c_player.isConnected()) {
				c_lost_players.add(c_player);
			}
		}
		for (C_Player c_lost_player : c_lost_players) {
			c_players.remove(c_lost_player);
		}
	}

	public void addConnection(Socket socket) {
		C_Player c_player = new C_Player(socket, id_players, room, game_model);
		c_player.start();
		c_players.add(c_player);
		id_players++;
		if (id_players > 12) {
			isAvailable = false;
		}
		if (countDown.isAlive()) {
			countDown.holdTimer();
		}
	}

	private boolean isAllPlayersReady() {
		boolean ready = true;

		for (C_Player c_player : c_players) {
			if (!c_player.isReady()) {
				ready = false;
				break;
			}
		}
		
		if (c_players.size() >= 2 && ready && countDown.getHoldTimer()) {
			countDown.resetTimer();
		}

		return ready;
	}

	// -------------------------------------------------------------

	public boolean isAvailable() {
		return this.isAvailable;
	}
}
