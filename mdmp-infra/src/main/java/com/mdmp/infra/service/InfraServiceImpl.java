package com.mdmp.infra.service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.mdmp.common.exception.MDMPException;
import com.mdmp.infra.bean.Report;
import com.mdmp.infra.cache.CacheManager;
import com.mdmp.infra.message.Message;
import com.mdmp.infra.operator.MessageOperator;
import com.mdmp.infra.operator.OperatorFactory;

@Service("infraService")
public class InfraServiceImpl implements InfraService {

	/**
	 * DataSourceId ===> Report Set
	 */
	//private Map<String, Set<Report>> reportCache = new ConcurrentHashMap<String, Set<Report>>();
	
	@Override
	public void processMessage(Message msg) {
		// Get data source Id
		String dsId = msg.getDataSourceId();
		try {
			// Init Handler chain by Handler Factory
			//MessageOperator handler = OperatorFactory.initHandlerChain(dsId);
			Report report = CacheManager.getReportCacheInstance().getReport(dsId);
			MessageOperator handler = CacheManager.getOperatorCacheInstance().getOperator(report.getId());
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

}
