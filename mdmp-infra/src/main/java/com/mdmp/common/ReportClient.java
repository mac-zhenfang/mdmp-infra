package com.mdmp.common;

import com.mdmp.common.util.Context;
import com.mdmp.common.util.http.HttpClientUtil;
import com.mdmp.infra.bean.Report;

public class ReportClient {
	static Context context = new Context();

	public static Report getReport(String reportId, String token)
			throws Exception {
		String projectUrl = context.get(MDMPConstants.WORKSPACE_URI)
				+ Context.getUrl("create.project.uri");
		String method = Context.getMethod("create.project.uri");
		return HttpClientUtil.request(String.format(projectUrl, reportId), method,
				null, null, Report.class);
	}

	public static Report getReportsByDataSourceId(String dsId, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
