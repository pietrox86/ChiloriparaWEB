package it.pbc.chiloripara.web.servlet;

import it.pbc.chiloripara.services.PaymentsService;
import it.pbc.chiloripara.services.interfaces.IRegistrazioneService;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderConfirm extends HttpServlet {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		IRegistrazioneService regService = (IRegistrazioneService) wac.getBean("registrazioneService");
		
		logger.debug("sono nella servlet");
		String paymentStatus = request.getParameter("payment_status");
		String authCode = request.getParameter("custom");
		logger.debug("token "+paymentStatus);
		logger.debug("payerid "+authCode);
		
		if(paymentStatus!=null && paymentStatus.equalsIgnoreCase("COMPLETED"))
		{
			regService.activateRegistro(authCode);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
