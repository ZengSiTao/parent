package com.linktop.cloud.entranceguardcore.aop.vo.user;

import com.linktop.cloud.entranceguardcore.aop.IntersectorBase;

public class UserGetPageVO extends IntersectorBase {

	private static final long serialVersionUID = -6370657879880619159L;

	@Override
	public String getSyncName() {
		return UserGetPageVO.class.getName();
	}

}
