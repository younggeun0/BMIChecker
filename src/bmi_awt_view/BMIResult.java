package bmi_awt_view;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Label;

import bmi_awt_evt.BMIResultEvt;

public class BMIResult extends Dialog {
	
	Label lblResult;

	public BMIResult(BMIView bv, String textResult) {
		super(bv, "결과", true);
		
		Button btnClose = new Button("종료");
		lblResult = new Label();
		showResult(textResult);

		setLayout(null);
		
		lblResult.setBounds(30, 40, 100, 25);
		add(lblResult);
		
		btnClose.setBounds(165, 50, 40, 25);
		add(btnClose);
		
		
		BMIResultEvt bre = new BMIResultEvt(this);
		addWindowListener(bre);
		btnClose.addActionListener(bre);
		
		setBounds(bv.getX()+80, bv.getY()+80, 230, 100);
		setVisible(true);
	}
	
	public void showResult(String textResult) {
		lblResult.setText(textResult);
	}
}
