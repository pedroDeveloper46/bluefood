package pedrovictor.bluefood.infrastructure.web.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import pedrovictor.bluefood.application.service.ImageService;

@Controller
public class ImageController {
	
	@Autowired
	private ImageService imageService;
	
	@GetMapping(path="/images/{type}/{imgName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE} )
	@ResponseBody
	public byte[] getBytes(@PathVariable String type, 
			@PathVariable String imgName) {
		
		return imageService.getBytes(type, imgName);
		
	}

}
