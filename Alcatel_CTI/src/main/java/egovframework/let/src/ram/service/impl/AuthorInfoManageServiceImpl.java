package egovframework.let.src.ram.service.impl;

import java.util.List;

import egovframework.let.src.ram.service.AuthorInfo;
import egovframework.let.src.ram.service.AuthorInfoManageService;
import egovframework.com.mapper.AuthorInfoManagerMapper;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("AuthorInfoManageService")
public class AuthorInfoManageServiceImpl extends EgovAbstractServiceImpl implements AuthorInfoManageService {

	
	@Resource(name="AuthorInfoManagerMapper")
	private AuthorInfoManagerMapper authMapper;
	
	@Override
	public List<AuthorInfo> selectAuthorIInfoManageCombo() {
		// TODO Auto-generated method stub
		return authMapper.selectAuthorIInfoManageCombo();
	}
	

}
