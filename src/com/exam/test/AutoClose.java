package com.exam.test;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class AutoClose {

	/**
	 * 测试对话框自动关闭
	 * 
	 * @param args
	 */
	public static void showMessage() {
		JOptionPane op = new JOptionPane("本对话框将在3秒后关闭", JOptionPane.INFORMATION_MESSAGE);
		final JDialog dialog = op.createDialog("");
		// 创建一个新计时器
		Timer timer = new Timer();
		// 30秒 后执行该任务
		timer.schedule(new TimerTask() {
			public void run() {
				dialog.setVisible(false);
				dialog.dispose();
			}
		}, 3000);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setAlwaysOnTop(true);
		dialog.setModal(false);
		dialog.setVisible(true);
	}
	public static void main(String[] args) {
		AutoClose.showMessage();
	}
}
