package pedrovictor.bluefood.util;

import java.util.Collection;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;

public class StringUtils {
	
	public static boolean isEmpty(String string) {
		if(string == null) {
			return true;
		}
		
		return string.trim().length() == 0;
	}
	
	public static String encrypt(String senha) {
		
		if(isEmpty(senha)) {
			return null;
		}
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(senha);
	}
	
	
	public static String concatenate(Collection<String> strings) {
		
		if (strings == null || strings.size() == 0) {
			return null;
		}
		
		StringBuilder s = new StringBuilder();
		String delimiter = ", ";
		boolean first = true;
		
		for (String string : strings) {
			if (!first) {
				s.append(delimiter);
			}
			
			s.append(string);
			first = false;
		}
		
		
		return s.toString();
		
		
		
	}

}
