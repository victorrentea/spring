java -jar zipkin.jar

>> open http://localhost:9411/zipkin

>> start all 4 apps
(comment out the rabbit binder and spring cloud stream
if you don't have rabbit installed)

>> navigate to http://localhost:8081/zipkin