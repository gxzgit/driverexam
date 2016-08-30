package com.exam.service;

public interface IExamResultService {
	public int createResult(String idCard);
	public void updateResult(int id,String costTime,int correctAnswers,int wrongAnswers,String flag,int lastScore) ;
}

