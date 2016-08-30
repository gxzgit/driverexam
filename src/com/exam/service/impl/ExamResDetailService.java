package com.exam.service.impl;

import com.exam.dao.ExamResDetailDao;
import com.exam.entity.ExamResDetail;
import com.exam.service.IExamResDetailService;

public class ExamResDetailService implements IExamResDetailService {
	ExamResDetailDao erdDao = new ExamResDetailDao();

	/**
	 * 插入一条答题情况
	 */
	public void inserExamResDetail(ExamResDetail erd) {
		erdDao.inserExamResDetail(erd);
	}
	
	/**
	 * 对上面的方法进行改进，符合UI时候的要求（避免UI层再去封装一个ExamResDetail）
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
