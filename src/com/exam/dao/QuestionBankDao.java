package com.exam.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.exam.entity.QuestionBank;
import com.exam.utils.JdbcUtils;

public class QuestionBankDao {
	
	/**
	 * ��ȡ��������������
	 * @return
	 */
	public List<QuestionBank> getAllQuestion(){
		String sql = " select * from exam_questionbank";
		try {
			List<QuestionBank> list = JdbcUtils.getQuerrRunner().query(sql, new BeanListHandler<QuestionBank>(QuestionBank.class));
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
}