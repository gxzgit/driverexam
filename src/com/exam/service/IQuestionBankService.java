package com.exam.service;

import java.util.List;

import com.exam.entity.QuestionBank;

public interface IQuestionBankService {
	
	public List<QuestionBank> getAllQuestion();
	public List<QuestionBank> getRandomQuestion();
	public List<QuestionBank> getChoseQuestion(String idCard);
}
