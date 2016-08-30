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
	int size;	//��Ŀ����

	/**
	 * ��ȡ��������е���Ŀ
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
	 * ������������list
	 */
	@Override
	public List<QuestionBank> getAllQuestion() {
		// TODO Auto-generated method stub
		return qbdao.getAllQuestion();
	}

	/**
	 * ����������
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
	 * ȡ����ǰһ�εĿ��Դ�����Ŀ��������������ж�
	 * ���������ȡ��Ӧ�������⣨��ʵ�в����ܳ��֣�
	 * ������ȫ��ȡ��
	 * С������ȫ��ȡ�����ڴ������ѡȡ���¸�������Ŀ
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
				 * ����-1��Ϊ�˽��wrongQuestion.get(i)�ǻ������Ŀ��id������
				 * �����Ǵ�0��ʼ�������0λ�ö�Ӧ������Ŀ��1λ�ã��������
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