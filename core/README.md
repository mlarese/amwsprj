#OIR S.r.l.: dataFeed

Progetto Spring Inizializr con
- SpringBoot
- Web
- REST repositories
- JAX/RS
- Session
- DevTools

Java version:
- 1.8

SpringBoot version:
- 1.4.2.RELEASE

#Per lanciare:

    gradle bootRun

    http://localhost:8080
    
#Swagger UI
    
    http://localhost:8080/swagger-ui.html
    
#note
    
    curl -G -X POST --header 'Content-Type: application/json' --header 'Accept: text/plain' --data-urlencode 'jobParameters=a=b' http://127.0.0.1:8080/batch/operations/jobs/xmlExporterJob
    