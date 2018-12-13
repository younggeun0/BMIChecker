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
		lblInfo = new Label("BMI란?");
		Label lblHeight = new Label("키(cm) : ");
		Label lblWeight = new Label("몸무게(kg) : ");
		taInfo = new TextArea(
				"비만 계산 방법은 신체질량지수인 BMI(Body Mass \nIndex)에 "
				+ "근거한방식으로 BMI지수= 몸무게(kg) ÷ (\n신장(m) × 신장(m))입니다. "
				+ "산출된 값이 18.5 이하면 \n저체중, 18.5~23은 정상, 23~25는 과체중, "
				+ "25~30은\n 비만, 30이상은 고도비만으로 나누어집니다.", 10, 10);
		taInfo.setEditable(false);
		btnCal = new Button("계산");
		btnExit = new Button("종료");
		
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
