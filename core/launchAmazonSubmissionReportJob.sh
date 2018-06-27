
#!/bin/sh

curl -G -X POST --header 'Content-Type: application/json' --header 'Accept: text/plain' --data-urlencode 'jobParameters=a='$(date +%s)'' http://localhost:8080/batch/operations/jobs/amazon-submission-report