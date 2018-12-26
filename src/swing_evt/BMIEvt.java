package swing_evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import swing_view.BMIHistory;
import swing_view.BMIView;

public class BMIEvt implements ActionListener {

	private BMIView bv;
	private List<HistoryVO> listRow;
	private boolean exitFlag;
	
	public BMIEvt(BMIView bv, List<HistoryVO> listRow) {
		this.bv = bv;
		this.listRow = new ArrayList<HistoryVO>();
		exitFlag = false;
		
		if (listRow != null)
			this.listRow = listRow;
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
			saveHistory();
			
			if(exitFlag) {
				bv.dispose();
			}
		}

		if (e.getSource() == bv.getJbHistory()) {
			// history��ư Ŭ���� BMIHistory ȣ��
			new BMIHistory(bv, listRow);
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
			int select = JOptionPane.showConfirmDialog(bv, "����� �����Ͻðڽ��ϱ�?");
			switch(select) {
			case JOptionPane.OK_OPTION : 
				addHistory(height, weight, bmiResult, textResult);
				break;
			}
		}
	}
	
	// list�� HistoryVO ������ ��� BMIHistoryȣ�� �� ����
	public void addHistory(double height, double weight, double bmiResult, String textResult) {
		// ����� ���� HistoryVO ��ü
		HistoryVO hv = new HistoryVO();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		hv.setDate(sdf.format(d));
		hv.setHeight(height);
		hv.setWeight(weight);
		hv.setBmiNum(bmiResult);
		hv.setBmiResult(textResult);
		listRow.add(hv);
	}
	
	// ����Ǹ� listRow�� �ʱ�ȭ�ǰ�(�ε�), saveHistory�� ȣ��Ǹ� ���� listRow������
	// ��� ������ �� JOptionPane ConfirmDialog ����, �߰��Ѵ� �ϸ� �޼ҵ� ȣ���ؼ� �߰��ϵ��� ����
	// saveHistory�� �������� ����, addHistory�� Confirm OK�Ҷ� ����
	public void saveHistory() {
		
		ObjectOutputStream oos = null;
		
		try {
			
			String path = JOptionPane.showInputDialog("������ ��θ� �Է����ּ���.��)C:/dev/home");
			
			File file = new File(path);
			
			if (!file.isDirectory()) {
				JOptionPane.showMessageDialog(bv, "��θ� �߸��Է��ϼ̽��ϴ�.");
				return;
			}
			
			oos = new ObjectOutputStream(new FileOutputStream(
					new File(path+"/history.dat")));

			// "��¥","Ű","������","BMI����","���" 
			// listRow�κ��� HistoryVO �����͸� �����ͼ� CSV String �����ͷ� ����
			HistoryVO tempVO = null;
			
			for(int i=0; i<listRow.size(); i++) {
				tempVO = listRow.get(i);
				oos.writeObject(tempVO);
				oos.flush();
			}
			
			exitFlag = true;
		} catch (NullPointerException npe) { 
			JOptionPane.showMessageDialog(bv, "�������� �ʰ� �����մϴ�.");
			bv.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(oos!=null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
