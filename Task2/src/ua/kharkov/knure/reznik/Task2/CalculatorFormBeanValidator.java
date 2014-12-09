package ua.kharkov.knure.reznik.Task2;

import java.util.HashMap;
import java.util.Map;

import ua.kharkov.knure.reznik.Task2.bean.CalculatorFormBean;

public class CalculatorFormBeanValidator {

	public static Map<String, String> validate(CalculatorFormBean cfb) {
		Map<String, String> errorMap = new HashMap<String, String>();
		if (!isNumber(cfb.getValue1())) {
			errorMap.put("value1", "Value1 must be number only.");
		}
		if (!isNumber(cfb.getValue2())) {
			errorMap.put("value2", "Value2 must be number only.");
		}
		if (isDivisionByZero(cfb.getValue1(), cfb.getValue2())) {
			errorMap.put("divisionByZero", "Division by zero is impossible.");
		}
		if (!isValidOperation(cfb.getOperation())) {
			errorMap.put("operation", "Operation not correct.");
		}
		return errorMap;
	}

	private static boolean isNumber(String value) {
		if (value != null && value.matches("-?\\d+(\\.\\d+)?")) {
			return true;
		}
		return false;
	}

	private static boolean isDivisionByZero(String value1, String value2) {
		if (value1 != null && value2 != null && value2.equals("0")) {
			return true;
		}
		return false;
	}

	private static boolean isValidOperation(String operation) {
		if (operation != null && operation.matches("plus|minus|multiply|division")) {
			return true;
		}
		return false;
	}

}