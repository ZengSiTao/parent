package com.linktop.cloud.entranceguardcore.aop.vo;

import com.linktop.cloud.entranceguardcore.aop.IntersectorBase;

public class DummyVO extends IntersectorBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -548541429303045713L;

	@Override
	public String getSyncName() {
		return DummyVO.class.getName();
	}

}
