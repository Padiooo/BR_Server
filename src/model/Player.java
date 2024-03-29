package model;

import java.util.Observable;
import java.util.Observer;

import info.Info;

public class Player extends Observable implements IPlayer {

	private int x;
	private int y;
	private int direction_x;
	private int direction_y;
	private int id_player;

	private int speed_player = Info.PLAYER_SPEED;
	private int size_player = Info.PLAYER_SIZE;

	private String death_recap;

	private boolean alive = true;
	private BallShoot[] balls = new BallShoot[3];

	public Player(int id_player, int x, int y) {
		this.id_player = id_player;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean move(int direction_x, int direction_y) {
		this.direction_x = direction_x;
		this.direction_y = direction_y;
		x += direction_x * speed_player;
		y += direction_y * speed_player;
		setChanged();
		notifyObservers(this);
		return false;
	}

	@Override
	public boolean rollBack() {
		x += direction_x * speed_player;
		y += direction_y * speed_player;
		return false;
	}

	@Override
	public boolean shoot(int id_ball, int mouse_x, int mouse_y) {
		double angle = Math.atan2(mouse_y - (y - size_player / 2), mouse_x - (x - size_player / 2));
		BallShoot ball = new BallShoot(id_player, id_ball, x, y, angle);
		balls[id_ball] = ball;
		ball.start();
		return false;
	}

	@Override
	public boolean reload(int id_ball) {
		balls[id_ball] = null;
		return false;
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public boolean die(String death_recap) {
		this.death_recap = death_recap;
		alive = false;
		return alive;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		s.append(id_player + " " + x + " " + y + " ");
		for (int i = 0; i < 3; i++) {
			if (balls[i] == null) {
				s.append(-99 + " " + -99 + " ");
			} else {
				s.append(balls[i].toString() + " ");
			}
		}

		return s.toString();
	}
	
	@Override
	public void setX(int x) {
		this.x = x;
	}
	
	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public int getId() {
		return id_player;
	}
	
	@Override
	public BallShoot[] getBalls() {
		return balls;
	}
	
	@Override
	public String getDeathRecap() {
		return death_recap;
	}
	
	@Override
	public void addObserverX(Observer o) {
		addObserver(o);
	}

	// ------------------------------------------------------

	public String getDeath_recap() {
		return death_recap;
	}


}
