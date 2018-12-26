package swing_evt;

import java.io.Serializable;

public class HistoryVO implements Serializable {
	
	private String date;
	private double height;
	private double weight;
	private double bmiNum;
	private String bmiResult;
	
	public void setDate(String date) {
		this.date = date;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void setBmiNum(double bmiNum) {
		this.bmiNum = bmiNum;
	}
	public void setBmiResult(String bmiResult) {
		this.bmiResult = bmiResult;
	}
	public String getDate() {
		return date;
	}
	public double getHeight() {
		return height;
	}
	public double getWeight() {
		return weight;
	}
	public double getBmiNum() {
		return bmiNum;
	}
	public String getBmiResult() {
		return bmiResult;
	}
}
