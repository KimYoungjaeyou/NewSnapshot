package com.snapshot.es.service;

import java.util.List;
import java.util.Map;

import com.snapshot.es.vo.SnapshotEsVO;

public interface SnapshotEsService {
	
//	//요청구분에 따른 총 성공 건수
//	public Map<String, Object> getGwTotalCount(SnapshotEsVO reqfg);
//
	
	//요청구분에 따른 총 성공 건수
	public List<Map<String, Object>> getGwTotalCount(String month, String frdt, String todt);

	//Snapshot 전체 처리 건수 (전체/실패/성공 group by)
	public List<Map<String, Object>> getSsCount(String month, String frdt, String todt);
	
	//Snapshot 삭제종류별건수
	public List<Map<String, Object>> getSsFailCount(String month, String frdt, String todt);
	

}
