package com.mdmp.infra.model.engine;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.impl.ByteArrayResource;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.mdmp.infra.bean.Model;
import com.mdmp.infra.message.JsonMessage;
import com.mdmp.infra.message.Message;

/**
 * It's only support JsonMessage
 * @author dream
 *
 */
public class DroolsRuleEngin extends RuleEngin {

	@Override
	public Message run(Message msg, Model model) {
		if(Message.MSG_JSON != msg.getType()){
			return msg;
		}
		JsonMessage message = (JsonMessage)msg;
		try {
			// load up the knowledge base
			KnowledgeBase kbase = readKnowledgeBase(model);
			StatefulKnowledgeSession ksession = kbase
					.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
					.newFileLogger(ksession, "test");
			ksession.insert(message);
			ksession.fireAllRules();
			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	private static KnowledgeBase readKnowledgeBase(Model model)
			throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		String logic = model.getLogic();
		Resource logicResource = new ByteArrayResource(logic.getBytes());
		kbuilder.add(logicResource, ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
}
