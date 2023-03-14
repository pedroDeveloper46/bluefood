package pedrovictor.bluefood.infrastructure.web.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import pedrovictor.bluefood.util.FileType;

public class UploadValidator  implements ConstraintValidator<UploadConstraint, MultipartFile> {

	private List<FileType> acceptedFileTypes;
	
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		if(multipartFile == null) {
			return true;
		}
		
		for (FileType fileType : acceptedFileTypes) {
			if(fileType.sameOf(multipartFile.getContentType())) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void initialize(UploadConstraint constraintAnnotation) {
		// TODO Auto-generated method stub
		acceptedFileTypes = Arrays.asList(constraintAnnotation.acceptedTypes());
	}
	
	
	

}
