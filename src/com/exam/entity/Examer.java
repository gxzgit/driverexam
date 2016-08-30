package com.exam.entity;

/**
 * øº…˙¿‡
 * @author Administrator
 *
 */
public class Examer {
	private Integer id;
	private String idCard;
	private String name;
	private Integer count;
	private Integer lastScore;
	public Integer getLastScore() {
		return lastScore;
	}
	public void setLastScore(Integer lastScore) {
		this.lastScore = lastScore;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Examer() {
		
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", idCard=" + idCard + ", name=" + name + ", count=" + count + "]";
	}
	
}
