package com.sohoki.backoffice.sym.agt.service.impl;

import java.io.PrintStream;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sohoki.backoffice.alcatel.service.alcatelServiceInfo;
import com.sohoki.backoffice.mapper.ErrorInfoManageMapper;
import com.sohoki.backoffice.sts.error.service.ErrorInfo;


@Service("TelStateResetSchedulerService")
public class TelStateResetSchedulerService {

	private static final Logger logger = Logger.getLogger(TelStateResetSchedulerService.class);
	
	@Resource(name="ErrorInfoManageMapper")
	private ErrorInfoManageMapper errorMapper;
	
	@Resource(name="alcatelServiceInfo")
	private alcatelServiceInfo alcalInfo;
	
	public void telStateResetScheduling() throws Exception{
		try{
			 alcalInfo.telePhoneReset();
		}catch (RuntimeException re) {
		       logger.error("telStateResetScheduling run failed", re);
		       ErrorInfo vo = new ErrorInfo();
		       vo.setErrorType("ERROR_TYPE_1");
			   vo.setErrorMessage("telStateResetScheduling runing error:" + re.toString());
			   errorMapper.insertErrorMessage(vo);
		}catch (Exception e){
			   logger.error("telStateResetScheduling failed", e);
			   ErrorInfo vo = new ErrorInfo();
			   vo.setErrorType("ERROR_TYPE_1");
			   vo.setErrorMessage("telStateResetScheduling error:" + e.toString());
			   errorMapper.insertErrorMessage(vo);
		}
	}
}
