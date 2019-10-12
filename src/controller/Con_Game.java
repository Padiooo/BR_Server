package controller;

import model.IPlayer;
import model.Player;

public class Con_Game {

	public static IPlayer getPlayer(int id_player) {
		int x = -1;
		int y = -1;
		int scale = 50;
		switch (id_player) {
		case 0:
			x = 1;
			y = 1;
			break;
		case 1:
			x = 9;
			y = 9;
			break;
		case 2:
			x = 9;
			y = 1;
			break;
		case 3:
			x = 1;
			y = 9;
			break;
		case 4:
			x = 5;
			y = 1;
			break;
		case 5:
			x = 5;
			y = 9;
			break;
		case 6:
			x = 9;
			y = 5;
			break;
		case 7:
			x = 1;
			y = 5;
			break;
		case 8:
			x = 3;
			y = 3;
			break;
		case 9:
			x = 7;
			y = 7;
			break;
		case 10:
			x = 7;
			y = 3;
			break;
		case 11:
			x = 3;
			y = 7;
			break;
		}
		return new Player(id_player, x * scale, y * scale);
	}
}
