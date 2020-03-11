package com.sohoki.backoffice.mapper;

import java.util.List;

import com.sohoki.backoffice.sts.error.service.ErrorInfo;
import com.sohoki.backoffice.sts.error.service.ErrorInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ErrorInfoManageMapper")
public interface ErrorInfoManageMapper {
	
    public List<ErrorInfoVO> selectErrorMessage(ErrorInfoVO searchVO);
    
    public int selectErrorMessageCnt(ErrorInfoVO searchVO);
	
    public int insertErrorMessage(ErrorInfo vo);

    public int deleteErrorMessage(String errorSeq);

}
