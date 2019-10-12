package model;

public class Player implements IPlayer {

	@Override
	public String move(int direction_x, int direction_y) {
		return null;
	}

	@Override
	public boolean shoot(int mouse_x, int mouse_y) {
		return false;
	}

	@Override
	public boolean reload(int id_ball) {
		return false;
	}

	@Override
	public boolean isAlive() {
		return false;
	}

	@Override
	public boolean die() {
		return false;
	}	
	
	@Override
	public String toString() {
		return null;
	}
	
}
