package bmi_awt_view;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import bmi_awt_evt.BMIEvt;

@SuppressWarnings("serial")
public class BMIView extends Frame {

	private TextField tfHeight;
	private TextField tfWeight;
	private Button btnCal, btnExit;
	private TextArea taInfo;
	private Label lblInfo;

	public BMIView() {

		super("BMI Checker");
		lblInfo = new Label("BMI��?");
		Label lblHeight = new Label("Ű(cm) : ");
		Label lblWeight = new Label("������(kg) : ");
		taInfo = new TextArea(
				"�� ��� ����� ��ü���������� BMI(Body Mass \nIndex)�� "
				+ "�ٰ��ѹ������ BMI����= ������(kg) �� (\n����(m) �� ����(m))�Դϴ�. "
				+ "����� ���� 18.5 ���ϸ� \n��ü��, 18.5~23�� ����, 23~25�� ��ü��, "
				+ "25~30��\n ��, 30�̻��� �������� ���������ϴ�.", 10, 10);
		taInfo.setEditable(false);
		btnCal = new Button("���");
		btnExit = new Button("����");
		
		tfHeight = new TextField();
		tfWeight = new TextField();
		
		lblInfo.setBounds(15,40, 40, 20);
		add(lblInfo);
		
		taInfo.setBounds(15,60,320, 90);
		add(taInfo);
		
		lblHeight.setBounds(36, 180, 50, 20);
		add(lblHeight);
		
		lblWeight.setBounds(15, 205, 70, 20);
		add(lblWeight);
		
		tfHeight.setBounds(100, 180, 80, 20);
		add(tfHeight);
		
		tfWeight.setBounds(100, 205, 80, 20);
		add(tfWeight);
		
		btnCal.setBounds(185, 180, 150, 40);
		add(btnCal);
		
		btnExit.setBounds(255, 230, 80, 40);
		add(btnExit);
		
		BMIEvt eventHandler = new BMIEvt(this);
		btnCal.addActionListener(eventHandler);
		btnExit.addActionListener(eventHandler);
		addWindowListener(eventHandler);
		
		setLayout(null);
		setResizable(false);
		setBounds(400, 250, 350, 290);
		setVisible(true);
	}
	
	public TextField getTfHeight() {
		return tfHeight;
	}
	public TextField getTfWeight() {
		return tfWeight;
	}
	public Button getBtnCal() {
		return btnCal;
	}
	public Button getBtnExit() {
		return btnExit;
	}
	public TextArea getTaInfo() {
		return taInfo;
	}
	public Label getLblInfo() {
		return lblInfo;
	}
}
