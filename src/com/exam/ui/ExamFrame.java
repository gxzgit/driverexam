package com.exam.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.exam.entity.Examer;
import com.exam.entity.QuestionBank;
import com.exam.service.IExamResDetailService;
import com.exam.service.IExamResultService;
import com.exam.service.IExamerService;
import com.exam.service.IQuestionBankService;
import com.exam.service.impl.ExamResDetailService;
import com.exam.service.impl.ExamResultService;
import com.exam.service.impl.ExamerService;
import com.exam.service.impl.QuestionBankService;
import com.exam.utils.CardEntity;
import com.exam.utils.FontInit;
import com.exam.utils.FunctUtils;

@SuppressWarnings("serial")
public class ExamFrame extends JFrame {
	IQuestionBankService qbService = new QuestionBankService(); // ����ʵ�����Ӧ��Service
	IExamResultService erService = new ExamResultService();
	IExamResDetailService erdService = new ExamResDetailService();
	IExamerService examerService = new ExamerService();

	private JPanel pane = null; // ��Ҫ��JPanel����JPanel�Ĳ��ֹ����������ó�BorderLayout
	private CardLayout card = new CardLayout(); // CardLayout���ֹ�����
	Examer em = null; // ����֮ǰ�鵽���û���Ϣ
	int totalQuestion = 0; // ��������properties�ж�ȡ
	JPanel jsouth = null; // south�������JPanel
	JPanel jwest = null; // west
	JPanel jcenter = null; // center
	Properties prop = new Properties(); // ��properties��ȡ�ⲿ�ļ�

	Integer score = 0; // ÿ����ķ�ֵ
	// Integer getValue = 0; // �ܷ�
	int nowID;
	boolean check = true;
	int wrongCount = 0;
	Integer remainValue = 100; // ʣ�µķ֣����ֵ���90��ʱ�����ʾ��������ʧ��
	List<QuestionBank> allQues; // ������
	JLabel showTime = new JLabel(); // ��ʾʱ��
	JLabel showScore = new JLabel(); // ��ʾ��ǰ��÷���
	JButton nextQues = new JButton("��һ��"); // ȷ��������һ��İ�ť
	JButton prevQues = new JButton("��һ��"); // ������һ��İ�ť
	JButton submit = new JButton("����");
	JButton[] choseButton = new JButton[4]; // �ĸ���ѡ��ť
	JButton[] jbutton; // ���ⰴť
	CardEntity[] cardEntity = null;
	String choseAns = null;
	Integer index = 0;
	int questionFlag = 0; // ��¼�����Ѿ����˼��⣨�����־ֻ��Ϊ���ÿ���ֻ�ܲ����Ѿ�������⣩
							// ȥ��������������⣬�������������
	MyActionListener mk = new MyActionListener(); // ����������
	int costTime = 0; // ���ѵ�ʱ��

	/**
	 * �Կ�����Ϣģ����г�ʼ��
	 * 
	 * @param examer
	 */
	public ExamFrame(Examer examer) {
		FontInit.InitGlobalFont(new Font("΢�������", Font.PLAIN, 18));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.em = examer;
		this.setSize(1100, 700);
		this.setLocation((screenSize.width - 1100) / 2, (screenSize.height - 700) / 2);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pane = new JPanel(new BorderLayout());
		questionInit();
		buttonInit();
		examerInit();
		nowID = erService.createResult(em.getIdCard());
		this.setContentPane(pane);
		this.setVisible(true);
	}

