package com.sohoki.backoffice.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import com.sohoki.backoffice.sts.cnt.service.ContentFileInfo;
import com.sohoki.backoffice.sts.cnt.service.ContentFileInfoVO;

@Mapper("ContentFileManagerMapper")
public interface ContentFileManagerMapper {
	
	public List<ContentFileInfoVO> selectFilePageListByPagination (ContentFileInfoVO  searchVO);
	
	public int selectFilePageListByPaginationTotCnt_S (ContentFileInfoVO  searchVO);
	
	public List<ContentFileInfo> fileDelInfo(List reportSeq);
	
	public int selectFileListTotCnt_S  (String conSeq);
	
	public ContentFileInfoVO selectFileDetail (String atchFileId);
	
	public int insertFileManage(ContentFileInfo vo);
	
	public int updateFileManage(ContentFileInfo vo);
	
	public int updateFileManageUseYn(ContentFileInfo vo);
	
	public int updateFileDetailInfo(ContentFileInfo vo);
		
	public int deleteFileManage(String atchFileId);
}
