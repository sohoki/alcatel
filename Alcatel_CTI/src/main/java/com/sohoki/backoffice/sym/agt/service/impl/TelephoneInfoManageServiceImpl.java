package com.sohoki.backoffice.sym.agt.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sohoki.backoffice.sym.agt.service.TelephoneInfo;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoManageService;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

import com.sohoki.backoffice.alcatel.service.alcatelServiceInfo;
import com.sohoki.backoffice.mapper.TelephoneInfoManageMapper;

@Service("TelephoneInfoManageService")
public class TelephoneInfoManageServiceImpl extends EgovAbstractServiceImpl implements TelephoneInfoManageService{

	
	@Resource(name="TelephoneInfoManageMapper")
	private TelephoneInfoManageMapper agentMapper;

	
    @Resource(name = "egovAgentIdGnrService")
    private EgovIdGnrService idgenService;

    
    @Autowired
    private alcatelServiceInfo  alcatel;
    
	@Override
	public List<TelephoneInfoVO> selectAgentPageInfoManageListByPagination(TelephoneInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageListByPagination(searchVO);
	}

	@Override
	public TelephoneInfoVO selectAgentPageInfoManageDetail(String agentCode)
			throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageDetail(agentCode);
	}

	
	@Override
	public int selectAgentExist(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentExist(agentCode);
	}

	@Override
	public int selectAgentPageInfoManageListTotCnt_S(TelephoneInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageListTotCnt_S(searchVO);
	}

	@Override
	public int updateAgentPageInfoManage(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			vo.setAgentCode(idgenService.getNextStringId());
			ret = agentMapper.insertAgentInfoManage(vo);
		}else {
			ret = agentMapper.updateAgentPageInfoManage(vo);
		}
		return ret;
	}

	@Override
	public int updateAgentIpMac(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentIpMac(vo);
	}


	@Override
	public int deleteAgentPageInfoManage(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.deleteAgentPageInfoManage(agentCode);
	}

	@Override
	public int updateAgentChangeNumber(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentChangeNumber(vo);
	}

	@Override
	public int updateAgentStateChange(TelephoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentStateChange(vo);
	}
    //초기화
	@Override
	public String agentReset(String agentReset) throws Exception {
		// TODO Auto-generated method stub
		String[] agentCodes = agentReset.split(",");
		String result = "";
		TelephoneInfoVO info = new TelephoneInfoVO();
		for(String agentCode : agentCodes){
			
			info = agentMapper.selectAgentPageInfoManageDetail(agentCode);
			if ( info.getAgentState().equals("PHONE_STATE_2") && !info.getAgentNownumber().equals("")){
				if (!alcatel.telelPhoneChange(info.getAgentNownumber(), info.getSeatId(), "OT").equals("OK")){
					result = result + "," + info.getAgentNm();
				}
			}
		}
		return result;
	}

	@Override
	public String agentTelChange(TelephoneInfo  vo) throws Exception {
		// TODO Auto-generated method stub
		String result = "FALSE";
		try{
			TelephoneInfoVO info = agentMapper.selectAgentPageInfoManageDetail(vo.getAgentCode());
			if ( info.getAgentState().equals("PHONE_STATE_1") && info.getAgentNownumber().toString().length() < 2   ){
				result = alcatel.telelPhoneChange( vo.getAgentNownumber(),  info.getSeatId(), "IN");
			}else {
				result = "FALSE";
			}
		}catch(Exception e){
			System.out.println("agentTelChange ERROR:" + e.toString());
		}
		
		return result;
	}

	@Override
	public String agentResetAll() throws Exception {
		// TODO Auto-generated method stub
		String result = "FALSE";
		try{
			result = alcatel.telePhoneReset();
			result = "OK";
		}catch(Exception e){
			System.out.println("agentTelChange ERROR:" + e.toString());
		}
		
		return result;
	}

	@Override
	public int updateTelSeatChangeManage(String seatInfo, String seatGubun) throws Exception {
		// TODO Auto-generated method stub
		TelephoneInfoVO info = new TelephoneInfoVO();
		info.setTelChange(seatGubun);
		info.setSeatChangeInfo(Arrays.asList(seatInfo.split("\\s*,\\s*")) ); 
		
		return agentMapper.updateTelSeatChangeManage(info);
	}

	@Override
	public String agentResetBasicUpdate() throws Exception {
		String result = "FALSE";
		try{
			System.out.println("agentResetBasicUpdate-----------------------------------------------------------------------------------------------------------------------");
			result = alcatel.telePhoneBasicUpdate();
			result = "OK";
		}catch(Exception e){
			System.out.println("agentTelChange ERROR:" + e.toString());
		}
		
		return result;
	}

	@Override
	public int agentBackup() throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.insertTelBackUp();
	}
}
