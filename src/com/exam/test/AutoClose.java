package com.exam.test;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class AutoClose {

	/**
	 * ���ԶԻ����Զ��ر�
	 * 
	 * @param args
	 */
	public static void showMessage() {
		JOptionPane op = new JOptionPane("���Ի�����3���ر�", JOptionPane.INFORMATION_MESSAGE);
		final JDialog dialog = op.createDialog("");
		// ����һ���¼�ʱ��
		Timer timer = new Timer();
		// 30�� ��ִ�и�����
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