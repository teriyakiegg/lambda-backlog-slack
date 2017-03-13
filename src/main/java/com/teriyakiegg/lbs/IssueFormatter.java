package com.teriyakiegg.lbs;

import com.nulabinc.backlog4j.Issue;

/**
 * Make the string of issue for Slack output
 * 
 * @author teriyakiegg
 *
 */
public class IssueFormatter {
	
	private StringBuilder sb;
	
	public IssueFormatter() {
	}
	
	protected String formatIssueInfo (Issue issue) {
		
		sb = new StringBuilder();
		sb.append("```");
		appendWithNewLine("Subject: " + issue.getSummary());
		appendWithNewLine("Assignee: " + issue.getAssignee().getName());
		appendWithNewLine("Status: " + issue.getStatus().getName());
		appendWithNewLine("Due Date: " + issue.getDueDate().toString());
		sb.append("```");
		
		return sb.toString();
	}
	
	private void appendWithNewLine (String string) {
		sb.append(string + "\n");
	}
}
