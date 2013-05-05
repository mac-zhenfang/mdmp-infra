package com.mdmp.infra.processer;

import com.mdmp.infra.exception.MDMPException;


/**
 * 
 * @author johnny
 *
 */
public class ProcessorFactory {
	
	public static MessageProcessor getEventLogMessageProcessor() throws MDMPException{
		return getMessageProcessor(EventLogMessageProcessor.class);
	}

	public static MessageProcessor getKVMessageProcessor() throws MDMPException{
        return getMessageProcessor(KVMessageProcessor.class);
    }
	
	private static MessageProcessor getMessageProcessor(Class className) throws MDMPException{
		return null;
	}
}
