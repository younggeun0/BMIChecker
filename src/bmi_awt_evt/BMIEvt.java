package bmi_awt_evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import bmi_awt_view.BMIResult;
import bmi_awt_view.BMIView;

public class BMIEvt extends WindowAdapter implements ActionListener {

	private BMIView bv;
	
	public BMIEvt(BMIView bv) {
		this.bv = bv;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bv.getBtnCal()) {
			bmiCal();
		}
		if (e.getSource() == bv.getBtnExit()) {
			bv.dispose();
		}
	}
	
	public void bmiCal() {		
		double height = 0, weight = 0;

		if (!(bv.getTfHeight().getText().isEmpty() 
				|| bv.getTfWeight().getText().isEmpty())) {
		
		height = Double.valueOf(bv.getTfHeight().getText())/100;
		weight = Double.valueOf(bv.getTfWeight().getText());
		
			double bmiResult = 0;
			String textResult = null;
			
			bmiResult = weight / (height * height);
			
			if(bmiResult >= 30) {
				textResult = "�����Դϴ�.";
			} else if(bmiResult>=25 && bmiResult<30) {
				textResult = "���Դϴ�.";
			} else if(bmiResult>=23 && bmiResult<25) {
				textResult = "��ü���Դϴ�.";
			} else if(bmiResult>=18.5 && bmiResult<23) {
				textResult = "�����Դϴ�.";
			} else if(bmiResult<18.5) {
				textResult = "��ü���Դϴ�.";
			}

			new BMIResult(bv, textResult);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		bv.dispose();
	}
}
