package com.exam.service.impl;

import com.exam.dao.ExamerDao;
import com.exam.entity.Examer;
import com.exam.service.IExamerService;

public class ExamerService implements IExamerService {
	ExamerDao examerDao = new ExamerDao();

	/**
	 * 通过身份证查询是否有这个考生
	 */
	@Override
	public Examer checkIdCard(String pra) {
		// TODO Auto-generated method stub
		return examerDao.checkIdCard(pra);
	}

	/**
	 * 更新考生的考试次数
	 */
	@Override
	public void updateCount(int id, int count) {
		// TODO Auto-generated method stub
		examerDao.updateCount(id, count);
	}
	
	/**
	 * 更新最后一次的考试成绩
	 */
	public void updateLastScore(Examer ex,int score){
		examerDao.updateLastScore(ex, score);
	}

}
