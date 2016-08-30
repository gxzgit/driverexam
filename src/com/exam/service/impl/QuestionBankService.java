package com.exam.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.exam.dao.ExamResDetailDao;
import com.exam.dao.QuestionBankDao;
import com.exam.entity.QuestionBank;
import com.exam.service.IQuestionBankService;
import com.exam.utils.FunctUtils;

public class QuestionBankService implements IQuestionBankService {
	QuestionBankDao qbdao = new QuestionBankDao();
	ExamResDetailDao erddao = new ExamResDetailDao();
	int size;	//题目数量

	/**
	 * 获取题库中所有的题目
	 */
	public QuestionBankService() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("examinit.properties");
		Properties prop = new Properties();
		try {
			prop.load(inputStream);
			size = Integer.valueOf(prop.getProperty("question"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获得所有问题的list
	 */
	@Override
	public List<QuestionBank> getAllQuestion() {
		// TODO Auto-generated method stub
		return qbdao.getAllQuestion();
	}

	/**
	 * 随机获得问题
	 */
	public List<QuestionBank> getRandomQuestion() {
		List<QuestionBank> returnQues = new ArrayList<QuestionBank>();
		List<QuestionBank> allQuestion = qbdao.getAllQuestion();
		int[] randomNumber = FunctUtils.randomCommon(0, allQuestion.size(), size);
		for (int i = 0; i < randomNumber.length; i++) {
			returnQues.add(allQuestion.get(randomNumber[i]));
		}
		return returnQues;
	}

	/**
	 * 取考生前一次的考试错的题目，对其个数进行判断
	 * 大于则从中取对应数量的题（现实中不可能出现）
	 * 等于则全部取出
	 * 小于则是全部取出后，在从题库中选取余下个数的题目
	 */
	@Override
	public List<QuestionBank> getChoseQuestion(String idCard) {
		// TODO Auto-generated method stub
		List<QuestionBank> returnQues = new ArrayList<QuestionBank>();
		List<Integer> wrongQuestion = erddao.getWrongQuestion(idCard);

		List<QuestionBank> temp = this.getAllQuestion();
		if (wrongQuestion.size() == 0) {
			returnQues = getRandomQuestion();
		} else if (wrongQuestion.size() >= size) {
			for (int i = 0; i < size; i++) {
				returnQues.add(temp.get(wrongQuestion.get(i)));
			}
		} else {
			for (int i = 0; i < wrongQuestion.size(); i++) {
				/**
				 * 下面-1是为了解决wrongQuestion.get(i)是获得了题目的id，但是
				 * 数组是从0开始，数组的0位置对应的是题目的1位置，如此类推
				 */
				System.out.print(wrongQuestion.get(i) + "  ||  ");
				returnQues.add(temp.get(wrongQuestion.get(i) - 1));

			}
			int[] randomNumber = FunctUtils.randomCommon(0, temp.size(), (size - returnQues.size()));
			for (int k = 0; k < randomNumber.length;) {
				System.out.print(temp.get(randomNumber[k]).getId() + "  ||  ");
				if (!returnQues.contains(temp.get(randomNumber[k]))) {
					returnQues.add(temp.get(randomNumber[k]));
					k++;
				}
			}
		}
		return returnQues;
	}

}
