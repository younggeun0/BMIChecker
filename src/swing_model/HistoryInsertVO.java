package swing_model;

import java.io.Serializable;

public class HistoryInsertVO implements Serializable {
	
	private String name;
	private double height;
	private double weight;
	private double bmiNum;
	private String bmiResult;
	
	public HistoryInsertVO() {
	}
	
	public HistoryInsertVO(String name, 
			double height, double weight, 
			double bmiNum, String bmiResult) {
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.bmiNum = bmiNum;
		this.bmiResult = bmiResult;
	}

	public String getName() {
		return name;
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
		return "HistoryInsertVO [name=" + name + ", height=" + height + ", weight=" + weight + ", bmiNum=" + bmiNum
				+ ", bmiResult=" + bmiResult + "]";
	}
}
