package com.sohoki.backoffice.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import com.sohoki.backoffice.sts.tran.service.TransInfoVO;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfo;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;

@Mapper("TelephoneInfoManageMapper")
public interface TelephoneInfoManageMapper {

	    public List<TelephoneInfoVO> selectAgentPageInfoManageListByPagination(TelephoneInfoVO searchVO);
	    
	    public TelephoneInfoVO selectAgentPageInfoManageDetail(String  agentCode );
	    
	    public TelephoneInfoVO selectAgentPageInfoManageDetailSeatId(String  seatId );
	    
	    public int selectAgentPageInfoManageListTotCnt_S(TelephoneInfoVO searchVO);
	    //신규 추가
	    public int selectRestChangeCount();
	    
	    public List<TelephoneInfoVO> selectRestChangeUpdate();
	    //신규 추가 끝 부분
	    
	    public int selectAgentExist (String agentCode);
	    
	    public String selectTelChangeInfo(TelephoneInfoVO vo);
	    
	    public int insertAgentInfoManage(TelephoneInfo vo);
	    
	    public int updateAgentIpMac (TelephoneInfo vo);
	    
	    public int updateAgentChangeNumber(TelephoneInfo vo);
	    
	    public int updateAgentStateChange(TelephoneInfo vo);
	    
	    public int updateAgentPageInfoManage(TelephoneInfo vo);
	    //신규 좌석 변경
		public int updateTelSeatChangeManage(TelephoneInfo vo);
		
	    public int deleteAgentPageInfoManage(String  agentCode);
}
