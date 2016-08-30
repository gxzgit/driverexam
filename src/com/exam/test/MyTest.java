package com.exam.test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.exam.entity.Examer;
import com.exam.entity.QuestionBank;
import com.exam.service.impl.QuestionBankService;
import com.exam.utils.FunctUtils;
import com.exam.utils.JdbcUtils;

public class MyTest {
	@Test
	public void insertExamer() {
		Examer examer = new Examer();
		examer.setId(10);
		examer.setIdCard("330523");
		examer.setName("李强");
		examer.setCount(0);
		String sql = "insert into exam_examer(id,idCard,name,count) values(?,?,?,?)";

		try {
			JdbcUtils.getQuerrRunner().update(sql, examer.getId(), examer.getIdCard(), examer.getName(),
					examer.getCount());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void insertRes() {
		String sql = "insert into exam_result(id,flag) values(?,?)";
		try {
			JdbcUtils.getQuerrRunner().update(sql, "10", "notpass");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		ImageIcon image = new ImageIcon(MyTest.class.getResource("/image/4.jpg"));// 图片在类中的相对位置
//		JLabel labImage = new JLabel(image);// 加入Label
//		image.setImage(image.getImage().getScaledInstance(200, 100, Image.SCALE_DEFAULT));
//		JFrame jf = new JFrame();
//		jf.add(labImage);
//		jf.setVisible(true);
//		jf.pack();
//	}
	@Test
	public void testAo(){
		int k = FunctUtils.autoInckey("exam_result");
		System.out.println(k);
	}
	
	@Test
	public void randomNum(){
		int[] rd = FunctUtils.randomCommon(1, 100, 20);
		Arrays.sort(rd);
		for (int i = 0; i < rd.length; i++) {
			System.out.print(rd[i]+"  ");
		}
	}
	
	@Test
	public void testTime(){
		String transTime = FunctUtils.transTime(100);
		System.out.println(transTime);
	}
	@Test
	public void getWrongQues(){
		QuestionBankService qbs = new QuestionBankService();
		List<QuestionBank> ss = qbs.getChoseQuestion("1");
		for (QuestionBank questionBank : ss) {
			System.out.println(questionBank.getId());
		}
	}
	
	
}
