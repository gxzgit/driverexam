package com.exam.dao;

import java.sql.SQLException;
import java.util.Date;

import com.exam.entity.ExamResult;
import com.exam.utils.JdbcUtils;

public class ExamResultDao {

	/**
	 * ����һ���µĿ����ɼ��ܱ�����ʱ�����ɼ���
	 * @param idCard
	 * @param date
	 */
	public void createResult(String idCard, Date date) {
		String sql = "insert into exam_result(idCard,examDate) values(?,?)";
		try {
			JdbcUtils.getQuerrRunner().update(sql, idCard, date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �ڿ���������󣬶�֮ǰ�����Ŀ��������и���
	 * @param er
	 */
	public void updateResult(ExamResult er) {
		String sql = "UPDATE exam_result SET costTime=?,correctAnswers=?,"
				+ "wrongAnswers=?,flag=?,lastScore=? WHERE id=?";
		try {
			JdbcUtils.getQuerrRunner().update(sql, er.getCostTime(), er.getCorrectAnswers(), er.getWrongAnswers(),
					er.getFlag(), er.getLastScore(), er.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}