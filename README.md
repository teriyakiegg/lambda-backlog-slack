# Lambda-Backlog-Slack

Lambda-Backlog-Slack is a program to get issue-statuses of Backlog from Slack through AWS Lambda.  
Java 8, Maven and Eclipse are used for this program.

* AWS Lambda
	* [https://aws.amazon.com/lambda/](https://aws.amazon.com/lambda/)

* Backlog API
    * [http://developer.nulab-inc.com/docs/backlog/api/2/](http://developer.nulab-inc.com/docs/backlog/api/2/)

* Slack API (Outgoing Webhooks)
	* [https://api.slack.com/custom-integrations/outgoing-webhooks](https://api.slack.com/custom-integrations/outgoing-webhooks)

## How to use

1. Create an AWS account and a Lambda function, selecting "microservice-http-endpoint" as a blueprint.  
Runtime is Java 8, Handler is "com.teriyakiegg.lbs.LbsHandler::handler".

2. Configure POST for Lambda function, and set "Mapping Templates" as below in "Integration Request" of API Gateway.  
Enter "application/x-www-form-urlencoded" in Content-Type.

    ## convert HTML POST data or HTTP GET query string to JSON
    
    ## get the raw post data from the AWS built-in variable and give it a nicer name
    #if ($context.httpMethod == "POST")
     #set($rawAPIData = $input.path('$'))
    #elseif ($context.httpMethod == "GET")
     #set($rawAPIData = $input.params().querystring)
     #set($rawAPIData = $rawAPIData.toString())
     #set($rawAPIDataLength = $rawAPIData.length() - 1)
     #set($rawAPIData = $rawAPIData.substring(1, $rawAPIDataLength))
     #set($rawAPIData = $rawAPIData.replace(", ", "&"))
    #else
     #set($rawAPIData = "")
    #end
    
    ## first we get the number of "&" in the string, this tells us if there is more than one key value pair
    #set($countAmpersands = $rawAPIData.length() - $rawAPIData.replace("&", "").length())
    
    ## if there are no "&" at all then we have only one key value pair.
    ## we append an ampersand to the string so that we can tokenise it the same way as multiple kv pairs.
    ## the "empty" kv pair to the right of the ampersand will be ignored anyway.
    #if ($countAmpersands == 0)
     #set($rawPostData = $rawAPIData + "&")
    #end
    
    ## now we tokenise using the ampersand(s)
    #set($tokenisedAmpersand = $rawAPIData.split("&"))
    
    ## we set up a variable to hold the valid key value pairs
    #set($tokenisedEquals = [])
    
    ## now we set up a loop to find the valid key value pairs, which must contain only one "="
    #foreach( $kvPair in $tokenisedAmpersand )
     #set($countEquals = $kvPair.length() - $kvPair.replace("=", "").length())
     #if ($countEquals == 1)
      #set($kvTokenised = $kvPair.split("="))
      #if ($kvTokenised[0].length() > 0)
       ## we found a valid key value pair. add it to the list.
       #set($devNull = $tokenisedEquals.add($kvPair))
      #end
     #end
    #end
    
    ## next we set up our loop inside the output structure "{" and "}"
    {
    #foreach( $kvPair in $tokenisedEquals )
      ## finally we output the JSON for this pair and append a comma if this isn't the last pair
      #set($kvTokenised = $kvPair.split("="))
     "$util.urlDecode($kvTokenised[0])" : #if($kvTokenised[1].length() > 0)"$util.urlDecode($kvTokenised[1])"#{else}""#end#if( $foreach.hasNext ),#end
    #end
    }
 
3. Create a Slack account and configure Outgoing WebHooks.

4. Enter "shinchoku" in Trigger Word(s) and copy and paste the URL from API Gateway into "URL(s)".

5. Create a Backlog account, projects and issues.

6. Import lambda-backlog-slack into Eclipse after clone from git.

7. Configure lbs.properties as below.

    *slackToken: you can get from "Token" in Outgoing WebHooks configuration page of Slack.  
    *backlogSpaceId: this is your subdomain part of backlog URL.  
    (i.e. if your subdomain is "example.backlog.jp", backlogSpaceId is "example".)  
    *backlogApiKey = you can get from Personal Settings of Backlog.

8. In Eclipse right-click the project, click "Run As" -> "Maven build..." and enter "package shade:shade".

9. Upload the jar file which was created in target directory to Lambda Functions.

10. Enter "shinchoku *" in your Slack, then bot replies to you about the issue information!  
Note: * is your issue key.


## Notes
* LBS is short for Lambda + Backlog + Slack.