package com.mdmp.infra.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mdmp.common.ReportClient;
import com.mdmp.common.exception.MDMPException;
import com.mdmp.infra.bean.Report;

/**
 * 
 * @author johnny
 * 
 */
public class OperatorFactory {
	public static final String MODEL_HANDLER = "model";
	
	
	public static final int MONGODB_FORWARD_HANDLER = 1;
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static MessageHandler initHandlerChain(String dsId)
			throws MDMPException, JsonParseException, JsonMappingException, IOException {
		// Get DataSource by dsId
		// Get handler logic from Report
		Report report = ReportClient.getReportsByDataSourceId(dsId, null);
		// Init Handler chain
		return initHandlerChainInternal(report.getLogic());
	}

	private static MessageHandler initHandlerChainInternal(String logic)
			throws MDMPException, JsonParseException, JsonMappingException, IOException {
		OperatorBean[] handlerList = mapper.readValue(logic, OperatorBean[].class);
		Map<Integer, MessageHandler> msgHandlerList = new HashMap<Integer, MessageHandler>();
		for(OperatorBean oneBean : handlerList){
			MessageHandler handler = getMessageHandler(oneBean);
			if(handler == null){
				continue;
			}
			msgHandlerList.put(oneBean.getIndex(), handler);
		}
		for(OperatorBean oneBean : handlerList){
			MessageHandler handler = msgHandlerList.get(oneBean.getIndex());
			for(int childIndex : oneBean.getChildren()){
				handler.addChinldHanlder(msgHandlerList.get(childIndex));
			}
		}
		return msgHandlerList.get(0);
	}
	
	public static MessageHandler getMessageHandler(OperatorBean handlerBean){
		if(MODEL_HANDLER.equals(handlerBean.getName())){
			return new ModelMessageHandler();
		}
		return null;
	}
}
