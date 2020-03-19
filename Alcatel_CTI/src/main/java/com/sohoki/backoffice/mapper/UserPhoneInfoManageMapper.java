package com.sohoki.backoffice.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfo;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoVO;

@Mapper("UserPhoneInfoManageMapper")
public interface UserPhoneInfoManageMapper {

	
	    public List<UserPhoneInfoVO> selectUserPhoneInfoManageListByPagination(UserPhoneInfoVO searchVO);
	    
	    public List<UserPhoneInfoVO> selectUserPhoneInfoManageListByCombo(UserPhoneInfoVO searchVO);
	    
	    public List<UserPhoneInfoVO>  selectAgentCombophoneNumber(UserPhoneInfoVO searchVO);
	    
	    public UserPhoneInfoVO selectUsserPhoneInfoDetail(String phoneNumber);
	    
	    public int selectUserNumberIdCheck( UserPhoneInfo vo);
	    
	    public int selectUserPhoneInfoManageListByCnt(UserPhoneInfoVO searchVO);
		
	    public int insertUserPhoneInfo(UserPhoneInfo vo);
	    
	    public int updateUserPhoneInfo(UserPhoneInfo vo);

	    public int deleteUserPhoneInfo(String phoneNumber);
	    
	    
}
