package com.sohoki.backoffice.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfo;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;

@Mapper("TelephoneInfoManageMapper")
public interface TelephoneInfoManageMapper {

	    public List<TelephoneInfoVO> selectAgentPageInfoManageListByPagination(TelephoneInfoVO searchVO);
		//지점에서  단말기 상태 보기 
	    public List<TelephoneInfoVO> selectAgentNowStateInnfo(String  centerId);
	    
	    public List<TelephoneInfoVO> selectAgentCenterPageList(TelephoneInfoVO searchVO);
	    
	    public TelephoneInfoVO selectAgentPageInfoManageDetail(String  agentCode );
		
	    public TelephoneInfoVO selectAgentPageInfoManageView(TelephoneInfoVO searchVO);
	    
	    public TelephoneInfoVO selectAgentUrlCheck(TelephoneInfo searchVO);
		
	    public int selectAgentPageInfoManageListTotCnt_S(TelephoneInfoVO searchVO);
	    
	    public String selectDisplayCheck(TelephoneInfo vo);
	    
	    public int selectAgentExist (String agentCode);
		
	    public int insertAgentPageInfoManage(TelephoneInfo vo);
		
	    public int updateAgentPageInfoManage(TelephoneInfo vo);
	    
	    public int updateAgentState();
	    
	    public int updateAgentUrlRec(TelephoneInfo vo);
	    
	    public int updateAgentIpMac (TelephoneInfo vo);
	    
	    public int updateDisplayUpdate(TelephoneInfo vo);
	    
	    public int updateAgentStateUpdate(String displaySeq);
		
	    public int deleteAgentPageInfoManage(String  agentCode);
}
