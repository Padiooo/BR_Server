package model;

public interface IPlayer {
	
	public boolean move(int direction_x, int direction_y);
	
	public boolean rollBack();
	
	public boolean shoot(int id_ball, int mouse_x, int mouse_y);
	
	public boolean reload(int id_ball);
	
	public boolean isAlive();
	
	public boolean die(String death_recap);
	
	public String toString();
	
}
