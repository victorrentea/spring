//package spring.training.events;
//
//import com.orange.malima.customeralarms.service.configuration.Constants;
//import com.orange.malima.customeralarms.service.importer.integration.*;
//import com.orange.malima.customeralarms.service.importer.service.AmqpListenersService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.integration.amqp.dsl.Amqp;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.IntegrationFlows;
//import org.springframework.messaging.Message;
//import org.springframework.stereotype.Component;
//
//import java.text.NumberFormat;
//
//
//@Component
//public class ProcessingFlowBuilder {
//    @Autowired
//    private MyMessageFilter filter;
//    @Autowired
//    private MyMessageHandler handler;
//
//    public IntegrationFlow createAlarmIntegrationFlow() {
//        return IntegrationFlows.from("q1in")
//                .filter(filter, "canProceed")
//                .handle(handler, "processAlarmMessage")
//                .channel((messageGroup, timeout) ->
//                        messageMetricRegister.registerAcknowledgedMessage(messageGroup))
//                .get();
//    }
//}
//
//@Component
//class MyMessageFilter {
//    public boolean canProceed(String s) {
//        return s.equals(s.toUpperCase());
//    }
//}
//
//@Component
//class MyMessageHandler {
//    public String handle(String s) {
//        s.
//    }
//
//}