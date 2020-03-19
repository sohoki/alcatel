package com.sohoki.backoffice.sts.tran.service;

import java.util.List;

public interface TransInfoManageService {

	
	List<TransInfoVO> selectTranInfoManageListByPagination (TransInfoVO searchVO) throws Exception;
	
	TransInfoVO  selectTranInfoManageDetail(String tranSeq)throws Exception;
	
	int selectTranProcessCount(String tranProcessName)throws Exception;
	
	int selectTrannfoManageListTotCnt_S(TransInfoVO searchVO)throws Exception;
	
	int updateTraninfoManage(TransInfo VO)throws Exception;
}
