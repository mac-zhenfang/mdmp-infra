package com.mdmp.infra.service;

import org.springframework.stereotype.Service;

import com.mdmp.common.exception.MDMPException;
import com.mdmp.infra.bean.Report;
import com.mdmp.infra.cache.CacheManager;
import com.mdmp.infra.handler.MessageHandler;
import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;

@Service("infraService")
public class InfraServiceImpl implements InfraService {

	@Override
	public void processMessage(Message msg) {
		// Get data source Id
		String dsId = msg.getDataSourceId();
		try {
			// Init Handler chain by Handler Factory
			Report report = CacheManager.getReportCacheInstance().getReport(dsId);
			MessageHandler handler = CacheManager.getHandlerCacheInstance().getHandler(report.getId());
			if(handler==null){
				return;
			}
			// Submit message
			handler.submitMessage(msg);
		} catch (MDMPException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateReports() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		InfraServiceImpl infra = new InfraServiceImpl();
		JsonMessage msg = new JsonMessage(
				"001",
				"{\"name\":\"news\",\"age\":15,\"dsId\":001,\"ip\":\"10.224.192.166\",\"no\":10,\"location\":\"china\", \"GroupByKey\":\"location\"}");
		infra.processMessage(msg);
		System.out.println(CacheManager.getMongodbCacheInstance().getValue("aaa"));
	}
}
