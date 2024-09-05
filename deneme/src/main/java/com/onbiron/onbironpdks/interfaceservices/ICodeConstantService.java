package com.onbiron.onbironpdks.interfaceservices;

import java.util.List;

import org.springframework.data.domain.Page;

import com.onbiron.onbironpdks.entities.CodeConstant;
 

public interface ICodeConstantService {

	public List<CodeConstant> getAllCodeConstantS();
	
	public CodeConstant createCodeConstantS(CodeConstant codeConstant);
	
	public CodeConstant getByIdCodeConstantS(Long id);
	
	public CodeConstant updateByIdCodeConstantS(Long id, CodeConstant codeConstant);
	
	public CodeConstant deleteCodeConstantS(CodeConstant codeConstant);
	
	public List<CodeConstant> getByParentIdCodeConstant(Long parentId);
	
	public Page<CodeConstant> getAllCodeConstantPageS(int page, int rowsPerPage );
	
	 public List<CodeConstant> search(String searchTerm);
	
	
}