	/**
	 * ������ģ����г�ʼ��
	 */
	public void questionInit() {

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("examinit.properties");
		try {
			prop.load(inputStream);
			totalQuestion = Integer.valueOf(prop.getProperty("question"));
			jbutton = new JButton[totalQuestion];
			score = 100 / totalQuestion;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jcenter = new JPanel(card);
		// ��ȡ����
		allQues = qbService.getChoseQuestion(em.getIdCard());
		// ����ÿ��card��ʵ���࣬����������
		cardEntity = new CardEntity[totalQuestion];
		for (int i = 0; i < totalQuestion; i++) {
			cardEntity[i] = new CardEntity();
			JPanel oneCard = cardEntity[i].getOneCard(allQues.get(i));
			jcenter.add(oneCard, (i + 1) + "");
		}
		pane.add(jcenter, BorderLayout.CENTER);

		for (int i = 0; i < cardEntity.length; i++) {
			cardEntity[i].getRadioButtonA().addActionListener(mk);
			cardEntity[i].getRadioButtonB().addActionListener(mk);
			if (cardEntity[i].getRadioButtonC() != null && cardEntity[i].getRadioButtonD() != null) {
				cardEntity[i].getRadioButtonC().addActionListener(mk);
				cardEntity[i].getRadioButtonD().addActionListener(mk);
			}
		}
	}

	/**
	 * �԰�ť��ʼ����properties�������������ť���Ŀ���
	 */
	public void buttonInit() {

		choseButton[0] = new JButton("A");
		choseButton[1] = new JButton("B");
		choseButton[2] = new JButton("C");
		choseButton[3] = new JButton("D");
		showChoseButton(index);
		JPanel cbpl = new JPanel();
		cbpl.add(choseButton[0]);
		cbpl.add(choseButton[1]);
		cbpl.add(choseButton[2]);
		cbpl.add(choseButton[3]);
		for (int k = 0; k < 4; k++) {
			choseButton[k].addActionListener(mk);
		}
		jsouth = new JPanel(new BorderLayout());
		int grow = totalQuestion / 25 + 1;
		JPanel buttonArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20)); // ��ť��
		JPanel jbs = new JPanel(new GridLayout(grow, 25));
		// ��ʼ��������

		jbs.setBorder(BorderFactory.createTitledBorder("������Ϣ"));

