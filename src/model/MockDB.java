package model;

import java.util.ArrayList;

public class MockDB implements DB{
	
	private ArrayList<String[]> dbRows = new ArrayList<String[]>(); 
	private boolean isConnected = false;
	
	public MockDB() {
		String[] row = {"Vic", "75"};
		dbRows.add(row);
		String[] row2 = {"Nau", "60"};
		dbRows.add(row2);
		//System.out.println(this.dbRows);
	}
	
	@Override
	public boolean connect() {
		boolean connected = false; 
		if(!isConnected) {
			isConnected = true;
			connected = true;
		}
		return connected;
	}

	
	@Override
	public int getNumberOfGames() {
		return this.dbRows.size(); 
	}
	
	@Override
	public String[] getGameHistory(int game) {
		return this.dbRows.get(game);
	}	
	
	@Override
	public boolean addNewGame(String[] game)
	{
		this.dbRows.add(game); 
		return true; 
	}

	@Override
	public boolean close() {
		boolean closed = false; 
		if(isConnected) {
			isConnected = false;
			closed = true;
		}
		return closed;
	}

	@Override
	public ArrayList<String[]> getAllGames() {
		// TODO Auto-generated method stub
		return this.dbRows;
	}
	

}
