package swing_run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import swing_evt.HistoryVO;
import swing_view.BMIView;

public class BMIRun {
	
	// history.dat ������ �о���� �ڵ� ����
	public List<HistoryVO> loadHistory() throws IOException, FileNotFoundException, NullPointerException {
		
		BufferedReader br = null;
		String temp = null;
		String[] rowData = null;
		HistoryVO hv = null;
		List<HistoryVO> listRow = null;
		
		int select = JOptionPane.showConfirmDialog(null, "�ҷ����� history.dat ������ �����ʴϱ�?");
		
		switch (select) {
		case JOptionPane.OK_OPTION :
			
			String path = JOptionPane.showInputDialog("�ҷ����� history.dat�� �ִ� ���丮 ��θ� �Է����ּ���\n��)C:/dev");
			
			File file = new File(path);
			String historyDatPath = file.getPath()+"\\history.dat";
			
			if(file.isDirectory()) {
				try {
					hv = new HistoryVO();
					listRow = new ArrayList<HistoryVO>();
					br = new BufferedReader(new FileReader(historyDatPath));
					
					while( (temp = br.readLine()) != null) {
						// �о���ΰ� �߶󳽴����� String[]�� ����, list�� �ٷ� ����,�� ����Ʈ�� ����
						rowData = temp.split(",");
						
						hv.setDate(rowData[0]);
						hv.setDate(rowData[0]);
						hv.setHeight(Double.valueOf(rowData[1]));
						hv.setWeight(Double.valueOf(rowData[2]));
						hv.setBmiNum(Double.valueOf(rowData[3]));
						hv.setBmiResult(rowData[4]);
						listRow.add(hv);
					}
				} finally {
					if (br != null) br.close();
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
		}
		new BMIView(listRow);
	}
}
