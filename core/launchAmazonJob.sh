#!/bin/sh

# MARKETPLACE_ID="A1RKKUPIHCS9HS" #ES
 MARKETPLACE_ID="APJ6JRA9NG5V4" #IT
# MARKETPLACE_ID="A1F83G8C2ARO7P" #CO.UK

curl -G -X POST --header 'Content-Type: application/json' --header 'Accept: text/plain' --data-urlencode 'jobParameters=a='$(date +%s)',pathToFile=/public_html/20171108/OirFeed_esempio_IT.xml,marketplaceId='$MARKETPLACE_ID',ftpHost=46.4.228.223,ftpPort=2121,ftpUser=feed,ftpPassword=knBESaXrN4xZv3gW' http://localhost:8080/batch/operations/jobs/amazon