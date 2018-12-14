package swing_view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import wing_evt.BMIEvt;

public class BMIView extends JFrame {

	private JTextField jtfHeight, jtfWeight;
	private JButton jbCal, jbExit;
	private JTextArea jtaInfo;
	private JLabel jlInfo;
	
	public BMIView() {
		
		super("BMI Checker");
		jlInfo = new JLabel("BMI��?");
		JLabel jlHeight = new JLabel("Ű(cm) : ");
		JLabel jlWeight = new JLabel("������(kg) : ");
		jtaInfo = new JTextArea(
				"�� ��� ����� ��ü���������� BMI(Body Mass Index)�� "
				+ "�ٰ��ѹ������ BMI����= ������(kg) �� (����(m) �� ����(m))�Դϴ�. "
				+ "����� ���� 18.5 ���ϸ� ��ü��, 18.5~23�� ����, 23~25�� ��ü��, "
				+ "25~30�� ��, 30�̻��� �������� ���������ϴ�.", 10, 10);
		jtaInfo.setEditable(false);
		jtaInfo.setLineWrap(true);
		
		jbCal = new JButton("���");
		jbExit = new JButton("����");
		
		jtfHeight = new JTextField();
		jtfWeight = new JTextField();
		
		jlInfo.setBounds(15,20, 45, 20);
		add(jlInfo);
		
		jtaInfo.setBounds(15,40,320, 90);
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
		
		BMIEvt eventHandler = new BMIEvt(this);
		jbCal.addActionListener(eventHandler);
		jbExit.addActionListener(eventHandler);
		
		setLayout(null);
		setResizable(false);
		setBounds(400, 250, 350, 290);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	public JLabel getJlInfo() {
		return jlInfo;
	}
}
