package com.exam.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.exam.utils.FontInit;

@SuppressWarnings("serial")
public class ResultFrame extends JFrame {
	private JPanel pane = null;

	public ResultFrame(String examName, int score, String str) {
		FontInit.InitGlobalFont(new Font("华文彩云", Font.BOLD, 34));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		pane = new JPanel(new GridLayout(3, 1));
		this.setSize(600, 400);
		this.setLocation((screenSize.width - 600) / 2, (screenSize.height - 400) / 2);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(pane);
		JLabel examInfo = new JLabel(examName+"   考生:");
		JLabel jscore = new JLabel("你的分数为：            "+String.valueOf(score));
		JLabel jstr = new JLabel(str);
		pane.add(examInfo);
		pane.add(jscore);
		pane.add(jstr);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new ResultFrame("aaa", 66, "aafdfffdddd");
	}
}
