package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import info.Info;
import model.BallShoot;
import model.GameModel;
import model.IPlayer;

public class Con_Game extends Thread implements Observer {

	private GameModel game_model;
	private ArrayList<IPlayer> players;

	public Con_Game(GameModel game_model) {
		this.game_model = game_model;
		players = game_model.getPlayers();
	}

	@Override
	public void run() {
		while (players.size() > 1) {
			checkHitBox();
			removeDeadPlayer();
		}

		players.get(0).die("You won");
	}

	public void removeDeadPlayer() {
		ArrayList<IPlayer> dead_players = new ArrayList<IPlayer>();
		for (IPlayer player : players) {
			if (!player.isAlive()) {
				dead_players.add(player);
			}
		}
		for (IPlayer player : dead_players) {
			game_model.removePlayer(player);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof IPlayer) {
			isPlayerOutBound((IPlayer) o);
		}
	}

	public void checkHitBox() {
		for (IPlayer player : players) {
			int px = player.getX();
			int py = player.getY();
			for (IPlayer _player : players) {
				if (player.isAlive()) {
					if (player.getId() != _player.getId()) {
						for (BallShoot ball : _player.getBalls()) {
							if (ball != null) {
								if (checkDistance(px, py, ball.getX(), ball.getY())) {
									playerDie(player, _player);
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	public void playerDie(IPlayer player_killed, IPlayer player_killer) {
		player_killed.die("Killed by " + player_killer.getId());
	}

	// if distance > pb_distanc => player touched (true)
	public boolean checkDistance(int px, int py, int bx, int by) {

		if (bx == -99 || by == -99) {
			return false;
		}

		int distance = Info.BALL_SIZE / 2 + Info.PLAYER_SIZE / 2;
		int pb_distance = (int) Math.sqrt((px - bx) * (px - bx) + (py - by) * (py - by));

		return (distance > pb_distance) ? true : false;
	}

	public boolean isPlayerOutBound(IPlayer player) {
		int x = player.getX();
		int y = player.getY();

		boolean outBound = false;

		if (x < 0) {
			player.setX(0);
			outBound = true;
		} else if (x > Info.WINDOW_SIZE_X) {
			player.setX(Info.WINDOW_SIZE_X);
			outBound = true;
		}

		if (y < 0) {
			player.setY(0);
			outBound = true;
		} else if (y > Info.WINDOW_SIZE_Y) {
			player.setY(Info.WINDOW_SIZE_Y);
			outBound = true;
		}
		return outBound;
	}

}
