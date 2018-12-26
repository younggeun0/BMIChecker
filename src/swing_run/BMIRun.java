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
	
	// history.dat 파일을 읽어오는 코드 구현
	public List<HistoryVO> loadHistory() throws ClassNotFoundException, IOException, FileNotFoundException, NullPointerException {
		
		ObjectInputStream ois = null;
		HistoryVO temp = null;
		List<HistoryVO> listRow = null;
		
		int select = JOptionPane.showConfirmDialog(null, "불러들일 history.dat 파일이 있으십니까?");
		
		switch (select) {
		case JOptionPane.OK_OPTION :
			
			String path = JOptionPane.showInputDialog("불러들일 history.dat이 있는 디렉토리 경로를 입력해주세요\n예)C:/dev");
			
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
				JOptionPane.showMessageDialog(null, "파일이 아닙니다.");
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
			JOptionPane.showMessageDialog(null, "불러들일 history.dat가 없습니다.");
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "저장된 history.dat가 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		new BMIView(listRow);
	}
}
