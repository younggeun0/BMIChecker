package bmi_checker;

import java.util.Scanner;

public class Run {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int flag = -1;
		double height;
		double weight;
		System.out.println(" 비만 계산 방법은 신체질량지수인 BMI(Body Mass Index)에 "
				+ "근거한\n방식으로 BMI지수= 몸무게(kg) ÷ (신장(m) × 신장(m))입니다.\n"
				+ "(산출된 값이 18.5 이하면 저체중, 18.5~23은 정상, 23~25는 과체중,\n"
				+ "25~30은 비만, 30이상은 고도비만으로 나누어집니다.)");

		while (flag != 0) {
			try {
				System.out.println("-------------------------------------------------");
				System.out.print("BMI 계산하기(1) 종료(0) > ");
				
				flag = Integer.parseInt(sc.next());

				switch (flag) {
				case 1:
					System.out.print("\n키를 입력해주세요 : ");
					height = sc.nextDouble();
					System.out.print("몸무게를 입력해주세요 : ");
					weight = sc.nextDouble();
					BMIChecker.printResult(BMIChecker.bmiCalc(height, weight));
					continue;
				case 0:
					sc.close();
					System.exit(0);
				default:
					System.out.println("\n** BMI check(1) 또는 종료(0)만 입력가능합니다.**\n");
				}
			} catch (Exception e) {
				System.out.println("\n**숫자만 입력가능합니다. 다시 시도해주세요.**\n");
			}
		}
	}
}
