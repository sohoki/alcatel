package com.sohoki.backoffice.sts.cnt.service;

import java.util.List;

import com.sohoki.backoffice.sym.cnt.service.CenterInfo;

public interface ContentFileInfoManageService {

	
	   List<ContentFileInfoVO> selectFilePageListByPagination(ContentFileInfoVO searchVO) throws Exception;
		
	   List<ContentFileInfo> fileDelInfo(List report_Seq) throws Exception;
	   
	   int selectFilePageListByPaginationTotCnt_S (ContentFileInfoVO searchVO)throws Exception;
		
	   int  selectFileListTotCnt_S (String conSeq) throws Exception;
		
		ContentFileInfoVO selectFileDetail   (String atchFileId) throws Exception;

		int insertFileManage (ContentFileInfo vo) throws Exception;
		
		int updateFileManage (ContentFileInfo vo) throws Exception;
		
		int updateFileDetailInfo(ContentFileInfo vo) throws Exception;
		
		int updateFileManageUseYn(ContentFileInfo vo) throws Exception;
		
		int deleteFileManage (String atchFileId) throws Exception;
}
