package pedrovictor.bluefood.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
	
	private static final Locale LOCAL_BRAZIL = Locale.of("pt", "BR");
	
	public static NumberFormat newCurrencyFormat() {
		NumberFormat n = NumberFormat.getNumberInstance(LOCAL_BRAZIL);
		
		n.setMaximumFractionDigits(2);
		n.setMinimumFractionDigits(2);
		n.setGroupingUsed(false);
		
		return n;
	}
	
	
	public static String formatCurrency(BigDecimal bigDecimal) {
		
		if (bigDecimal == null) {
			return null;
		}
		
		return newCurrencyFormat().format(bigDecimal);
		
	}

}
