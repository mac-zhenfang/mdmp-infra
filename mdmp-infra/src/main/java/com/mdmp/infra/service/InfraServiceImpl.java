package com.mdmp.infra.service;

import org.springframework.stereotype.Service;

import com.mdmp.common.exception.MDMPException;
import com.mdmp.infra.handler.HandlerFactory;
import com.mdmp.infra.handler.MessageHandler;
import com.mdmp.infra.messager.Message;

@Service("infraService")
public class InfraServiceImpl implements InfraService {

	@Override
	public void processMessage(Message msg) {
		// Get data source Id
		String dsId = msg.getDataSourceId();
		try {
			// Init Handler chain by Handler Factory
			MessageHandler handler = HandlerFactory.initHandlerChain(dsId);
			// Submit message
			handler.submitMessage(msg);
		} catch (MDMPException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
