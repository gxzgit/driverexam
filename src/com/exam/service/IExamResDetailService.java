package com.exam.service;

import com.exam.entity.ExamResDetail;

public interface IExamResDetailService {
	public void inserExamResDetail(ExamResDetail erd);
	public void inserExamResDetail(int resultId,int quesId,String flag,String choseAns);
}
