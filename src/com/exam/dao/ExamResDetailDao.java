package com.exam.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.exam.entity.ExamResDetail;
import com.exam.utils.JdbcUtils;

public class ExamResDetailDao {

	/**
	 * ��exam_result_detail����������
	 * @param erd
	 */
	public void inserExamResDetail(ExamResDetail erd) {
		String sql = "INSERT INTO  exam_result_detail(resultId,quesId,flag,choseAns)" + "VALUES(?,?,?,?)";
		try {
			JdbcUtils.getQuerrRunner().update(sql, erd.getResultId(), erd.getQuesId(), erd.getFlag(),
					erd.getChoseAns());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ͨ������������֤��ȡ��������⣬��Ϊֻ�ܿ����Σ�����ȡǰһ�ο��Եĳɼ��д���ı�ż���
	 * SELECT MIN(id) FROM exam_result WHERE idCard=?) AND flag='F'������
	 * @param idCard
	 * @return
	 */
	public List<Integer> getWrongQuestion(String idCard){
		String sql = "SELECT quesId FROM exam_result_detail WHERE resultId=(SELECT MIN(id) FROM exam_result WHERE idCard=?) AND flag='F'";
		List<Long> ques = null;
		try {
			ques = JdbcUtils.getQuerrRunner().query(sql, new ColumnListHandler<Long>(),idCard);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Integer> res = new ArrayList<Integer>();
		for (Long l : ques) {
			res.add(l.intValue());
		}
		return res;
	}
}