package com.mdmp.infra.model.engine;

import com.mdmp.infra.bean.Model;
import com.mdmp.infra.message.Message;

public abstract class RuleEngin {
	public abstract Object run(Message msg, Model model);
}
