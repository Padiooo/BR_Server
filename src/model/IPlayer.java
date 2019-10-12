package model;

public interface IPlayer {
	
	public String move(int direction_x, int direction_y);
	
	public boolean shoot(int mouse_x, int mouse_y);
	
	public boolean reload(int id_ball);
	
	public boolean isAlive();
	
	public boolean die();
	
	public String toString();
	
}
