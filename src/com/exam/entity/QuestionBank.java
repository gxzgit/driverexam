package com.exam.entity;

/**
 * Ã‚ø‚
 * @author Administrator
 *
 */
public class QuestionBank {
	private Integer id;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String rightAns;
	private String rightExplain;
	private String picUrl;
	private String question;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getRightAns() {
		return rightAns;
	}
	public void setRightAns(String rightAns) {
		this.rightAns = rightAns;
	}
	public String getRightExplain() {
		return rightExplain;
	}
	public void setRightExplain(String rightExplain) {
		this.rightExplain = rightExplain;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public QuestionBank() {
		
	}
	@Override
	public String toString() {
		return "QuestionBank [id=" + id + ", optionA=" + optionA + ", optionB=" + optionB + ", optionC=" + optionC
				+ ", optionD=" + optionD + ", rightAns=" + rightAns + ", rightExplain=" + rightExplain + ", picUrl="
				+ picUrl + ", question=" + question + "]";
	}
	
	
}
