package bmi_checker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;

public class AWTEvt extends WindowAdapter implements ActionListener {

	private AWTDesign design;
	
	public AWTEvt(AWTDesign design) {
		this.design = design;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == design.getBtnCal()) {
			bmiCal();
		}
		if (e.getSource() == design.getBtnExit()) {
			design.dispose();
		}
	}
	
	public void bmiCal() {
		double height = 0, weight = 0;

		if (!(design.getTfHeight().getText().isEmpty() 
				|| design.getTfWeight().getText().isEmpty())) {
		
		height = Double.valueOf(design.getTfHeight().getText())/100;
		weight = Double.valueOf(design.getTfWeight().getText());
		
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
			design.getLblInfo().setText("���");
			design.getTaInfo().setText(textResult);
		}
	}
}
