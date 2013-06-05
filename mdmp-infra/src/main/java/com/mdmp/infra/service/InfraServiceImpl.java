package com.mdmp.infra.service;

import org.springframework.stereotype.Service;

import com.mdmp.common.exception.MDMPException;
import com.mdmp.infra.bean.Report;
import com.mdmp.infra.cache.CacheManager;
import com.mdmp.infra.message.Message;
import com.mdmp.infra.operator.MessageOperator;

@Service("infraService")
public class InfraServiceImpl implements InfraService {

	@Override
	public void processMessage(Message msg) {
		// Get data source Id
		String dsId = msg.getDataSourceId();
		try {
			// Init Handler chain by Handler Factory
			Report report = CacheManager.getReportCacheInstance().getReport(dsId);
			MessageOperator handler = CacheManager.getOperatorCacheInstance().getOperator(report.getId());
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

}
