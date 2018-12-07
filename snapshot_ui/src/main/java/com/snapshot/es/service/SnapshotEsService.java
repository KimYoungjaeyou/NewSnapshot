package com.snapshot.es.service;

import java.util.Map;

import com.snapshot.es.vo.SnapshotEsVO;

public interface SnapshotEsService {
	
	//요청구분에 따른 총 요청 건수
	public int getGwTotalCount(SnapshotEsVO reqfg);
	
	//요청구분에 따른 총 성공 건수
	public Map<String, Object> getGwTotalSuccessCount(SnapshotEsVO reqfg);
	
	//요청구분에 따른 총 실패 건수
	public Map<String, Object> getGwTotalFailCount(SnapshotEsVO reqfg);
	
	//요청 실패 Case Count
	public Map<String, Object> getGwFailCaseCount(SnapshotEsVO reqfg);
	
	//Snapshot 전체 요청 건수
	public int getSsTotalCount(SnapshotEsVO reqfg);
	
	public int getSsTotalCount(SnapshotEsVO reqfg);
}
