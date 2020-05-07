package com.sohoki.backoffice.sts.error.service;

import java.util.List;


public interface ErrorInfoManageService {
	
	List<ErrorInfoVO> selectErrorMessage(ErrorInfoVO searchVO) throws Exception;
	
	int selectErrorMessageCnt(ErrorInfoVO searchVO)throws Exception;
	
	int insertErrorMessage(String errorMessage)throws Exception;
	
	int deleteErrorMessage(String errorSeq)throws Exception;
	

	
}
