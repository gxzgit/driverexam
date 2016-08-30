package com.exam.service.impl;

import java.util.Date;

import com.exam.dao.ExamResultDao;
import com.exam.entity.ExamResult;
import com.exam.service.IExamResultService;
import com.exam.utils.FunctUtils;

public class ExamResultService implements IExamResultService{
	ExamResultDao erdao = new ExamResultDao();
	
	/**
	 * 生成一个考试信息总表（不含所有成绩）
	 */
	@Override
	public int createResult(String idCard) {
		// TODO Auto-generated method stub
		Date date = new Date();
		erdao.createResult(idCard, date);
		int key = FunctUtils.autoInckey("exam_result");
		return key;
	}
	
	/**
	 * 同理，对之前生成的信息总表进行更新
	 */
	@Override
	public void updateResult(int id,String costTime, int correctAnswers, int wrongAnswers, String flag, int lastScore) {
		// TODO Auto-generated method stub
		ExamResult er = new ExamResult();
		er.setCostTime(costTime);
		er.setCorrectAnswers(correctAnswers);
		er.setWrongAnswers(wrongAnswers);
		er.setFlag(flag);
		er.setLastScore(lastScore);
		er.setId(id);
		erdao.updateResult(er);
	}
}
