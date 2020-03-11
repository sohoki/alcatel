package com.sohoki.backoffice.sym.agt.service;

import java.util.List;

public interface UserPhoneInfoManageService {

	List<UserPhoneInfoVO> selectUserPhoneInfoManageListByPagination(UserPhoneInfoVO searchVO) throws Exception;
    
    List<UserPhoneInfoVO> selectUserPhoneInfoManageListByCombo(UserPhoneInfoVO searchVO) throws Exception;
    
    UserPhoneInfoVO selectUsserPhoneInfoDetail(String phoneNumber) throws Exception;
    
    int selectUserPhoneInfoManageListByCnt(UserPhoneInfoVO searchVO) throws Exception;
	
    int updateUserPhoneInfo(UserPhoneInfo vo) throws Exception;

    int deleteUserPhoneInfo(String phoneNumber) throws Exception;
}
