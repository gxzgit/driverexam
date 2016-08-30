package com.exam.utils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.exam.entity.QuestionBank;

/**
 * ÿ��CardLayout��װ��Ϊһ��ʵ��
 * @author Administrator
 *
 */
public class CardEntity {

	private static int index = 0;
	//���ñ��
	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		CardEntity.index = index;
	}

	JPanel jpQues = new JPanel(new BorderLayout());// ��������JPanel
	JPanel centerArea = new JPanel(new FlowLayout(FlowLayout.CENTER)); // ������
	JLabel title = new JLabel();	//����
	ButtonGroup group = new ButtonGroup(); 	//��ť��
	JPanel containQues = new JPanel();		//�����ĸ�ѡ��
	JRadioButton radioButtonA = null;
	JRadioButton radioButtonB = null;
	JRadioButton radioButtonC = null;
	JRadioButton radioButtonD = null;
	String rightFlag = null;	//��ȷѡ��
	String rightExplain =null;	//����
	public String getRightExplain() {
		return rightExplain;
	}

	public void setRightExplain(String rightExplain) {
		this.rightExplain = rightExplain;
	}

	public String getRightFlag() {
		return rightFlag;
	}

	public void setRightFlag(String rightFlag) {
		this.rightFlag = rightFlag;
	}

	public JPanel getJpQues() {
		return jpQues;
	}

	public void setJpQues(JPanel jpQues) {
		this.jpQues = jpQues;
	}

	public JPanel getCenterArea() {
		return centerArea;
	}

	public void setCenterArea(JPanel centerArea) {
		this.centerArea = centerArea;
	}

	public JLabel getTitle() {
		return title;
	}

	public void setTitle(JLabel title) {
		this.title = title;
	}

	public ButtonGroup getGroup() {
		return group;
	}

	public void setGroup(ButtonGroup group) {
		this.group = group;
	}

	public JPanel getContainQues() {
		return containQues;
	}

	public void setContainQues(JPanel containQues) {
		this.containQues = containQues;
	}

	public JRadioButton getRadioButtonA() {
		return radioButtonA;
	}

	public void setRadioButtonA(JRadioButton radioButtonA) {
		this.radioButtonA = radioButtonA;
	}

	public JRadioButton getRadioButtonB() {
		return radioButtonB;
	}

	public void setRadioButtonB(JRadioButton radioButtonB) {
		this.radioButtonB = radioButtonB;
	}

	public JRadioButton getRadioButtonC() {
		return radioButtonC;
	}

	public void setRadioButtonC(JRadioButton radioButtonC) {
		this.radioButtonC = radioButtonC;
	}

	public JRadioButton getRadioButtonD() {
		return radioButtonD;
	}

	public void setRadioButtonD(JRadioButton radioButtonD) {
		this.radioButtonD = radioButtonD;
	}

	/**
	 * ��һ������תΪһ��CardLayout
	 * @param ques
	 * @return
	 */
	public JPanel getOneCard(QuestionBank ques) {
		title.setText(ques.getQuestion());
		title.setFont(new Font("΢�������", Font.PLAIN, 22));
		title.setBorder(BorderFactory.createTitledBorder("��"+index+"��"));
		// ��ʼ������ѡ����ģ��
		if (ques.getPicUrl() != null) {
			JLabel image = FunctUtils.getImage(ques.getPicUrl());
			centerArea.add(image);
		}
		group = new ButtonGroup();// ������ѡ��ť��
		radioButtonA = new JRadioButton(ques.getOptionA());// ������ѡ��ť
		radioButtonB = new JRadioButton(ques.getOptionB());// ������ѡ��ť
		rightFlag = ques.getRightAns();					   //��ȷ��
		rightExplain = ques.getRightExplain();			   //����
		containQues.setLayout(new GridLayout(4, 1));	//���񲼾�
		containQues.add(radioButtonA);
		containQues.add(radioButtonB);
		group.add(radioButtonA);// ��radioButton1���ӵ���ѡ��ť����
		group.add(radioButtonB);// ��radioButton2���ӵ���ѡ��ť����

		if (ques.getOptionC() != null || ques.getOptionD() != null) {
			radioButtonC = new JRadioButton(ques.getOptionC());// ������ѡ��ť
			radioButtonD = new JRadioButton(ques.getOptionD());// ������ѡ��ť
			group.add(radioButtonC);// ��radioButton3���ӵ���ѡ��ť����
			group.add(radioButtonD);// ��radioButton3���ӵ���ѡ��ť����
			containQues.add(radioButtonC);
			containQues.add(radioButtonD);
		}

		centerArea.add(containQues, BorderLayout.CENTER);
		// ������һ��İ�ťģ��

		// ��������ģ��
		jpQues.add(title, BorderLayout.NORTH);
		jpQues.add(centerArea, BorderLayout.CENTER);
		return jpQues;
	}

	public CardEntity() {
		index++;
	}
}