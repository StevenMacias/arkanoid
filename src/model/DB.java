package model;

import java.util.ArrayList;

public interface DB {
	public boolean connect();
	public int getNumberOfGames();
	public String[] getGameHistory(int game);
	public ArrayList<String[]> getAllGames(); 
	public boolean addNewGame(String[] game);	
	public boolean close();

}