		for (int i = 0; i < jbutton.length; i++) {
			jbutton[i] = new JButton(String.valueOf((i + 1)));
			jbutton[i].addActionListener(mk);
			jbs.add(jbutton[i]);
		}
		JLabel info = new JLabel("��ѡ�⣬��������ѡ����ѡ����ȷѡ��");
		info.setBorder(BorderFactory.createTitledBorder("��ʾ��Ϣ"));
		buttonArea.add(info);
		nextQues.setFont(new Font("΢�������", Font.PLAIN, 22));
		prevQues.setFont(new Font("΢�������", Font.PLAIN, 22));
		buttonArea.add(cbpl);
		buttonArea.add(prevQues);
		buttonArea.add(nextQues);
		jsouth.add(buttonArea, BorderLayout.NORTH);
		jsouth.add(jbs, BorderLayout.CENTER);
		pane.add(jsouth, BorderLayout.SOUTH);
		nextQues.addActionListener(mk);
		prevQues.addActionListener(mk);
		submit.addActionListener(mk);
	}

	// �Կ�����Ϣ���г�ʼ��
	public void examerInit() {

		jwest = new JPanel(new GridLayout(5, 1));
		jwest.setBorder(BorderFactory.createTitledBorder("������Ϣ��"));
		JLabel jex = new JLabel("����������" + em.getName());
		JLabel scoreInfo = new JLabel();

		if (em.getCount() == 0 || em.getLastScore() == null) {
			scoreInfo.setText("�״βμӿ�Ŀһ");
		} else {
			scoreInfo.setText("�ϴεķ���Ϊ" + em.getLastScore());
		}
		jwest.add(jex);
		jwest.add(scoreInfo);
		showScore.setText("��ǰ�÷�Ϊ��0");
		jwest.add(showScore);
		showTime.setBorder(BorderFactory.createTitledBorder("ʣ��ʱ�䣺"));
		jwest.add(showTime);
		submit.setPreferredSize(new Dimension(100, 50));
		jwest.add(submit);
		pane.add(jwest, BorderLayout.WEST);
		String deadline = prop.getProperty("deadline");
		Timer timer = new Timer();
		MyTask myTask = new MyTask(Integer.valueOf(deadline));
		timer.scheduleAtFixedRate(myTask, 0, 1000);
	}

	/**
	 * ����ʱ���ڲ��ࡢ��ʱ��
	 * 
	 * @author Administrator
	 *
	 */
	class MyTask extends TimerTask {
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = null;
		long deadline;
		String result;

		public MyTask(int minute) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			cal.add(Calendar.MINUTE, minute);
			deadline = cal.getTimeInMillis();
		}

		@Override
		public void run() {
			Calendar c = Calendar.getInstance();
			long spendTime = c.getTimeInMillis();
			long total = (deadline - spendTime) / 1000;
			if (total == 0) {
				JOptionPane.showMessageDialog(null, "����ʱ�䵽", "����", JOptionPane.PLAIN_MESSAGE);
				String flag = null;
				String passMessage = null;
				if (remainValue > 10) {
					passMessage = "���ź���û��ͨ����Ŀһ����";
					flag = "notpass";
				} else {
					passMessage = "��ϲ�㣬ͨ���˿�Ŀһ����";
					flag = "pass";
				}
				examerService.updateLastScore(em, 100 - remainValue);
				erService.updateResult(nowID, FunctUtils.transTime(costTime), (100 - remainValue) / score,
						remainValue / score, flag, 100 - remainValue);
				ExamFrame.this.dispose();
				new ResultFrame(em.getName(), 100 - remainValue, passMessage);

			}
			long min = total / 60;
			long second = total - min * 60;
			result = min + "�֣�" + second + "��";
			showTime.setText(result);
			costTime++;
		}
	}

	/**
	 * �����Щ����ֻ�жԴ�(ABѡ��)���Ӷ�ʹCD��ť���ɰ���
	 * 
	 * @param index
	 */
	public void showChoseButton(int index) {
		if (cardEntity[index].getRadioButtonC() != null && cardEntity[index].getRadioButtonD() != null) {
			choseButton[2].setEnabled(true);
			choseButton[3].setEnabled(true);
		} else {
			choseButton[2].setEnabled(false);
			choseButton[3].setEnabled(false);
		}
	}

	// һ��ȷ�ϣ�ԭ�ȵİ�ť���ɫ���Ͳ����Ը���ֻ���Բ鿴��
	public void setSelectButtonClose(int index) {
		cardEntity[index].getRadioButtonA().setEnabled(false);
		cardEntity[index].getRadioButtonB().setEnabled(false);
		if (cardEntity[index].getRadioButtonC() != null && cardEntity[index].getRadioButtonD() != null) {
			cardEntity[index].getRadioButtonC().setEnabled(false);
			cardEntity[index].getRadioButtonD().setEnabled(false);
		}
	}

	/**
	 * ���е���¼��ļ���
	 * 
	 * @author asus
	 *
	 */
	class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			/**
			 * ��ť����
			 */
			for (int i = 0; i < jbutton.length; i++) {
				if (e.getSource() == jbutton[i]) {
					if (i <= questionFlag) {
						card.show(jcenter, (i + 1) + "");
						index = i;
						ExamFrame.this.showChoseButton(index);
					}
				}
			}

			/**
			 * �������һ�ⰴťʱ�����Ĳ���
			 */
			if (e.getSource() == nextQues) {
				// System.out.println(choseAns + "&&" +
				// cardEntity[index].getRightFlag());
				boolean choseFlag = cardEntity[index].getRadioButtonA().isSelected()
						|| cardEntity[index].getRadioButtonB().isSelected();
				if (!choseFlag) {
					if (cardEntity[index].getRadioButtonC() != null) {
						choseFlag = cardEntity[index].getRadioButtonC().isSelected()
								|| cardEntity[index].getRadioButtonD().isSelected();
					}
				}
				if (choseFlag) {
					if (choseAns != null) {
						String flag = null;
						if (choseAns.equals(cardEntity[index].getRightFlag())) {
							jbutton[index].setText("��");
							jbutton[index].setBackground(Color.BLUE);
							remainValue -= score;
							showScore.setText("��ǰ�÷֣�" + (100 - remainValue));
							flag = "T";
						} else {
							jbutton[index].setText("��");
							jbutton[index].setBackground(Color.RED);
							wrongCount++;
							flag = "F";
							FunctUtils.showMessage(cardEntity[index].getRightExplain(), 5000, "�𰸽���");
							// JOptionPane.showMessageDialog(null,
							// cardEntity[index].getRightExplain(), "�𰸽���",
							// JOptionPane.PLAIN_MESSAGE);
						}
						ExamFrame.this.setSelectButtonClose(index);
						erdService.inserExamResDetail(FunctUtils.autoInckey("exam_result"), allQues.get(index).getId(),
								flag, choseAns);
						choseAns = null;
						questionFlag++;
					}

					if (wrongCount > 0.1 * (100 / score)) {
						if (check) {
							// JOptionPane.showMessageDialog(null,
							// "��Ϊ��ĳɼ��Ѿ�����90�֣����ź���û��ͨ������", "������ʾ",
							// JOptionPane.PLAIN_MESSAGE);
							FunctUtils.showMessage("��Ϊ��ĳɼ��Ѿ�����90�֣����ź���û��ͨ������", 10000, "������ʾ");
							check = false;
						}
					}
					if (index < totalQuestion - 1) {
						choseAns = null;
						index++;
						card.next(jcenter);
					} else {
						FunctUtils.showMessage("���Ѿ������һ���ˣ�����������ύ", 3000, "������ʾ");
					}

					ExamFrame.this.showChoseButton(index);
				}
			}

			/**
			 * �����һ��ʱ��ť�����Ĳ���
			 */
			if (e.getSource() == prevQues) {
				boolean choseFlag = cardEntity[index].getRadioButtonA().isSelected()
						|| cardEntity[index].getRadioButtonB().isSelected();
				if (!choseFlag) {
					if (cardEntity[index].getRadioButtonC() != null) {
						choseFlag = cardEntity[index].getRadioButtonC().isSelected()
								|| cardEntity[index].getRadioButtonD().isSelected();
					}
				}
				if (!choseFlag) {
					if (index > 0) {
						card.previous(jcenter);
						index--;
					} else {
						JOptionPane.showMessageDialog(null, "���ǵ�һ��������������", "������ʾ", JOptionPane.PLAIN_MESSAGE);
					}
				}
				ExamFrame.this.showChoseButton(index);
			}

			// �����ѡ��ť�����Ĳ���
			for (int i = 0; i < cardEntity.length; i++) {
				if (e.getSource() == cardEntity[i].getRadioButtonA()) {
					choseAns = "A";
				} else if (e.getSource() == cardEntity[i].getRadioButtonB()) {
					choseAns = "B";
				} else if (e.getSource() == cardEntity[i].getRadioButtonC()) {
					choseAns = "C";
				} else if (e.getSource() == cardEntity[i].getRadioButtonD()) {
					choseAns = "D";
				}
			}

			// Ϊ��ѡ��ť������ݵ��ĸ�Button�����û�����
			if (cardEntity[index].getRadioButtonA().isEnabled()) {
				if (e.getSource() == choseButton[0]) {
					cardEntity[index].getRadioButtonA().setSelected(true);
					choseAns = "A";
				} else if (e.getSource() == choseButton[1]) {
					cardEntity[index].getRadioButtonB().setSelected(true);
					choseAns = "B";
				} else if (e.getSource() == choseButton[2]) {
					cardEntity[index].getRadioButtonC().setSelected(true);
					choseAns = "C";
				} else if (e.getSource() == choseButton[3]) {
					cardEntity[index].getRadioButtonD().setSelected(true);
					choseAns = "D";
				}
			}

			if (e.getSource() == submit) {

				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�ύ��", "������ʾ", JOptionPane.YES_NO_OPTION);// i=0/1
				if (i == JOptionPane.YES_OPTION) {
					String flag = null;
					String passMessage = null;
					if (remainValue > 10) {
						passMessage = "���ź���û��ͨ����Ŀһ����";
						flag = "notpass";
					} else {
						passMessage = "��ϲ�㣬ͨ���˿�Ŀһ����";
						flag = "pass";
					}
					examerService.updateLastScore(em, 100 - remainValue);
					erService.updateResult(nowID, FunctUtils.transTime(costTime), (100 - remainValue) / score,
							remainValue / score, flag, 100 - remainValue);
					ExamFrame.this.dispose();
					new ResultFrame(em.getName(), 100 - remainValue, passMessage);
				}
			}
			// System.out.println(value);
			// System.out.println(index);
		}
	}
}