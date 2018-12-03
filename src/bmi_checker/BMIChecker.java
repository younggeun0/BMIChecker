package bmi_checker;

public class BMIChecker {
	/*
	* BMI����= ������(kg) �� (����(m) �� ����(m))
	* ����� �� 18.5 ���ϸ� ��ü��, 18.5~23�� ����, 23~25�� ��ü��, 25~30�� ��, 30�̻��� ����
	*/
	
	public static double bmiCalc(double  height, double weight) {
		double fHeight = height/100;
		
		System.out.printf("\nbmi���� : %.2f\n",weight/(fHeight*fHeight));
		return weight / (fHeight * fHeight);
	}
	
	public static void printResult(double bmi) {
		
		if(bmi >= 30) {
			System.out.println("�����Դϴ�.");
		} else if(bmi>=25 && bmi<30) {
			System.out.println("���Դϴ�.");
		} else if(bmi>=23 && bmi<25) {
			System.out.println("��ü���Դϴ�.");
		} else if(bmi>=18.5 && bmi<23) {
			System.out.println("�����Դϴ�.");
		} else if(bmi<18.5) {
			System.out.println("��ü���Դϴ�.");
		}
	}
}
