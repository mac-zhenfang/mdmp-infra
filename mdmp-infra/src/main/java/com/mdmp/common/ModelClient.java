package com.mdmp.common;

import com.mdmp.common.util.Context;
import com.mdmp.common.util.http.HttpClientUtil;
import com.mdmp.infra.bean.DataSource;
import com.mdmp.infra.bean.Model;

public class ModelClient {
	static Context context = new Context();

	public static Model getModel(String dsId, String token)
			throws Exception {
		String projectUrl = context.get(MDMPConstants.WORKSPACE_URI)
				+ Context.getUrl("create.project.uri");
		String method = Context.getMethod("create.project.uri");
		return HttpClientUtil.request(String.format(projectUrl, dsId), method,
				null, null, Model.class);
	}
}
