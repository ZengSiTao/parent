package com.linktop.cloud.entranceguardcore.aop.intersector;


public interface HandlerIntersector<T extends Object>{
	String getClassName();
	boolean beforeHandle(T obj);
	boolean afterHandle(T obj);
}
