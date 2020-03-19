package com.sohoki.backoffice.sym.agt.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface UserPhoneInfoManageService {

	List<UserPhoneInfoVO> selectUserPhoneInfoManageListByPagination(UserPhoneInfoVO searchVO) throws Exception;
    
    List<UserPhoneInfoVO> selectUserPhoneInfoManageListByCombo(UserPhoneInfoVO searchVO) throws Exception;
    
    UserPhoneInfoVO selectUsserPhoneInfoDetail(String phoneNumber) throws Exception;
    
    List<UserPhoneInfoVO> selectAgentCombophoneNumber(UserPhoneInfoVO searchVO) throws Exception;
    
    String excelUpload(File excelFile) throws Exception;
    
    int selectUserPhoneInfoManageListByCnt(UserPhoneInfoVO searchVO) throws Exception;
	
    int updateUserPhoneInfo(UserPhoneInfo vo) throws Exception;

    int deleteUserPhoneInfo(String phoneNumber) throws Exception;
}
