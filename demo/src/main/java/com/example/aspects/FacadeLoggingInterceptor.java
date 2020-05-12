//package com.example.aspects;
//
//import com.bnpp.regliss.service.ReglissRequestContext;
//import com.bnpp.regliss.utils.PerformanceUtil;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.ServletOutputStream;
//import java.io.InputStream;
//import java.lang.annotation.Retention;
//import java.lang.annotation.Target;
//import java.lang.reflect.Method;
//import java.util.stream.Stream;
//
//import static java.lang.annotation.ElementType.METHOD;
//import static java.lang.annotation.RetentionPolicy.RUNTIME;
//import static java.util.stream.Collectors.joining;
//
//@Aspect
//@Component
//public class FacadeLoggingInterceptor {
//
//	@Target(METHOD)
//	@Retention(RUNTIME)
//	public @interface NotLogged {
//	}
//
//	@Autowired
//	private ReglissRequestContext requestContext;
//
//	private ObjectMapper objectMapper = new ObjectMapper();
//
//	public Logger log = LoggerFactory.getLogger(FacadeLoggingInterceptor.class);
//
//	@PostConstruct
//	public void configureMapper() {
//		if (log.isTraceEnabled()) {
//			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//		}
//	}
//
//	@Around("execution(* *(..)) && @annotation(org.springframework.scheduling.annotation.Async))")
//	public Object test(ProceedingJoinPoint joinPoint) throws Throwable {
//		return joinPoint.proceed();
//	}
//
//	@Around("execution(* *(..)) && @within(com.bnpp.regliss.config.facade.Facade))")
//	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//		logBefore(joinPoint);
//		long t0 = System.currentTimeMillis();
//
//		Object returnedObject;
//		try {
//			returnedObject = joinPoint.proceed();
//		} catch (Exception e) {
//			long deltaMillis = System.currentTimeMillis() - t0;
//			log.error("Exception ({}): {}: {}",
//					PerformanceUtil.formatDeltaTime(deltaMillis),
//					e.getClass(), e.getMessage());
//			throw e;
//		}
//
//		logAfter(joinPoint, returnedObject, t0);
//		return returnedObject;
//	}
//	private boolean isNotLogged(ProceedingJoinPoint joinPoint) {
//		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//		Method method = signature.getMethod();
//		return method.getAnnotation(NotLogged.class) != null;
//	}
//
//	private void logBefore(ProceedingJoinPoint joinPoint) {
//		if (log.isDebugEnabled() ) {
//			String separator = log.isTraceEnabled() ? "\n" : ", ";
//			String argListConcat;
//			if (isNotLogged(joinPoint)) {
//				argListConcat = "@NotLogged";
//			} else {
//				argListConcat = Stream.of(joinPoint.getArgs())
//						.map(this::objectToJson)
//						.map(Object::toString)
//						.collect(joining(separator));
//			}
//
//			log.debug("Invoking {}.{}(..) (user:{}): {}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), requestContext.getUsername(), argListConcat);
//		}
//	}
//
//	private String objectToJson(Object object) {
//		try {
//			if (object instanceof ServletOutputStream) {
//				return "<ServletOutputStream>";
//			} else if (object instanceof InputStream) {
//				return "<InputStream>";
//			} else {
//				return objectMapper.writeValueAsString(object);
//			}
//		} catch (JsonProcessingException e) {
//			log.warn("Could not serialize as JSON (stacktrace on TRACE): " + e);
//			log.trace("Cannot serialize value: " + e, e);
//
//			return "<ERR>";
//		}
//	}
//
//	private void logAfter(ProceedingJoinPoint joinPoint, Object returnedObject, long t0) {
//		if (log.isDebugEnabled()) {
//			long deltaMillis = System.currentTimeMillis() - t0;
//			String returnString = "<not logged>";
//			if (!isNotLogged(joinPoint)) {
//				returnString = objectToJson(returnedObject);
//			}
//			log.debug("Return from {}.{} ({} sec): {}",
//					joinPoint.getTarget().getClass().getSimpleName(),
//					joinPoint.getSignature().getName(),
//					PerformanceUtil.formatDeltaTime(deltaMillis),
//					returnString);
//		}
//	}
//
//}
