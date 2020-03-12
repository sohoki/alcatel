package com.sohoki.backoffice.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfo;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;

@Mapper("TelephoneInfoManageMapper")
public interface TelephoneInfoManageMapper {

	    public List<TelephoneInfoVO> selectAgentPageInfoManageListByPagination(TelephoneInfoVO searchVO);
	    
	    public TelephoneInfoVO selectAgentPageInfoManageDetail(String  agentCode );
	    
	    public int selectAgentPageInfoManageListTotCnt_S(TelephoneInfoVO searchVO);
	    
	    public int selectAgentExist (String agentCode);
	    
	    public int insertAgentInfoManage(TelephoneInfo vo);
	    
	    public int updateAgentIpMac (TelephoneInfo vo);
	    
	    public int updateAgentChangeNumber(TelephoneInfo vo);
	    
	    public int updateAgentStateChange(TelephoneInfo vo);
	    
	    public int updateAgentPageInfoManage(TelephoneInfo vo);
		
	    public int deleteAgentPageInfoManage(String  agentCode);
}
