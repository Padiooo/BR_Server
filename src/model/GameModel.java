package model;

import java.util.ArrayList;

public class GameModel {

	private ArrayList<IPlayer> players = new ArrayList<IPlayer>();

	public String getUpdate() {
		StringBuilder sb = new StringBuilder();

		for (IPlayer player : players) {
			sb.append(player.toString());
		}
		return sb.toString();
	}

	public ArrayList<IPlayer> getPlayers() {
		return players;
	}
	
	public IPlayer getPlayerById(int index) {
		for (IPlayer player : players) {
			if(player.getId() == index) {
				return player;
			}
		}
		return null;
	}

	public void removePlayer(IPlayer player) {
		players.remove(player);
	}

	public void addPlayer(IPlayer player) {
		players.add(player);
	}

}
