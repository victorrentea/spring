//package spring.training.proxy;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Service;
//
//@Primary
//@Service
//class ExpensiveOpsWithCache implements IExpensiveOps {
//	private final IExpensiveOps ops;
//	private Map<Integer, Boolean> cache = new HashMap<>(); 
//	// Multithread
//	// OutOfMemory
//	
//	public ExpensiveOpsWithCache(IExpensiveOps ops) {
//		this.ops = ops;
//	}
//
//	public Boolean isPrime(int n) {
//		if(cache.containsKey(n)) {
//			return cache.get(n);
//		}
//		Boolean result = ops.isPrime(n);
//		cache.put(n, result);
//		return result;
//	}
//
//	public String hashAllFiles(File folder) {
//		return ops.hashAllFiles(folder); // TODO
//	}
//}