package com.exam.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.exam.entity.Examer;
import com.exam.utils.JdbcUtils;

public class ExamerDao {
	
	/**
	 * 检查输入的身份证是否正确
	 * 返回的是一个exam对象，传递给界面二
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
	 * 更新考生的最新成绩（和成绩表有冗余），
	 * 因为每次开始考试的时候都需要查询考生上一次的成绩
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
	 * 根据考生的id（主键）更新考生的考试次数
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
