package com.exam.entity;

/**
 * 考生成绩详情表
 * @author Administrator
 *
 */
public class ExamResDetail {
	private Integer id;
	private Integer resultId;

	public Integer getQuesId() {
		return quesId;
	}

	public void setQuesId(Integer quesId) {
		this.quesId = quesId;
	}

	private Integer quesId;
	private String flag;
	private String choseAns;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getChoseAns() {
		return choseAns;
	}

	public void setChoseAns(String choseAns) {
		this.choseAns = choseAns;
	}

	@Override
	public String toString() {
		return "ExamResDetail [id=" + id + ", resultId=" + resultId + ", quesId=" + quesId + ", flag=" + flag
				+ ", choseAns=" + choseAns + "]";
	}

}
