package bmi_awt_evt;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import bmi_awt_view.BMIResult;

public class BMIResultEvt extends WindowAdapter implements ActionListener {

	private BMIResult br;
	
	public BMIResultEvt(BMIResult br) {
		this.br = br;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		br.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		br.dispose();
	}
}
