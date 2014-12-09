package ua.kharkov.knure.reznik.Task2;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.kharkov.knure.reznik.Task2.bean.CalculatorFormBean;

@WebServlet("/calculator")
public class Calculator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CalculatorFormBean cfb = getCalculatorFormBean(request);
		Map<String, String> errorMap = CalculatorFormBeanValidator
				.validate(cfb);
		request.setAttribute("errorMap", errorMap);
		if (errorMap.size() > 0) {
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
			return;
		}

		double value1 = Double.valueOf(cfb.getValue1());
		double value2 = Double.valueOf(cfb.getValue2());
		double result = calculate(value1, value2, cfb.getOperation());

		request.setAttribute("value1", value1);
		request.setAttribute("value2", value2);
		request.setAttribute("operation", cfb.getOperation());
		request.setAttribute("result", result);

		request.getRequestDispatcher("result.jsp").forward(request, response);
	}

	private double calculate(double value1, double value2, String operation) {
		double result = 0;
		switch (operation) {
		case "plus":
			result = value1 + value2;
			break;
		case "minus":
			result = value1 - value2;
			break;
		case "multiply":
			result = value1 * value2;
			break;
		case "division":
			result = value1 / value2;
			break;
		}
		return result;
	}

	private CalculatorFormBean getCalculatorFormBean(HttpServletRequest request) {
		CalculatorFormBean cfb = new CalculatorFormBean();
		String value1 = request.getParameter("value1");
		String value2 = request.getParameter("value2");
		String operation = request.getParameter("operation");
		cfb.setValue1(value1);
		cfb.setValue2(value2);
		cfb.setOperation(operation);
		return cfb;
	}

}