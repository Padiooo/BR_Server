package connection;

import java.net.Socket;
import java.util.ArrayList;

import controller.CountDown;

public class C_Game extends Thread {

	private ArrayList<C_Player> c_players = new ArrayList<C_Player>();

	private int id_players = 0;
	private String room;
	private boolean isAvailable;
	private CountDown countDown;

	public C_Game(String room) {
		countDown = new CountDown();
		this.room = room;
		isAvailable = true;
	}

	@Override
	public void run() {
		while (!isAllPlayersReady() || !countDown.isCountDownFinished()) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isAvailable = false;
	}

	public void addConnection(Socket socket) {
		c_players.add(new C_Player(socket, id_players, room));
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
		if(ready) {
			countDown.resetTimer();
		}
		
		return ready;
	}

	// -------------------------------------------------------------

	public boolean isAvailable() {
		return this.isAvailable;
	}
}
