package com.sohoki.backoffice.sym.agt.service;

import java.util.List;

import com.sohoki.backoffice.sts.tran.service.TransInfoVO;


public interface TelephoneInfoManageService {

	
	List<TelephoneInfoVO> selectAgentPageInfoManageListByPagination(TelephoneInfoVO searchVO) throws Exception;
    
    TelephoneInfoVO selectAgentPageInfoManageDetail(String  agentCode ) throws Exception;
    
    String agentReset(String  agentReset ) throws Exception;
    
    String agentTelChange(TelephoneInfo  vo ) throws Exception;
    
    String agentResetAll( ) throws Exception;
    
    String agentResetBasicUpdate( ) throws Exception;
    
    int selectAgentPageInfoManageListTotCnt_S(TelephoneInfoVO searchVO) throws Exception;
    
    int selectAgentExist (String agentCode) throws Exception;
    
    int updateAgentIpMac (TelephoneInfo vo) throws Exception;
    
    int updateAgentChangeNumber(TelephoneInfo vo) throws Exception;
    
    int updateAgentStateChange(TelephoneInfo vo) throws Exception;
    
    int updateAgentPageInfoManage(TelephoneInfo vo) throws Exception;
	
    int deleteAgentPageInfoManage(String  agentCode) throws Exception;
	//좌석 상태 변경
	int updateTelSeatChangeManage(String seatInfo, String seatGubun)throws Exception;
}
