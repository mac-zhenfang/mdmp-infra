package com.mdmp.infra.service;

import com.mdmp.infra.message.Message;


public interface InfraService{

	void processMessage(Message msg);
	
	void updateReports();
}
