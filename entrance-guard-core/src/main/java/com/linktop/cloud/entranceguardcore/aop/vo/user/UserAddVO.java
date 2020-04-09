package com.linktop.cloud.entranceguardcore.aop.vo.user;

import com.linktop.cloud.entranceguardcore.aop.IntersectorBase;

public class UserAddVO extends IntersectorBase {

	private static final long serialVersionUID = 6851095115025979548L;

	@Override
	public String getSyncName() {
		return UserAddVO.class.getName();
	}

}
