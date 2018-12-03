package bmi_checker;

import java.util.Scanner;

public class Run {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int flag = -1;
		double height;
		double weight;
		System.out.println(" �� ��� ����� ��ü���������� BMI(Body Mass Index)�� "
				+ "�ٰ���\n������� BMI����= ������(kg) �� (����(m) �� ����(m))�Դϴ�.\n"
				+ "(����� ���� 18.5 ���ϸ� ��ü��, 18.5~23�� ����, 23~25�� ��ü��,\n"
				+ "25~30�� ��, 30�̻��� �������� ���������ϴ�.)");

		while (flag != 0) {
			try {
				System.out.println("-------------------------------------------------");
				System.out.print("BMI ����ϱ�(1) ����(0) > ");
				
				flag = Integer.parseInt(sc.next());

				switch (flag) {
				case 1:
					System.out.print("\nŰ�� �Է����ּ��� : ");
					height = sc.nextDouble();
					System.out.print("�����Ը� �Է����ּ��� : ");
					weight = sc.nextDouble();
					BMIChecker.printResult(BMIChecker.bmiCalc(height, weight));
					continue;
				case 0:
					sc.close();
					System.exit(0);
				default:
					System.out.println("\n** BMI check(1) �Ǵ� ����(0)�� �Է°����մϴ�.**\n");
				}
			} catch (Exception e) {
				System.out.println("\n**���ڸ� �Է°����մϴ�. �ٽ� �õ����ּ���.**\n");
			}
		}
	}
}
