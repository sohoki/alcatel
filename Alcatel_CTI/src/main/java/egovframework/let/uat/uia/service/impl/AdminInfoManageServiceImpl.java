package egovframework.let.uat.uia.service.impl;

import java.util.List;

import egovframework.com.mapper.AdminInfoManagerMapper;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.let.uat.uia.service.AdminInfo;
import egovframework.let.uat.uia.service.AdminInfoManageService;
import egovframework.let.uat.uia.service.AdminInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;



@Service("AdminInfoManageService")
public class AdminInfoManageServiceImpl extends EgovAbstractServiceImpl implements  AdminInfoManageService {

	
	@Resource(name="AdminInfoManagerMapper")
	private AdminInfoManagerMapper adminMapper;
	
	@Override
	public int deleteAdminUserManage(String mberId) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.deleteAdminUserManage(mberId);
	}

	
	@Override
	public int updateAdminUserManage(AdminInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			ret = adminMapper.insertAdminUserManage(vo);
		}else {
			ret = adminMapper.updateAdminUserManage(vo);
		}
		return ret;
	}

	@Override
	public AdminInfoVO selectAdminUserManageDetail(AdminInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.selectAdminUserManageDetail(vo);
	}

	@Override
	public List<?> selectAdminUserManageListByPagination(AdminInfoVO searchVO)throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.selectAdminUserManageListByPagination(searchVO);
	}

	@Override
	public int selectAdminUserManageListTotCnt_S(AdminInfoVO searchVO)throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.selectAdminUserManageListTotCnt_S(searchVO);
	}

	@Override
	public int selectAdminUserMangerIDCheck(String code) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.selectAdminUserMangerIDCheck(code);
	}

	@Override
	public int updateAdminUserLockManage(String adminId) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.updateAdminUserLockManage(adminId);
	}


	@Override
	public int updatePassChange(AdminInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.updatePassChange(vo);
	}
	
}
