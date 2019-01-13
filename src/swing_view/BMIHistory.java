package swing_view;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import swing_model.HistoryVO;

///////////////////////////// 12-21-2018 BMI History 구현 ///////////////////////////
public class BMIHistory {
	private BMIView bv;
	private DefaultTableModel dtm;
	private List<HistoryVO> listRow;
	
	public BMIHistory(BMIView bv, List<HistoryVO> listRow) {
		this.bv = bv;
		this.listRow = listRow;
		showHistory();
	}
	
	@SuppressWarnings("serial")
	public void showHistory() {
		String[] columnNames = { "이름","키","몸무게","BMI지수","결과","날짜" };
		String[] rowData = new String[columnNames.length]; 
		dtm = new DefaultTableModel(columnNames, 0) {
			// 테이블 변경 막음
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		HistoryVO tempVO = null;
		for(int i=0; i<listRow.size(); i++) {
			tempVO = listRow.get(i);
			rowData[0] = tempVO.getName();
			rowData[1] = String.format("%3.2f", tempVO.getHeight()*100);
			rowData[2] = String.valueOf(tempVO.getWeight());
			rowData[3] = String.format("%3.2f", tempVO.getBmiNum());
			rowData[4] = tempVO.getBmiResult();
			rowData[5] = tempVO.getDate();
			dtm.addRow(rowData);
		}
		
		JTable jt = new JTable(dtm);
		JScrollPane jsp = new JScrollPane(jt);
		// 날짜, 결과 컬럼 폭 조정
		jt.getColumnModel().getColumn(4).setPreferredWidth(120);
		jt.getColumnModel().getColumn(5).setPreferredWidth(120);
		JOptionPane.showMessageDialog(bv, jsp, "BMI Checker - History", JOptionPane.PLAIN_MESSAGE);
	}
}
