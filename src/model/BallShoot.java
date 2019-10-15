package model;

import info.Info;

public class BallShoot extends Thread {
	
	private int id_player;
	private int id_ball;
	
	private int x;
	private int y;
	
	private int speed_ball = Info.BALL_SPEED;
	
	private double angle;
	

	public BallShoot(int id_player, int id_ball, int x, int y, double angle) {
		this.id_player = id_player;
		this.id_ball = id_ball;
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			x += speed_ball * Math.cos(angle);
			y += speed_ball * Math.sin(angle);
		}
	}
	

	@Override
	public String toString() {
		String s = x + " " + y;
		return s;
	}
	
	// ------------------------------------------------------

	public int getBallId() {
		return id_ball;
	}
	
	public int getPlayerId() {
		return id_player;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
