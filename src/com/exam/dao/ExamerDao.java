package com.exam.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.exam.entity.Examer;
import com.exam.utils.JdbcUtils;

public class ExamerDao {
	
	/**
	 * ������������֤�Ƿ���ȷ
	 * ���ص���һ��exam���󣬴��ݸ������
	 * @param pra
	 * @return
	 */
	public Examer checkIdCard(String pra) {
		String sql = "select * from exam_examer where idCard = ?";
		try {
			Examer examer = JdbcUtils.getQuerrRunner().query(sql, new BeanHandler<Examer>(Examer.class),
					pra);
			return examer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ���¿��������³ɼ����ͳɼ��������ࣩ��
	 * ��Ϊÿ�ο�ʼ���Ե�ʱ����Ҫ��ѯ������һ�εĳɼ�
	 * @param ex
	 * @param score
	 */
	public void updateLastScore(Examer ex,int score){
		String sql = "UPDATE exam_examer SET lastScore=? WHERE id=?";
		try {
			JdbcUtils.getQuerrRunner().update(sql,score,ex.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ݿ�����id�����������¿����Ŀ��Դ���
	 * @param id
	 * @param count
	 */
	public void updateCount(int id,int count){
		String sql = "UPDATE exam_examer SET count=? WHERE id=?";
		try {
			JdbcUtils.getQuerrRunner().update(sql,count,id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}