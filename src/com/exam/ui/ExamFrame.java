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
	IQuestionBankService qbService = new QuestionBankService(); // 各个实体类对应的Service
	IExamResultService erService = new ExamResultService();
	IExamResDetailService erdService = new ExamResDetailService();
	IExamerService examerService = new ExamerService();

	private JPanel pane = null; // 主要的JPanel，该JPanel的布局管理将被设置成BorderLayout
	private CardLayout card = new CardLayout(); // CardLayout布局管理器
	Examer em = null; // 接受之前查到的用户信息
	int totalQuestion = 0; // 总题数，properties中读取
	JPanel jsouth = null; // south方面的总JPanel
	JPanel jwest = null; // west
	JPanel jcenter = null; // center
	Properties prop = new Properties(); // 以properties读取外部文件

	Integer score = 0; // 每道题的分值
	// Integer getValue = 0; // 总分
	int nowID;
	boolean check = true;
	int wrongCount = 0;
	Integer remainValue = 100; // 剩下的分，当分低于90的时候就提示考生考试失败
	List<QuestionBank> allQues; // 总题数
	JLabel showTime = new JLabel(); // 显示时间
	JLabel showScore = new JLabel(); // 显示当前获得分数
	JButton nextQues = new JButton("下一题"); // 确定进入下一题的按钮
	JButton prevQues = new JButton("上一题"); // 返回上一题的按钮
	JButton submit = new JButton("交卷");
	JButton[] choseButton = new JButton[4]; // 四个单选按钮
	JButton[] jbutton; // 答题按钮
	CardEntity[] cardEntity = null;
	String choseAns = null;
	Integer index = 0;
	int questionFlag = 0; // 记录考生已经答了几题（这个标志只是为了让考生只能查阅已经答过的题）
							// 去掉则可以随意跳题，并不会产生错误
	MyActionListener mk = new MyActionListener(); // 创建监听器
	int costTime = 0; // 花费的时间

	/**
	 * 对考生信息模块进行初始化
	 * 
	 * @param examer
	 */
	public ExamFrame(Examer examer) {
		FontInit.InitGlobalFont(new Font("微软简标宋", Font.PLAIN, 18));
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
	 * 对问题模块进行初始化
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
		// 获取问题
		allQues = qbService.getChoseQuestion(em.getIdCard());
		// 创建每个card的实体类，便于做监听
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
	 * 对按钮初始化，properties里的题数决定按钮栏的宽度
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
		JPanel buttonArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20)); // 按钮区
		JPanel jbs = new JPanel(new GridLayout(grow, 25));
		// 初始化监听器

		jbs.setBorder(BorderFactory.createTitledBorder("答题信息"));

		for (int i = 0; i < jbutton.length; i++) {
			jbutton[i] = new JButton(String.valueOf((i + 1)));
			jbutton[i].addActionListener(mk);
			jbs.add(jbutton[i]);
		}
		JLabel info = new JLabel("单选题，请在上述选项中选择正确选项");
		info.setBorder(BorderFactory.createTitledBorder("提示信息"));
		buttonArea.add(info);
		nextQues.setFont(new Font("微软简标宋", Font.PLAIN, 22));
		prevQues.setFont(new Font("微软简标宋", Font.PLAIN, 22));
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

	// 对考生信息进行初始化
	public void examerInit() {

		jwest = new JPanel(new GridLayout(5, 1));
		jwest.setBorder(BorderFactory.createTitledBorder("考生信息："));
		JLabel jex = new JLabel("考生姓名：" + em.getName());
		JLabel scoreInfo = new JLabel();

		if (em.getCount() == 0 || em.getLastScore() == null) {
			scoreInfo.setText("首次参加科目一");
		} else {
			scoreInfo.setText("上次的分数为" + em.getLastScore());
		}
		jwest.add(jex);
		jwest.add(scoreInfo);
		showScore.setText("当前得分为：0");
		jwest.add(showScore);
		showTime.setBorder(BorderFactory.createTitledBorder("剩余时间："));
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
	 * 考试时间内部类、定时器
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
				JOptionPane.showMessageDialog(null, "考试时间到", "标题", JOptionPane.PLAIN_MESSAGE);
				String flag = null;
				String passMessage = null;
				if (remainValue > 10) {
					passMessage = "很遗憾，没有通过科目一考试";
					flag = "notpass";
				} else {
					passMessage = "恭喜你，通过了科目一考试";
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
			result = min + "分：" + second + "秒";
			showTime.setText(result);
			costTime++;
		}
	}

	/**
	 * 针对有些问题只有对错(AB选项)，从而使CD按钮不可按下
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

	// 一旦确认，原先的按钮变灰色，就不可以更改只可以查看了
	public void setSelectButtonClose(int index) {
		cardEntity[index].getRadioButtonA().setEnabled(false);
		cardEntity[index].getRadioButtonB().setEnabled(false);
		if (cardEntity[index].getRadioButtonC() != null && cardEntity[index].getRadioButtonD() != null) {
			cardEntity[index].getRadioButtonC().setEnabled(false);
			cardEntity[index].getRadioButtonD().setEnabled(false);
		}
	}

	/**
	 * 所有点击事件的监听
	 * 
	 * @author asus
	 *
	 */
	class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			/**
			 * 按钮监听
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
			 * 当点击下一题按钮时发生的操作
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
							jbutton[index].setText("√");
							jbutton[index].setBackground(Color.BLUE);
							remainValue -= score;
							showScore.setText("当前得分：" + (100 - remainValue));
							flag = "T";
						} else {
							jbutton[index].setText("×");
							jbutton[index].setBackground(Color.RED);
							wrongCount++;
							flag = "F";
							FunctUtils.showMessage(cardEntity[index].getRightExplain(), 5000, "答案解析");
							// JOptionPane.showMessageDialog(null,
							// cardEntity[index].getRightExplain(), "答案解析",
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
							// "因为你的成绩已经低于90分，很遗憾，没能通过考试", "友情提示",
							// JOptionPane.PLAIN_MESSAGE);
							FunctUtils.showMessage("因为你的成绩已经低于90分，很遗憾，没能通过考试", 10000, "友情提示");
							check = false;
						}
					}
					if (index < totalQuestion - 1) {
						choseAns = null;
						index++;
						card.next(jcenter);
					} else {
						FunctUtils.showMessage("这已经是最后一题了，如果答完请提交", 3000, "友情提示");
					}

					ExamFrame.this.showChoseButton(index);
				}
			}

			/**
			 * 点击上一题时按钮发生的操作
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
						JOptionPane.showMessageDialog(null, "这是第一题了请往下做题", "友情提示", JOptionPane.PLAIN_MESSAGE);
					}
				}
				ExamFrame.this.showChoseButton(index);
			}

			// 点击单选按钮触发的操作
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

			// 为单选按钮建立快捷的四个Button提升用户体验
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

				int i = JOptionPane.showConfirmDialog(null, "确定要提交吗", "友情提示", JOptionPane.YES_NO_OPTION);// i=0/1
				if (i == JOptionPane.YES_OPTION) {
					String flag = null;
					String passMessage = null;
					if (remainValue > 10) {
						passMessage = "很遗憾，没有通过科目一考试";
						flag = "notpass";
					} else {
						passMessage = "恭喜你，通过了科目一考试";
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
