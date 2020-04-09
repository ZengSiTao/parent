package com.linktop.cloud.entranceguardcore.aop.handler;

import com.linktop.cloud.entranceguardcore.aop.intersector.HandlerIntersector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AspectHandler {
	private static Logger log = LoggerFactory.getLogger(AspectHandler.class);

	private Map<String, HandlerIntersector<?>> mapIntersector = new ConcurrentHashMap<String, HandlerIntersector<?>>();
	
	@Autowired
	public AspectHandler(HandlerIntersector<?>[] intersectors) {
		
		for(HandlerIntersector<?> intersector : intersectors) {
			mapIntersector.put(intersector.getClassName(), intersector);
		}
	}


	@SuppressWarnings("unchecked")
	private HandlerIntersector<Object> findIntersector(String className) {
		return (HandlerIntersector<Object>) mapIntersector.get(className);
	}

	public <T> void handleBefore(T intersectorVO) {
		HandlerIntersector<Object> intersector = findIntersector(intersectorVO.getClass().getName());
		if(null == intersector) {
            log.info("SYNC", "NO intersector for:" + intersectorVO.getClass().getName());
			return;
		}
		intersector.beforeHandle(intersectorVO);
	}
	
	public <T> void handleAfter(T intersectorVO) {
		HandlerIntersector<Object> intersector = findIntersector(intersectorVO.getClass().getName());
		if(null == intersector) {
            log.info("SYNC", "NO intersector for:" + intersectorVO.getClass().getName());
			return;
		}
		intersector.afterHandle(intersectorVO);
	}
}
