package com.mdmp.infra.service;

import org.springframework.stereotype.Service;

import com.mdmp.infra.messager.Message;

@Service("infraService")
public class InfraServiceImpl implements InfraService{

	@Override
	public void processMessage(Message msg) {
		
	}

}
