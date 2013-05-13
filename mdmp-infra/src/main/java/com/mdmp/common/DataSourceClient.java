package com.mdmp.common;

import com.mdmp.common.util.Context;
import com.mdmp.common.util.http.HttpClientUtil;
import com.mdmp.infra.bean.DataSource;

public class DataSourceClient {
	static Context context = new Context();

	public static DataSource getDataSource(String dsId, String token)
			throws Exception {
		String projectUrl = context.get(MDMPConstants.WORKSPACE_URI)
				+ Context.getUrl("create.project.uri");
		String method = Context.getMethod("create.project.uri");
		return HttpClientUtil.request(String.format(projectUrl, dsId), method,
				null, null, DataSource.class);
	}
}
