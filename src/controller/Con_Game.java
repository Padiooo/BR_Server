package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import info.Info;
import logwriter.LogWriter;
import model.BallShoot;
import model.GameModel;
import model.IPlayer;
import model.Player;

public class Con_Game extends Thread implements Observer {

	private GameModel game_model;
	private ArrayList<IPlayer> players;

	private StringBuilder log = new StringBuilder();

	public Con_Game(GameModel game_model) {
		this.game_model = game_model;
		players = game_model.getPlayers();
	}

	@Override
	public void run() {
		for (IPlayer player : players) {
			player.addObserverX(this);
		}
		while (players.size() > 1) {
			try {
				checkHitBoxBallBall();
				checkHitBoxPlayerBall();
				removeDeadPlayer();
			} catch (NullPointerException n) {

			}
		}
		players.get(0).die("You won");
		log.append(players.get(0).getId() + " won");
		LogWriter.writeLog(log.toString());
	}

	private void checkHitBoxBallBall() {
		ArrayList<BallShoot> balls = new ArrayList<BallShoot>();
		for (IPlayer player : players) {
			for (BallShoot ball : player.getBalls()) {
				if (ball != null) {
					balls.add(ball);
				}
			}
		}

		for (BallShoot ball : balls) {
			if (ball != null) {
				for (BallShoot _ball : balls) {
					if (_ball != null) {
						if (ball.getPlayerId() != _ball.getPlayerId()) {
							if (checkDistanceBallBall(ball, _ball)) {
								game_model.getPlayerById(ball.getPlayerId()).getBalls()[ball.getBallId()] = null;
								game_model.getPlayerById(_ball.getPlayerId()).getBalls()[_ball.getBallId()] = null;
							}
						}
					}
				}
			}
		}
	}

	private boolean checkDistanceBallBall(BallShoot ball, BallShoot _ball) {

		int distance = Info.BALL_SIZE;

		//@formatter:off
		int bb_distance = (int) Math.sqrt((ball.getX() - _ball.getX()) * (ball.getX() - _ball.getX()) + (ball.getY() - _ball.getY()) * (ball.getY() - _ball.getY()));
		//@formatter:on

		return distance > bb_distance ? true : false;
	}

	private void removeDeadPlayer() {
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

	private void checkHitBoxPlayerBall() {
		for (IPlayer player : players) {
			int px = player.getX();
			int py = player.getY();
			for (IPlayer _player : players) {
				if (player.isAlive()) {
					if (player.getId() != _player.getId()) {
						for (BallShoot ball : _player.getBalls()) {
							if (ball != null) {
								if (checkDistancePlayerBall(px, py, ball.getX(), ball.getY())) {
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

	private void playerDie(IPlayer player_killed, IPlayer player_killer) {
		player_killed.die("Killed by " + player_killer.getId());
		log.append(player_killer.getId() + " killed " + player_killed.getId());
		log.append(System.getProperty("line.separator"));
	}

	// if distance > pb_distanc => player touched (true)
	private boolean checkDistancePlayerBall(int px, int py, int bx, int by) {

		if (bx == -99 || by == -99) {
			return false;
		}

		int distance = Info.BALL_SIZE / 2 + Info.PLAYER_SIZE / 2;
		int pb_distance = (int) Math.sqrt((px - bx) * (px - bx) + (py - by) * (py - by));

		return (distance > pb_distance) ? true : false;
	}

	private boolean isPlayerOutBound(IPlayer player) {
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
