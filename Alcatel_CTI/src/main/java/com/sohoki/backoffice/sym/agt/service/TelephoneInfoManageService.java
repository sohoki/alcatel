package com.sohoki.backoffice.sym.agt.service;

import java.util.List;


public interface TelephoneInfoManageService {

	
	List<TelephoneInfoVO> selectAgentPageInfoManageListByPagination(TelephoneInfoVO searchVO) throws Exception;
	
	List<TelephoneInfoVO> selectAgentNowStateInnfo(String  centerId) throws Exception;
	
	List<TelephoneInfoVO> selectAgentCenterPageList(TelephoneInfoVO searchVO) throws Exception;
	
    TelephoneInfoVO selectAgentPageInfoManageDetail(String  agentCode )throws Exception;
	
    TelephoneInfoVO selectAgentPageInfoManageView(TelephoneInfoVO searchVO) throws Exception;
    
    TelephoneInfoVO selectAgentUrlCheck(TelephoneInfo searchVO)throws Exception;
    
    int selectAgentExist (String agentCode) throws Exception;
	
    String selectDisplayCheck(TelephoneInfo vo) throws Exception;
    
    int selectAgentPageInfoManageListTotCnt_S(TelephoneInfoVO searchVO) throws Exception;
    
    int updateAgentState()throws Exception;
	
    int updateAgentPageInfoManage(TelephoneInfo vo) throws Exception;
    //url 업데이트
    int updateAgentUrlRec(TelephoneInfo vo)throws Exception;
    
    int updateAgentIpMac (TelephoneInfo vo) throws Exception;
    
    int updateAgentStateUpdate(String displaySeq) throws Exception;
    
    int updateDisplayUpdate(TelephoneInfo vo)throws Exception;
	
    int deleteAgentPageInfoManage(String  agentCode) throws Exception;
}
