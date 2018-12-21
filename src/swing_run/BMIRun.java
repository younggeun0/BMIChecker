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
	
	// history.dat 파일을 읽어오는 코드 구현
	public List<HistoryVO> loadHistory() throws IOException, FileNotFoundException, NullPointerException {
		
		BufferedReader br = null;
		String temp = null;
		String[] rowData = null;
		HistoryVO hv = null;
		List<HistoryVO> listRow = null;
		
		int select = JOptionPane.showConfirmDialog(null, "불러들일 history.dat 파일이 있으십니까?");
		
		switch (select) {
		case JOptionPane.OK_OPTION :
			
			String path = JOptionPane.showInputDialog("불러들일 history.dat이 있는 디렉토리 경로를 입력해주세요\n예)C:/dev");
			
			File file = new File(path);
			String historyDatPath = file.getPath()+"\\history.dat";
			
			if(file.isDirectory()) {
				try {
					hv = new HistoryVO();
					listRow = new ArrayList<HistoryVO>();
					br = new BufferedReader(new FileReader(historyDatPath));
					
					while( (temp = br.readLine()) != null) {
						// 읽어들인걸 잘라낸다음에 String[]로 저장, list에 바로 저장,그 리스트를 전달
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
		}
		new BMIView(listRow);
	}
}
