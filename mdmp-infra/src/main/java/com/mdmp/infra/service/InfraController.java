package com.mdmp.infra.service;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdmp.infra.messager.Message;


public class InfraController extends HttpServlet{

	@Resource(name="infraService")
	private InfraService infraService;
	
	@RequestMapping(value = "/ds", method = RequestMethod.POST)
	public @ResponseBody
	void processMessage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		StringValidator.verifyEmpty("jsonObject", appStr);
//		Message msg = mapper.readValue(appStr, DataSource.class);
		Message msg = null;
		infraService.processMessage(msg);
	}
}
