
1) make sure http://localhost:8081/actuator/prometheus returns data
- management.endpoints.web.exposure.include=*
  management.endpoint.prometheus.enabled=true
  
- spring-boot-starter-actuator in pom.xml

2) install prometheus
add this to prometheus.yml as a child of `scrape_configs`
```
- job_name: 'spring'
  metrics_path: '/actuator/prometheus'
  static_configs:
  - targets: ['localhost:8081']
```

3) start-grafana.bat

4) in grafana http://localhost:3000/
   (user/pass  admin/admin)
   Add a datasource to prometheus http://localhost:9090  Access=BROWSER!!
   
   Then add a Panel + a query like "http_server_requests_seconds_max"