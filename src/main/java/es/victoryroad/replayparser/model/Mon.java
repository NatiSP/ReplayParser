package es.victoryroad.replayparser.model;

public class Mon {

	private String name;
	private int used;
	private double usingPercentage;
	private double winRate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public double getUsingPercentage() {
		return usingPercentage;
	}
	public void setUsingPercentage(double usingPercentage) {
		this.usingPercentage = usingPercentage;
	}
	public double getWinRate() {
		return winRate;
	}
	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}
}
