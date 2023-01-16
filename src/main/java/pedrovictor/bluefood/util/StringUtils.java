package pedrovictor.bluefood.util;

//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;

public class StringUtils {
	
	public static boolean isEmpty(String senha) {
		if(senha == null) {
			return true;
		}
		
		return senha.trim().length() == 0;
	}
	
	public static String encrypt(String senha) {
		
		if(isEmpty(senha)) {
			return null;
		}
		
		//PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return senha; 
		//return encoder.encode(senha);
	}

}
