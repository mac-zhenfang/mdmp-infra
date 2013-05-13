package com.mdmp.infra.service;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdmp.common.util.StringUtils;
import com.mdmp.infra.message.JsonMessage;


public class InfraController extends HttpServlet{

	@Resource(name="infraService")
	private InfraService infraService;
	
	@RequestMapping(value = "/ds/{id}/actions/put/invoke", method = RequestMethod.POST)
	public @ResponseBody
	void put(@PathVariable String id, @RequestBody String appStr) throws IOException {
		StringUtils.verifyEmpty("jsonObject", appStr);
		JsonMessage msg = new JsonMessage(id, appStr);
		infraService.processMessage(msg);
	}
}
