package com.linktop.cloud.entranceguardcore.aop;

import java.io.Serializable;

public abstract class IntersectorBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8591515208068208942L;
	private Object argObj;
	private Object retObj;	
	public Object getArgObj() {
		return argObj;
	}
	public void setArgObj(Object argObj) {
		this.argObj = argObj;
	}
	public Object getRetObj() {
		return retObj;
	}
	public void setRetObj(Object retObj) {
		this.retObj = retObj;
	}

	public abstract String getSyncName();
}
