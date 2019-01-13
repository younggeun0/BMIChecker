package swing_view;

import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import swing_controller.BMIViewEvt;
import swing_model.HistoryVO;

@SuppressWarnings("serial")
public class BMIView extends JFrame {

	private JTextField jtfHeight, jtfWeight;
	private JButton jbCal, jbExit, jbHistory, jbSaveHistory, jbLoadHistory;
	private JTextArea jtaInfo;
	
	public BMIView(List<HistoryVO> listRow, String name) {
		
		super("BMI Checker");
		JLabel jlHeight = new JLabel("Ű(cm) : ");
		JLabel jlWeight = new JLabel("������(kg) : ");
		jtaInfo = new JTextArea(
				"�� ��� ����� ��ü���������� BMI(Body Mass Index)�� "
				+ "�ٰ��ѹ������ BMI����= ������(kg) �� (����(m) �� ����(m))�Դϴ�. "
				+ "����� ���� 18.5 ���ϸ� ��ü��, 18.5~23�� ����, 23~25�� ��ü��, "
				+ "25~30�� ��, 30�̻��� �������� ���������ϴ�.", 10, 10);
		TitledBorder tbTa = new TitledBorder("BMI��?"); 
		jtaInfo.setBorder(tbTa);
		
		jtaInfo.setEditable(false);
		jtaInfo.setLineWrap(true);
		
		jbCal = new JButton("���"/*, iiCal*/);
		jbExit = new JButton("����"/*, iiExit*/);
		jbHistory = new JButton("History");
		jbSaveHistory = new JButton("����");
		jbLoadHistory = new JButton("�ҷ�����");
		
		jtfHeight = new JTextField();
		jtfWeight = new JTextField();
		
		jtaInfo.setBounds(15,20,315, 120);
		add(jtaInfo);
		
		jlHeight.setBounds(36, 160, 50, 20);
		add(jlHeight);
		
		jlWeight.setBounds(15, 185, 70, 20);
		add(jlWeight);
		
		jtfHeight.setBounds(100, 160, 80, 20);
		add(jtfHeight);
		
		jtfWeight.setBounds(100, 185, 80, 20);
		add(jtfWeight);
		
		jbCal.setBounds(185, 160, 150, 40);
		add(jbCal);
		
		jbExit.setBounds(255, 210, 80, 40);
		add(jbExit);
		
		////////12-20-2018 History ���� ///////
		jbHistory.setBounds(170, 210, 80, 40);
		add(jbHistory);
		
		////////01-13-2019 DB�� ����� History SAVE, LOAD ���� ///////
		jbSaveHistory.setBounds(10, 210, 60, 40);
		add(jbSaveHistory);
		
		jbLoadHistory.setBounds(75, 210, 90, 40);
		add(jbLoadHistory);
		
		BMIViewEvt bve = new BMIViewEvt(this, listRow, name);
		jbCal.addActionListener(bve);
		jbExit.addActionListener(bve);
		jbHistory.addActionListener(bve);
		jbSaveHistory.addActionListener(bve);
		jbLoadHistory.addActionListener(bve);
		
		setLayout(null);
		setResizable(false);
		setBounds(400, 250, 350, 290);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JButton getJbHistory() {
		return jbHistory;
	}
	public JTextField getJtfHeight() {
		return jtfHeight;
	}
	public JTextField getJtfWeight() {
		return jtfWeight;
	}
	public JButton getJbCal() {
		return jbCal;
	}
	public JButton getJbExit() {
		return jbExit;
	}
	public JTextArea getJtaInfo() {
		return jtaInfo;
	}
	public JButton getJbSaveHistory() {
		return jbSaveHistory;
	}
	public JButton getJbLoadHistory() {
		return jbLoadHistory;
	}
}
