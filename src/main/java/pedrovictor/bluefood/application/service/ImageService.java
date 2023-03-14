package pedrovictor.bluefood.application.service;

import pedrovictor.bluefood.util.IOUtils;
import pedrovictor.bluefood.util.ImageType;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	
	@Value("${bluefood.files.logotipo}")
	private String logotiposDir;
	
	@Value("${bluefood.files.categoria}")
	private String categoriasDir;
	
	@Value("${bluefood.files.comida}")
	private String comidasDir;

	public void uploadLogotipo(MultipartFile file, String fileName) {
		try {
			IOUtils.copy(file.getInputStream(), fileName, logotiposDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
	
	public void uploadComida(MultipartFile file, String fileName) {
		try {
			IOUtils.copy(file.getInputStream(), fileName, comidasDir);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
	
	public byte[] getBytes(String type, String imgName) {
		
		
		try {
			String dir;

			if (type.equalsIgnoreCase(ImageType.COMIDA.toString())) {
				dir = comidasDir;
			} else if (type.equalsIgnoreCase(ImageType.LOGOTIPO.toString())) {
				dir = logotiposDir;
			} else if (type.equalsIgnoreCase(ImageType.CATEGORIA.toString())) {
				dir = categoriasDir;
			} else {
				throw new Exception(type + " não é um tipo válido");
			}

			return IOUtils.getBytes(Paths.get(dir, imgName));

		} catch (Exception e) {
			throw new ApplicationServiceException(e);
		}
			
		
	}
	
	
	
	
	
}
