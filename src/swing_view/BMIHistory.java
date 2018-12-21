package swing_view;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import swing_evt.HistoryVO;

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
		String[] columnNames = { "��¥","Ű","������","BMI����","���" };
		String[] rowData = new String[columnNames.length]; 
		dtm = new DefaultTableModel(columnNames, 0) {
			// ���̺� ���� ����
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		for(int i=0; i<listRow.size(); i++) {
			rowData[0] = listRow.get(i).getDate();
			rowData[1] = String.format("%3.2f", listRow.get(i).getHeight()*100);
			rowData[2] = String.valueOf(listRow.get(i).getWeight());
			rowData[3] = String.format("%3.2f", listRow.get(i).getBmiNum());
			rowData[4] = listRow.get(i).getBmiResult();
			dtm.addRow(rowData);
		}
		
		JTable jt = new JTable(dtm);
		JScrollPane jsp = new JScrollPane(jt);
		// ��¥, ��� �÷� �� ����
		jt.getColumnModel().getColumn(0).setPreferredWidth(120);
		jt.getColumnModel().getColumn(4).setPreferredWidth(120);
		JOptionPane.showMessageDialog(bv, jsp, "BMI Checker - History", JOptionPane.PLAIN_MESSAGE);
	}
}
