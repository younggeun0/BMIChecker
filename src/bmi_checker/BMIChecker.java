package bmi_checker;

public class BMIChecker {
	/*
	* BMI지수= 몸무게(kg) ÷ (신장(m) × 신장(m))
	* 산출된 값 18.5 이하면 저체중, 18.5~23은 정상, 23~25는 과체중, 25~30은 비만, 30이상은 고도비만
	*/
	
	public static double bmiCalc(double  height, double weight) {
		double fHeight = height/100;
		
		System.out.printf("\nbmi지수 : %.2f\n",weight/(fHeight*fHeight));
		return weight / (fHeight * fHeight);
	}
	
	public static void printResult(double bmi) {
		
		if(bmi >= 30) {
			System.out.println("고도비만입니다.");
		} else if(bmi>=25 && bmi<30) {
			System.out.println("비만입니다.");
		} else if(bmi>=23 && bmi<25) {
			System.out.println("과체중입니다.");
		} else if(bmi>=18.5 && bmi<23) {
			System.out.println("정상입니다.");
		} else if(bmi<18.5) {
			System.out.println("저체중입니다.");
		}
	}
}
