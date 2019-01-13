package swing_run;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import swing_model.HistoryDAO;
import swing_model.HistoryVO;
import swing_view.BMIView;

public class BMIRun {
	
	public static void main(String[] args) {
		
		String name = "";
		do {
			name = JOptionPane.showInputDialog("이름을 입력해주세요.");
			
			if (!name.isEmpty()) {
				break;
			} else {
				JOptionPane.showMessageDialog(null, "이름을 입력해주세요.");
			}
			
		} while(true);
		

		BMIRun br = new BMIRun();
		
		// bmi_history 테이블에서 기존에 저장된 DB정보를 읽어오는 method
		HistoryDAO h_dao = HistoryDAO.getInstance();

		List<HistoryVO> listRow = null;
		try {
			listRow = h_dao.selectAllHistory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		new BMIView(listRow,name);
	}
}
