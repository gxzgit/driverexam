package com.exam.service.impl;

import com.exam.dao.ExamResDetailDao;
import com.exam.entity.ExamResDetail;
import com.exam.service.IExamResDetailService;

public class ExamResDetailService implements IExamResDetailService {
	ExamResDetailDao erdDao = new ExamResDetailDao();

	/**
	 * ����һ���������
	 */
	public void inserExamResDetail(ExamResDetail erd) {
		erdDao.inserExamResDetail(erd);
	}
	
	/**
	 * ������ķ������иĽ�������UIʱ���Ҫ�󣨱���UI����ȥ��װһ��ExamResDetail��
	 * 
	 */
	public void inserExamResDetail(int resultId,int quesId,String flag,String choseAns){
		ExamResDetail erd = new ExamResDetail();
		erd.setResultId(resultId);
		erd.setQuesId(quesId);
		erd.setFlag(flag);
		erd.setChoseAns(choseAns);
		erdDao.inserExamResDetail(erd);
	}
}