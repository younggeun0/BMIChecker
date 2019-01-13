package swing_model;

import java.io.Serializable;

public class HistoryVO implements Serializable {
	
	private static final long serialVersionUID = 705316501072181819L;
	private String name;
	private String date;
	private double height;
	private double weight;
	private double bmiNum;
	private String bmiResult;
	
	public HistoryVO() {
	}
	
	public HistoryVO(String name, String date, 
			double height, double weight, double bmiNum, String bmiResult) {
		this.name = name;
		this.date = date;
		this.height = height;
		this.weight = weight;
		this.bmiNum = bmiNum;
		this.bmiResult = bmiResult;
	}

	public String getName() {
		return name;
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

	@Override
	public String toString() {
		return "HistoryVO [name=" + name + ", date=" + date + ", height=" + height + ", weight=" + weight + ", bmiNum="
				+ bmiNum + ", bmiResult=" + bmiResult + "]";
	}
}
