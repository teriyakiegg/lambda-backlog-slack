package com.teriyakiegg.lbs;

import static com.teriyakiegg.lbs.Constants.PROPERTIES_PATH;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * This class handles the call from API Gateway
 * 
 * @author teriyakiegg
 *
 */
public final class LbsHandler {
	
	private PropertiesLoader properties;
	private LambdaLogger lambdaLogger;
	
    public Map<String,Object> handler(Map<String,Object> input, Context context){
    	
    	Map<String,Object> output = new HashMap<String,Object>();
    	lambdaLogger = context.getLogger();
    	properties = new PropertiesLoader(PROPERTIES_PATH);
    	
    	if (!input.get("token").equals(properties.getProperty("slackToken"))) {
    		lambdaLogger.log("Unauthorized access by inappropriate slack token.");
    		return output;
    	}
    	
    	String inputIssue = input.get("text").toString().substring(10).trim();
    	
    	BacklogGateway bpGateway = new BacklogGateway(properties);
    	IssueFormatter issueFormatter = new IssueFormatter();
    	output.put("text", issueFormatter.formatIssueInfo(bpGateway.getIssue(inputIssue)));
        
        return output;
    }
}