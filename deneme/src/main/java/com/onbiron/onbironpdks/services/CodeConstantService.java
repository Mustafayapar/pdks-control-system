package com.onbiron.onbironpdks.services;

import java.util.List;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onbiron.onbironpdks.entities.CodeConstant;
import com.onbiron.onbironpdks.entities.UserRole;
import com.onbiron.onbironpdks.enums.ParentIdType;
import com.onbiron.onbironpdks.interfaceservices.ICodeConstantService;
import com.onbiron.onbironpdks.repositories.ICodeConstantRepository;

@Service
public class CodeConstantService implements ICodeConstantService {
	
	private final ICodeConstantRepository codeConstantRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserAnnualReportInfoService.class);


	public CodeConstantService(ICodeConstantRepository codeConstantRepository) {
	
		this.codeConstantRepository = codeConstantRepository;
	}

	@Override
	public List<CodeConstant> getAllCodeConstantS() {
		
		try {
	        // Retrieve all CodeConstant records from the repository
	        List<CodeConstant> codeConstants = codeConstantRepository.findAllByIsDeleted(false);

	        // Log the number of records retrieved
	        if (!codeConstants.isEmpty()) {
	            logger.info("Retrieved {} CodeConstant records.", codeConstants.size());
	        } else {
	            logger.warn("No CodeConstant records found.");
	        }

	        return codeConstants;
	    } catch (Exception e) {
	        logger.error("Error retrieving CodeConstant records: {}", e.getMessage());
	        throw new RuntimeException("Failed to retrieve CodeConstant records.", e);
	    }	}

	@Override
	public CodeConstant createCodeConstantS(CodeConstant codeConstant) {
		 try {
		        // Validate the input codeConstant object (you can add specific validation logic here)
		        if (codeConstant == null) {
		            logger.error("Attempted to create a null CodeConstant.");
		            throw new IllegalArgumentException("CodeConstant cannot be null.");
		        }

		        // Save the codeConstant entity to the repository
		        CodeConstant savedCodeConstant = codeConstantRepository.save(codeConstant);
		        logger.info("Successfully created CodeConstant with ID {}", savedCodeConstant.getId());

		        return savedCodeConstant;
		    } catch (Exception e) {
		        logger.error("Error creating CodeConstant: {}", e.getMessage());
		        throw new RuntimeException("Failed to create CodeConstant.", e);
		    }	}

	@Override
	public CodeConstant getByIdCodeConstantS(Long id) {
		
		  try {
		        // Attempt to find the CodeConstant by its ID
		        return codeConstantRepository.findById(id).orElse(null);
		    } catch (Exception e) {
		        // Log any exception that occurs during the retrieval process
		        logger.error("Error retrieving CodeConstant by ID {}: {}", id, e.getMessage());
		        throw new RuntimeException("Failed to retrieve CodeConstant.", e);
		    }	}

	@Override
	public CodeConstant updateByIdCodeConstantS(Long id, CodeConstant newCodeConstant) {
		
		 Optional<CodeConstant> codeConstantOptional = codeConstantRepository.findById(id);

	        if (codeConstantOptional.isPresent()) {
	           
	            CodeConstant codeConstant = codeConstantOptional.get();
	            codeConstant.setName(newCodeConstant.getName());
	            codeConstant.setExplanation(newCodeConstant.getExplanation());
	            codeConstant.setIsDeleted(newCodeConstant.getIsDeleted());
	            
	            return codeConstantRepository.save(codeConstant);
	        } else {
	            return null;
	        }
	}

	@Override
	public CodeConstant deleteCodeConstantS(CodeConstant codeConstant) {
		 try {
		        // Check if the CodeConstant record exists by its ID
		        Optional<CodeConstant> existingCodeConstant = codeConstantRepository.findById(codeConstant.getId());

		        if (existingCodeConstant.isPresent()) {
		            // Get the existing record
		            CodeConstant codeConstantToDelete = existingCodeConstant.get();

		            // Mark the record as deleted by setting the isDeleted flag to true
		            codeConstantToDelete.setIsDeleted(true);

		            // Save the updated record back to the database
		            CodeConstant deletedCodeConstant = codeConstantRepository.save(codeConstantToDelete);
		            logger.info("CodeConstant with ID {} marked as deleted.", deletedCodeConstant.getId());

		            return deletedCodeConstant;
		        } else {
		            logger.warn("CodeConstant with ID {} not found.", codeConstant.getId());
		            throw new RuntimeException("CodeConstant record not found.");
		        }
		    } catch (Exception e) {
		        logger.error("Error deleting CodeConstant record with ID {}: {}", codeConstant.getId(), e.getMessage());
		        throw new RuntimeException("Failed to delete CodeConstant record.", e);
		    }
	}

	@Override
	public List<CodeConstant> getByParentIdCodeConstant(long parentId) {
		 try {
		        // Retrieve the CodeConstant records by parentId
		       List<CodeConstant> codeConstants = codeConstantRepository.findByParentIdAndIsDeleted( parentId,false);

		        if (codeConstants != null) {
		            logger.info("Returning CodeConstants for Parent ID {}", parentId);
		            return codeConstants;
		        } else {
		            logger.warn("No CodeConstants found for Parent ID {}", parentId);
		            throw new RuntimeException("No CodeConstants found for the given Parent ID.");
		        }
		    } catch (Exception e) {
		        logger.error("Error retrieving CodeConstants for Parent ID {}: {}", parentId, e.getMessage());
		        throw new RuntimeException("Failed to retrieve CodeConstants.", e);
		    }
	}

	@Override
	public Page<CodeConstant> getAllCodeConstantPageS(int page, int rowsPerPage) {

		try {
			Pageable pageable = PageRequest.of(page, rowsPerPage);
	        Page<CodeConstant> codeConstant =  codeConstantRepository.findAllByIsDeleted(false, pageable);
	        if (codeConstant.isEmpty()) {
	            logger.warn("No users found in the database.");
	            throw new RuntimeException("No users found.");
	        }
	        return codeConstant;
	    } catch (Exception e) {
	        logger.error("An error occurred while fetching all users: {}", e.getMessage());
	        throw new RuntimeException("An error occurred while fetching users.", e);
	        }
	}

	@Override
	public List<CodeConstant> search(String searchTerm) {
		String cleanedSearchTerm = searchTerm.trim();
        return codeConstantRepository.searchByTerm( cleanedSearchTerm);	

	}

	@Override
	public List<CodeConstant> getByParentIdsCodeConstant(List<Long> parentIds) {
		  
        return codeConstantRepository.findByParentIdsAndIsDeleted(parentIds,false);
	}




	
}
