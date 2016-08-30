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
	 * 显示图片
	 * 
	 * @param url
	 * @return
	 */
	public static JLabel getImage(String url) {
		ImageIcon image = new ImageIcon(ExamFrame.class.getResource(url));// 图片在类中的相对位置
		image.setImage(image.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT));
		JLabel labImage = new JLabel(image);// 加入Label
		return labImage;
	}

	/**
	 * 获取最新的一个id，用来得到考生成绩总表的id，因为信息详细表要先得到总表的id
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
	 * 随机数获得模块，min最小值，max最大值，n为要获取的随机数个数
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
	 * 将得到的秒数转换XX分XX秒
	 */
	public static String transTime(int time) {
		int min = time/60;
		int second = time - min * 60;
		return min + "分："+second+"秒";
	}
	
	/**
	 * 显示提示框，一定时间后关闭
	 * @param mess
	 * @param stime
	 * @param title
	 */
	public static void showMessage(String mess,int stime,String title) {
		JOptionPane op = new JOptionPane(mess, JOptionPane.INFORMATION_MESSAGE);
		final JDialog dialog = op.createDialog(title);
		// 创建一个新计时器
		final Timer timer = new Timer();
		// 30秒 后执行该任务
		
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
