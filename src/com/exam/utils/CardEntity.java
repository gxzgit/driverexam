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
 * 每个CardLayout封装成为一个实体
 * @author Administrator
 *
 */
public class CardEntity {

	private static int index = 0;
	//设置标记
	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		CardEntity.index = index;
	}

	JPanel jpQues = new JPanel(new BorderLayout());// 单个问题JPanel
	JPanel centerArea = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 问题区
	JLabel title = new JLabel();	//标题
	ButtonGroup group = new ButtonGroup(); 	//按钮组
	JPanel containQues = new JPanel();		//包含四个选项
	JRadioButton radioButtonA = null;
	JRadioButton radioButtonB = null;
	JRadioButton radioButtonC = null;
	JRadioButton radioButtonD = null;
	String rightFlag = null;	//正确选项
	String rightExplain =null;	//解释
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
	 * 将一个问题转为一个CardLayout
	 * @param ques
	 * @return
	 */
	public JPanel getOneCard(QuestionBank ques) {
		title.setText(ques.getQuestion());
		title.setFont(new Font("微软简标宋", Font.PLAIN, 22));
		title.setBorder(BorderFactory.createTitledBorder("第"+index+"题"));
		// 开始创建单选区的模块
		if (ques.getPicUrl() != null) {
			JLabel image = FunctUtils.getImage(ques.getPicUrl());
			centerArea.add(image);
		}
		group = new ButtonGroup();// 创建单选按钮组
		radioButtonA = new JRadioButton(ques.getOptionA());// 创建单选按钮
		radioButtonB = new JRadioButton(ques.getOptionB());// 创建单选按钮
		rightFlag = ques.getRightAns();					   //正确答案
		rightExplain = ques.getRightExplain();			   //解释
		containQues.setLayout(new GridLayout(4, 1));	//表格布局
		containQues.add(radioButtonA);
		containQues.add(radioButtonB);
		group.add(radioButtonA);// 将radioButton1增加到单选按钮组中
		group.add(radioButtonB);// 将radioButton2增加到单选按钮组中

		if (ques.getOptionC() != null || ques.getOptionD() != null) {
			radioButtonC = new JRadioButton(ques.getOptionC());// 创建单选按钮
			radioButtonD = new JRadioButton(ques.getOptionD());// 创建单选按钮
			group.add(radioButtonC);// 将radioButton3增加到单选按钮组中
			group.add(radioButtonD);// 将radioButton3增加到单选按钮组中
			containQues.add(radioButtonC);
			containQues.add(radioButtonD);
		}

		centerArea.add(containQues, BorderLayout.CENTER);
		// 创建下一题的按钮模块

		// 添加上述模块
		jpQues.add(title, BorderLayout.NORTH);
		jpQues.add(centerArea, BorderLayout.CENTER);
		return jpQues;
	}

	public CardEntity() {
		index++;
	}
}
