package com.sohoki.backoffice.sts.cnt.service.impl;



import com.sohoki.backoffice.sts.cnt.service.ContentFileInfo;
import com.sohoki.backoffice.sts.cnt.service.ContentFileInfoVO;
import com.sohoki.backoffice.sts.cnt.service.ContentFileInfoManageService;
import com.sohoki.backoffice.sym.cnt.service.CenterInfo;

import java.io.File;
import java.util.List;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohoki.backoffice.mapper.ContentFileManagerMapper;


@Service("ContentFileInfoManageService")
public class ContentFileInfoManageServiceImpl extends EgovAbstractServiceImpl implements ContentFileInfoManageService {

	@Resource(name="ContentFileManagerMapper")
	private ContentFileManagerMapper conFileManager;

	
	
	@Override
	public List<ContentFileInfoVO> selectFilePageListByPagination(
			ContentFileInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.selectFilePageListByPagination(searchVO);
	}

	@Override
	public int selectFilePageListByPaginationTotCnt_S(ContentFileInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.selectFilePageListByPaginationTotCnt_S(searchVO);
	}

	@Override
	public int selectFileListTotCnt_S(String conSeq) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.selectFileListTotCnt_S(conSeq);
	}

	@Override
	public ContentFileInfoVO selectFileDetail(String atchFileId)
			throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.selectFileDetail(atchFileId);
	}

	@Override
	public int insertFileManage(ContentFileInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.insertFileManage(vo);
	}

	@Override
	public int updateFileManage(ContentFileInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.updateFileManage(vo);
	}

	@Override
	public int updateFileManageUseYn(ContentFileInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.updateFileManageUseYn(vo);
	}

	@Override
	public int deleteFileManage(String atchFileId) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.deleteFileManage(atchFileId);
	}

	@Override
	public List<ContentFileInfo> fileDelInfo(List reportSeq) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.fileDelInfo(reportSeq);
	}

	@Override
	public int updateFileDetailInfo(ContentFileInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.updateFileDetailInfo(vo);
	}
	
}
