package com.exam.service.impl;

import com.exam.dao.ExamerDao;
import com.exam.entity.Examer;
import com.exam.service.IExamerService;

public class ExamerService implements IExamerService {
	ExamerDao examerDao = new ExamerDao();

	/**
	 * ͨ������֤��ѯ�Ƿ����������
	 */
	@Override
	public Examer checkIdCard(String pra) {
		// TODO Auto-generated method stub
		return examerDao.checkIdCard(pra);
	}

	/**
	 * ���¿����Ŀ��Դ���
	 */
	@Override
	public void updateCount(int id, int count) {
		// TODO Auto-generated method stub
		examerDao.updateCount(id, count);
	}
	
	/**
	 * �������һ�εĿ��Գɼ�
	 */
	public void updateLastScore(Examer ex,int score){
		examerDao.updateLastScore(ex, score);
	}

}