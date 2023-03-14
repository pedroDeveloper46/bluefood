package pedrovictor.bluefood.infrastructure.web.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pedrovictor.bluefood.util.StringUtils;

@Component
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String source) {
		// TODO Auto-generated method stub
		
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		
		source = source.replace(",", ".").trim();
		
		return BigDecimal.valueOf(Double.valueOf(source));
		
	}

}
