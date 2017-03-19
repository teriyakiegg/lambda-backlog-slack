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
		appendWithNewLine("Status: " + issue.getStatus().getName());
		
		String assignee = "Assignee: ";
		if (issue.getAssignee() != null) {
			assignee += issue.getAssignee().getName();
		} else {
			assignee += "Not set";
		}
		appendWithNewLine(assignee);
		
		String dueDate = "Due Date: ";
		if (issue.getDueDate() != null) {
			dueDate += issue.getDueDate().toString();
		} else {
			dueDate += "Not set";
		}
		appendWithNewLine(dueDate);
		
		sb.append("```");
		
		return sb.toString();
	}
	
	private void appendWithNewLine (String string) {
		sb.append(string + "\n");
	}
}
