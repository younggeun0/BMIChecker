package swing_evt;

import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import swing_view.BMIView;

public class BMIEvt implements ActionListener {

	private BMIView bv;

	public BMIEvt(BMIView bv) {
		this.bv = bv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bv.getJbCal()) {
			
			// Ű�� �Է��� �ȵƴٸ� ���â�� ���� Űĭ���� �̵�
			if (bv.getJtfHeight().getText().isEmpty()) {
				
				JOptionPane.showMessageDialog(bv, "Ű(cm)�� �Է����ּ���.", "", JOptionPane.WARNING_MESSAGE);
				bv.getJtfHeight().requestFocus();
				return;
			} else {
				try {
					// �������� ����
					Integer.parseInt(bv.getJtfHeight().getText());
					
				} catch (NumberFormatException nfe) {
					// ���ڰ� �ƴϸ� ���
					JOptionPane.showMessageDialog(bv, "Ű(cm)�� ���ڸ� �Է°����մϴ�.", "", JOptionPane.WARNING_MESSAGE);
					// ���ڶ�� Ű TextField�� Focus
					bv.getJtfHeight().requestFocus();
					return;
				}
			}

			// �����԰� �Է��� �ȵƴٸ� ���â�� ���� ������ĭ���� �̵�
			if (bv.getJtfWeight().getText().isEmpty()) {
				JOptionPane.showMessageDialog(bv, "������(kg)�� �Է����ּ���.", "", JOptionPane.WARNING_MESSAGE);
				bv.getJtfWeight().requestFocus();
				return;
			}
			
			// �ΰ��� ��� ������� �ʴٸ�
			if (!(bv.getJtfHeight().getText().isEmpty() && bv.getJtfWeight().getText().isEmpty())) {
				try {
					// �Էµ� ���� ��� �������� ����
					Integer.parseInt(bv.getJtfHeight().getText());
					Integer.parseInt(bv.getJtfWeight().getText());
					
					// ���ڶ�� �޼ҵ� ȣ��
					bmiCal();
					
				} catch (NumberFormatException nfe) {
					// ���ڰ� �ƴϸ� ���ڰ� �ƴ� ĭ�� �������� �˷��ִ� ���
					JOptionPane.showMessageDialog(bv, "������(kg)�� ���ڸ� �Է°����մϴ�.", "", JOptionPane.WARNING_MESSAGE);
					bv.getJtfWeight().requestFocus();
					return;
				}
			}
		}
		if (e.getSource() == bv.getJbExit()) {
			bv.dispose();
		}
	}

	public void bmiCal() {
		double height = 0, weight = 0;

		// height�� ����ų� weight�� ������� �ʴٸ�.
		if (!(bv.getJtfHeight().getText().isEmpty() || bv.getJtfWeight().getText().isEmpty())) {

			height = Double.valueOf(bv.getJtfHeight().getText()) / 100;
			weight = Double.valueOf(bv.getJtfWeight().getText());

			double bmiResult = 0;
			String textResult = null;

			bmiResult = weight / (height * height);

			if (bmiResult >= 30) {
				textResult = "�����Դϴ�.";
			} else if (bmiResult >= 25 && bmiResult < 30) {
				textResult = "���Դϴ�.";
			} else if (bmiResult >= 23 && bmiResult < 25) {
				textResult = "��ü���Դϴ�.";
			} else if (bmiResult >= 18.5 && bmiResult < 23) {
				textResult = "�����Դϴ�.";
			} else if (bmiResult < 18.5) {
				textResult = "��ü���Դϴ�.";
			}

			JOptionPane.showMessageDialog(bv, textResult, "���", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
