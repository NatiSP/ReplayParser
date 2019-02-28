package es.victoryroad.replayparser.model;

import java.util.ArrayList;
import java.util.List;

public class Team {

	private List<Mon> mons;
	private double winRate;
	private int used;
	private int wins;
	private String trainer;
	
	public Team(){
		mons = new ArrayList<Mon>(6);
	}
	
	public List<Mon> getMons() {
		return mons;
	}
	public void setMons(List<Mon> mons) {
		this.mons = mons;
	}
	public double getWinRate() {
		return winRate;
	}
	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public String getTrainer() {
		return trainer;
	}
	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
}
