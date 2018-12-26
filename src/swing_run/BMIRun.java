package swing_run;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import swing_evt.HistoryVO;
import swing_view.BMIView;

public class BMIRun {
	
	// history.dat ������ �о���� �ڵ� ����
	public List<HistoryVO> loadHistory() throws ClassNotFoundException, IOException, FileNotFoundException, NullPointerException {
		
		ObjectInputStream ois = null;
		HistoryVO temp = null;
		List<HistoryVO> listRow = null;
		
		int select = JOptionPane.showConfirmDialog(null, "�ҷ����� history.dat ������ �����ʴϱ�?");
		
		switch (select) {
		case JOptionPane.OK_OPTION :
			
			String path = JOptionPane.showInputDialog("�ҷ����� history.dat�� �ִ� ���丮 ��θ� �Է����ּ���\n��)C:/dev");
			
			File file = new File(path);
			String historyDatPath = file.getPath()+"\\history.dat";
			
			if(file.isDirectory()) {
				try {
					temp = new HistoryVO();
					listRow = new ArrayList<HistoryVO>();
					ois = new ObjectInputStream(new FileInputStream(new File(historyDatPath)));
					
					try {
						while(true) {
							temp = (HistoryVO)ois.readObject();
							System.out.println(temp);
							listRow.add(temp);
						}
					} catch (EOFException e) {
						return listRow;
					}
					
				} finally {
					if (ois != null) ois.close();
				}
			} else {
				JOptionPane.showMessageDialog(null, "������ �ƴմϴ�.");
			}
		case JOptionPane.NO_OPTION :
		case JOptionPane.CANCEL_OPTION:
		}
		
		return listRow;
	}
	
	public static void main(String[] args) {
		
		BMIRun br = new BMIRun();
		List<HistoryVO> listRow = null;
		try {
			listRow = br.loadHistory();
		} catch (NullPointerException npe) {
			JOptionPane.showMessageDialog(null, "�ҷ����� history.dat�� �����ϴ�.");
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "����� history.dat�� �����ϴ�.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		new BMIView(listRow);
	}
}
