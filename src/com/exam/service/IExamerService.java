package com.exam.service;

import com.exam.entity.Examer;

public interface IExamerService {
	
	public Examer checkIdCard(String pra);
	public void updateCount(int id,int count);
	public void updateLastScore(Examer ex,int score);
}
