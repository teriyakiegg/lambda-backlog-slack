package com.teriyakiegg.lbs;

import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.BacklogClientFactory;
import com.nulabinc.backlog4j.Issue;
import com.nulabinc.backlog4j.Project;
import com.nulabinc.backlog4j.conf.BacklogConfigure;
import com.nulabinc.backlog4j.conf.BacklogJpConfigure;

import java.net.MalformedURLException;

/**
 * For Backlog API
 * 
 * @author teriyakiegg
 *
 */
public class BacklogProjectGateway {
	
	private BacklogClient backlog;

	public BacklogProjectGateway (PropertiesLoader properties) {
		
		BacklogConfigure configure;
		try {
			configure = new BacklogJpConfigure(properties.getProperty("backlogSpaceId"))
					.apiKey(properties.getProperty("backlogApiKey"));
			
			backlog = new BacklogClientFactory(configure).newClient();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	protected Project getProject (String projectKey) {
		return backlog.getProject(projectKey);
	}
	
	protected Issue getIssue (String issueKey) {
		return backlog.getIssue(issueKey);
	}
}
