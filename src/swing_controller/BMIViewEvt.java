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
			
			// 키가 입력이 안됐다면 경고창을 띄우고 키칸으로 이동
			if (bv.getJtfHeight().getText().isEmpty()) {
				
				JOptionPane.showMessageDialog(bv, "키(cm)를 입력해주세요.", "", JOptionPane.WARNING_MESSAGE);
				bv.getJtfHeight().requestFocus();
				return;
			} else {
				try {
					// 숫자인지 검증
					Integer.parseInt(bv.getJtfHeight().getText());
					
				} catch (NumberFormatException nfe) {
					// 숫자가 아니면 경고
					JOptionPane.showMessageDialog(bv, "키(cm)는 숫자만 입력가능합니다.", "", JOptionPane.WARNING_MESSAGE);
					// 숫자라면 키 TextField로 Focus
					bv.getJtfHeight().requestFocus();
					return;
				}
			}

			// 몸무게가 입력이 안됐다면 경고창을 띄우고 몸무게칸으로 이동
			if (bv.getJtfWeight().getText().isEmpty()) {
				JOptionPane.showMessageDialog(bv, "몸무게(kg)를 입력해주세요.", "", JOptionPane.WARNING_MESSAGE);
				bv.getJtfWeight().requestFocus();
				return;
			}
			
			// 두개가 모두 비어있지 않다면
			if (!(bv.getJtfHeight().getText().isEmpty() && bv.getJtfWeight().getText().isEmpty())) {
				try {
					// 입력된 값이 모두 숫자인지 검증
					Integer.parseInt(bv.getJtfHeight().getText());
					Integer.parseInt(bv.getJtfWeight().getText());
					
					// 숫자라면 메소드 호출
					bmiCal();
					
				} catch (NumberFormatException nfe) {
					// 숫자가 아니면 숫자가 아닌 칸이 무엇인지 알려주는 경고
					JOptionPane.showMessageDialog(bv, "몸무게(kg)는 숫자만 입력가능합니다.", "", JOptionPane.WARNING_MESSAGE);
					bv.getJtfWeight().requestFocus();
					return;
				}
			}
		}
		if (e.getSource() == bv.getJbExit()) {
			bv.dispose();
		}

		if (e.getSource() == bv.getJbHistory()) {
			// history버튼 클릭시 BMIHistory 호출
			new BMIHistory(bv, listRow);
		}
		
		if (e.getSource() == bv.getJbSaveHistory()) {
			saveHistory();
		}
		
		if (e.getSource() == bv.getJbLoadHistory()) {
			try {
				List<HistoryVO> fileDate = loadHistoryFromFile();
				
				// 읽어 들인 리스트정보를 DB에 추가
				int cnt = h_dao.insertLoadData(fileDate);
				
				JOptionPane.showMessageDialog(bv, cnt+"행의 데이터를 추가하였습니다.");
				listRow.clear();
				listRow = h_dao.selectAllHistory();
				
			} catch (ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(bv, "읽어들일수 있는 파일 형식이 아닙니다.");
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(bv, "파일이 존재하지 않습니다.");
				e1.printStackTrace();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(bv, "파일 불러오기에 실패했습니다.");
				e1.printStackTrace();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(bv, "죄송합니다. 추가에 실패했습니다.");
				e1.printStackTrace();
			}
		}
	}

	// history.dat 파일을 읽어오는 코드
	public List<HistoryVO> loadHistoryFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {

		FileDialog fd = new FileDialog(bv, "History 불러오기", FileDialog.LOAD);
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
			JOptionPane.showMessageDialog(null, "파일이 아닙니다.");
		}

		System.out.println(list);
		return list;
	}

	public void bmiCal() {
		double height = 0, weight = 0;

		// height가 비었거나 weight가 비어있지 않다면.
		if (!(bv.getJtfHeight().getText().isEmpty() || bv.getJtfWeight().getText().isEmpty())) {

			height = Double.valueOf(bv.getJtfHeight().getText()) / 100;
			weight = Double.valueOf(bv.getJtfWeight().getText());

			double bmiResult = 0;
			String textResult = null;
			
			bmiResult = weight / (height * height);

			if (bmiResult >= 30) {
				textResult = "고도비만입니다.";
			} else if (bmiResult >= 25 && bmiResult < 30) {
				textResult = "비만입니다.";
			} else if (bmiResult >= 23 && bmiResult < 25) {
				textResult = "과체중입니다.";
			} else if (bmiResult >= 18.5 && bmiResult < 23) {
				textResult = "정상입니다.";
			} else if (bmiResult < 18.5) {
				textResult = "저체중입니다.";
			}

			JOptionPane.showMessageDialog(bv, textResult, "결과", JOptionPane.INFORMATION_MESSAGE);
			int select = JOptionPane.showConfirmDialog(bv, "결과를 저장하시겠습니까?");
			switch(select) {
			case JOptionPane.OK_OPTION : 
				addHistory(height, weight, bmiResult, textResult);
				break;
			}
		}
	}
	
	// list에 HistoryVO 정보를 담고 BMIHistory호출 시 전달
	public void addHistory(double height, double weight, double bmiResult, String textResult) {
		// 기록을 담을 HistoryVO 객체
		HistoryInsertVO hivo = 
			new HistoryInsertVO(name, height, weight, bmiResult, textResult);
		System.out.println(hivo);
		
		try {
			h_dao.insertOneData(hivo);
			JOptionPane.showMessageDialog(bv, "새 정보가 추가되었습니다.");
			listRow.clear();
			listRow = h_dao.selectAllHistory();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(bv, "정보를 추가하는데 실패했습니다.");
			e.printStackTrace();
		}
	}
	
	// 실행되면 listRow가 초기화되고(로드), saveHistory가 호출되면 기존 listRow정보와
	// 결과 눌렀을 때 JOptionPane ConfirmDialog 띄우고, 추가한다 하면 메소드 호출해서 추가하도록 구현
	// saveHistory는 종료전에 수행, addHistory는 Confirm OK할때 수행
	public void saveHistory() {
		ObjectOutputStream oos = null;
		
		try {
			FileDialog fd = new FileDialog(bv, "History 저장 경로선택", FileDialog.SAVE);
			fd.setFilenameFilter(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return new File(dir, name).isDirectory();
				}
			});
			fd.setVisible(true);

			// directory 정보만 읽어와서 그 곳에 저장함
			// 그러기 위해선 파일명을 입력해야 하는 이슈 ///////////////
			String path = fd.getDirectory();
			
			File file = new File(path);
			
			if (!file.isDirectory()) {
				JOptionPane.showMessageDialog(bv, "경로를 잘못입력하셨습니다.");
				return;
			}
			
			oos = new ObjectOutputStream(new FileOutputStream(
					new File(path+"/"+new Date().getTime()+"_history.dat")));

			// "날짜","키","몸무게","BMI지수","결과" 
			// listRow로부터 HistoryVO 데이터를 가져와서 CSV String 데이터로 가공
			HistoryVO tempVO = null;
			
			for(int i=0; i<listRow.size(); i++) {
				tempVO = listRow.get(i);
				oos.writeObject(tempVO);
				oos.flush();
			}
			
		} catch (NullPointerException npe) { 
			JOptionPane.showMessageDialog(bv, "저장하지 않고 종료합니다.");
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
