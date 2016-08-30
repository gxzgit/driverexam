package com.exam.entity;

import java.util.Date;

/**
 * øº ‘≥…º®∏≈¿¿
 * @author Administrator
 *
 */
public class ExamResult {
	private Integer id;
	private String idCard ;
	private Date examDate;
	private String costTime;
	private Integer correctAnswers;
	private Integer wrongAnswers;
	private String flag;
	private Integer lastScore;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public String getCostTime() {
		return costTime;
	}
	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}
	public Integer getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(Integer correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	public Integer getWrongAnswers() {
		return wrongAnswers;
	}
	public void setWrongAnswers(Integer wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getLastScore() {
		return lastScore;
	}
	public void setLastScore(Integer lastScore) {
		this.lastScore = lastScore;
	}
	public ExamResult() {
		
	}
	@Override
	public String toString() {
		return "UserResult [id=" + id + ", idCard=" + idCard + ", examDate=" + examDate + ", costTime=" + costTime
				+ ", correctAnswers=" + correctAnswers + ", wrongAnswers=" + wrongAnswers + ", flag=" + flag
				+ ", lastScore=" + lastScore + "]";
	}
	
}
