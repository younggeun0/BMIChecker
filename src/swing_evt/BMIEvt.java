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
			saveHistory();
			
			if(exitFlag) {
				bv.dispose();
			}
		}

		if (e.getSource() == bv.getJbHistory()) {
			// history버튼 클릭시 BMIHistory 호출
			new BMIHistory(bv, listRow);
		}
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
	
	// 실행되면 listRow가 초기화되고(로드), saveHistory가 호출되면 기존 listRow정보와
	// 결과 눌렀을 때 JOptionPane ConfirmDialog 띄우고, 추가한다 하면 메소드 호출해서 추가하도록 구현
	// saveHistory는 종료전에 수행, addHistory는 Confirm OK할때 수행
	public void saveHistory() {
		
		ObjectOutputStream oos = null;
		
		try {
			
			String path = JOptionPane.showInputDialog("저장할 경로를 입력해주세요.예)C:/dev/home");
			
			File file = new File(path);
			
			if (!file.isDirectory()) {
				JOptionPane.showMessageDialog(bv, "경로를 잘못입력하셨습니다.");
				return;
			}
			
			oos = new ObjectOutputStream(new FileOutputStream(
					new File(path+"/history.dat")));

			// "날짜","키","몸무게","BMI지수","결과" 
			// listRow로부터 HistoryVO 데이터를 가져와서 CSV String 데이터로 가공
			HistoryVO tempVO = null;
			
			for(int i=0; i<listRow.size(); i++) {
				tempVO = listRow.get(i);
				oos.writeObject(tempVO);
				oos.flush();
			}
			
			exitFlag = true;
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
