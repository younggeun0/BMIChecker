package swing_view;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import swing_model.HistoryVO;

///////////////////////////// 12-21-2018 BMI History ���� ///////////////////////////
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
		String[] columnNames = { "�̸�","Ű","������","BMI����","���","��¥" };
		String[] rowData = new String[columnNames.length]; 
		dtm = new DefaultTableModel(columnNames, 0) {
			// ���̺� ���� ����
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
		// ��¥, ��� �÷� �� ����
		jt.getColumnModel().getColumn(4).setPreferredWidth(120);
		jt.getColumnModel().getColumn(5).setPreferredWidth(120);
		JOptionPane.showMessageDialog(bv, jsp, "BMI Checker - History", JOptionPane.PLAIN_MESSAGE);
	}
}
