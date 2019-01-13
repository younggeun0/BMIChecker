package swing_controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import swing_model.HistoryDAO;
import swing_model.HistoryInsertVO;
import swing_model.HistoryVO;
import swing_view.BMIHistory;
import swing_view.BMIView;

public class BMIViewEvt implements ActionListener {

	private BMIView bv;
	private List<HistoryVO> listRow;
	private String name;
	private HistoryDAO h_dao;
	
	public BMIViewEvt(BMIView bv, List<HistoryVO> listRow, String name) {
		this.bv = bv;
		this.listRow = new ArrayList<HistoryVO>();
		this.name = name;
		
		h_dao = HistoryDAO.getInstance();
		
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
			bv.dispose();
		}

		if (e.getSource() == bv.getJbHistory()) {
			// history��ư Ŭ���� BMIHistory ȣ��
			new BMIHistory(bv, listRow);
		}
		
		if (e.getSource() == bv.getJbSaveHistory()) {
			saveHistory();
		}
		
		if (e.getSource() == bv.getJbLoadHistory()) {
			try {
				List<HistoryVO> fileDate = loadHistoryFromFile();
				
				// �о� ���� ����Ʈ������ DB�� �߰�
				int cnt = h_dao.insertLoadData(fileDate);
				
				JOptionPane.showMessageDialog(bv, cnt+"���� �����͸� �߰��Ͽ����ϴ�.");
				listRow.clear();
				listRow = h_dao.selectAllHistory();
				
			} catch (ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(bv, "�о���ϼ� �ִ� ���� ������ �ƴմϴ�.");
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(bv, "������ �������� �ʽ��ϴ�.");
				e1.printStackTrace();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(bv, "���� �ҷ����⿡ �����߽��ϴ�.");
				e1.printStackTrace();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(bv, "�˼��մϴ�. �߰��� �����߽��ϴ�.");
				e1.printStackTrace();
			}
		}
	}

	// history.dat ������ �о���� �ڵ�
	public List<HistoryVO> loadHistoryFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {

		FileDialog fd = new FileDialog(bv, "History �ҷ�����", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory()+fd.getFile();
		System.out.println(path);
		
		File file = new File(path);

		List<HistoryVO> list = null;
		if (file.isFile()) {
			ObjectInputStream ois = null;
			HistoryVO tempVO = null;
			list = new ArrayList<HistoryVO>();
			
			try {
				ois = new ObjectInputStream(new FileInputStream(file));

				while (true) {
					try {
						tempVO = (HistoryVO) ois.readObject();
						list.add(tempVO);
					} catch (EOFException e) {
						break;
					}
				}
				
			} finally {
				if (ois != null)
					ois.close();
			}
		} else {
			JOptionPane.showMessageDialog(null, "������ �ƴմϴ�.");
		}

		System.out.println(list);
		return list;
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
		HistoryInsertVO hivo = 
			new HistoryInsertVO(name, height, weight, bmiResult, textResult);
		System.out.println(hivo);
		
		try {
			h_dao.insertOneData(hivo);
			JOptionPane.showMessageDialog(bv, "�� ������ �߰��Ǿ����ϴ�.");
			listRow.clear();
			listRow = h_dao.selectAllHistory();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(bv, "������ �߰��ϴµ� �����߽��ϴ�.");
			e.printStackTrace();
		}
	}
	
	// ����Ǹ� listRow�� �ʱ�ȭ�ǰ�(�ε�), saveHistory�� ȣ��Ǹ� ���� listRow������
	// ��� ������ �� JOptionPane ConfirmDialog ����, �߰��Ѵ� �ϸ� �޼ҵ� ȣ���ؼ� �߰��ϵ��� ����
	// saveHistory�� �������� ����, addHistory�� Confirm OK�Ҷ� ����
	public void saveHistory() {
		ObjectOutputStream oos = null;
		
		try {
			FileDialog fd = new FileDialog(bv, "History ���� ��μ���", FileDialog.SAVE);
			fd.setFilenameFilter(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return new File(dir, name).isDirectory();
				}
			});
			fd.setVisible(true);

			// directory ������ �о�ͼ� �� ���� ������
			// �׷��� ���ؼ� ���ϸ��� �Է��ؾ� �ϴ� �̽� ///////////////
			String path = fd.getDirectory();
			
			File file = new File(path);
			
			if (!file.isDirectory()) {
				JOptionPane.showMessageDialog(bv, "��θ� �߸��Է��ϼ̽��ϴ�.");
				return;
			}
			
			oos = new ObjectOutputStream(new FileOutputStream(
					new File(path+"/"+new Date().getTime()+"_history.dat")));

			// "��¥","Ű","������","BMI����","���" 
			// listRow�κ��� HistoryVO �����͸� �����ͼ� CSV String �����ͷ� ����
			HistoryVO tempVO = null;
			
			for(int i=0; i<listRow.size(); i++) {
				tempVO = listRow.get(i);
				oos.writeObject(tempVO);
				oos.flush();
			}
			
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
