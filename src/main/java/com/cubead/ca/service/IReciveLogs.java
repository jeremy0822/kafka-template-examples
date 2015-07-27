package com.cubead.ca.service;

import com.cubead.ca.vo.RequestData;



public interface IReciveLogs {
	public void writeLog(RequestData request);
	public void writeKafka(RequestData request);
}
