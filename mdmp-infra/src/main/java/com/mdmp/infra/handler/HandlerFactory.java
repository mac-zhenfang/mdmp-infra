package com.mdmp.infra.handler;

import com.mdmp.common.exception.MDMPException;
import com.mdmp.infra.handler.kv.KVMessageHandler;

/**
 * 
 * @author johnny
 * 
 */
public class HandlerFactory {
	
	public static final int MONGODB_FORWARD_HANDLER = 1;

	/*
	 * public static MessageProcessor getEventLogMessageProcessor() throws
	 * MDMPException{ return
	 * getMessageProcessor(EventLogMessageProcessor.class); }
	 */

	public static MessageHandler initHandlerChain(String dsId)
			throws MDMPException {
		// Get DataSource by dsId
		
		// Get handler logic from DataSource and Modle id
		
		// Init Handler chain
		return getMessageProcessor(KVMessageHandler.class);
	}

	public static MessageHandler getKVMessageProcessor() throws MDMPException {
		return getMessageProcessor(KVMessageHandler.class);
	}

	private static MessageHandler getMessageProcessor(Class className)
			throws MDMPException {
		return null;
	}
}
