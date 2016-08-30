package com.exam.utils;

import java.awt.Image;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.exam.ui.ExamFrame;

public class FunctUtils {
	
	
	/**
	 * ��ʾͼƬ
	 * 
	 * @param url
	 * @return
	 */
	public static JLabel getImage(String url) {
		ImageIcon image = new ImageIcon(ExamFrame.class.getResource(url));// ͼƬ�����е����λ��
		image.setImage(image.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT));
		JLabel labImage = new JLabel(image);// ����Label
		return labImage;
	}

	/**
	 * ��ȡ���µ�һ��id�������õ������ɼ��ܱ���id����Ϊ��Ϣ��ϸ��Ҫ�ȵõ��ܱ���id
	 * 
	 * @param tableName
	 * @return
	 */
	public static int autoInckey(String tableName) {
		String sql = "SELECT MAX(id) FROM " + tableName;
		long res = 0;
		try {
			res = JdbcUtils.getQuerrRunner().query(sql, new ScalarHandler<Long>());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int key = Integer.parseInt(String.valueOf(res));
		return key;
	}

	/**
	 * ��������ģ�飬min��Сֵ��max���ֵ��nΪҪ��ȡ�����������
	 * 
	 * @param min
	 * @param max
	 * @param n
	 * @return
	 */
	public static int[] randomCommon(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}

	/**
	 * ���õ�������ת��XX��XX��
	 */
	public static String transTime(int time) {
		int min = time/60;
		int second = time - min * 60;
		return min + "�֣�"+second+"��";
	}
	
	/**
	 * ��ʾ��ʾ��һ��ʱ���ر�
	 * @param mess
	 * @param stime
	 * @param title
	 */
	public static void showMessage(String mess,int stime,String title) {
		JOptionPane op = new JOptionPane(mess, JOptionPane.INFORMATION_MESSAGE);
		final JDialog dialog = op.createDialog(title);
		// ����һ���¼�ʱ��
		final Timer timer = new Timer();
		// 30�� ��ִ�и�����
		
		TimerTask tk = new TimerTask() {	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dialog.setVisible(false);
				dialog.dispose();
			}
		};
		timer.schedule(tk, stime);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setAlwaysOnTop(true);
		dialog.setModal(false);
		dialog.setVisible(true);
	}

}