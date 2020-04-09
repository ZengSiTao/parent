package com.linktop.cloud.entranceguardcore.aop.vo.user;

import com.linktop.cloud.entranceguardcore.aop.IntersectorBase;

public class UserBatchDelVO extends IntersectorBase {

	private static final long serialVersionUID = 4467283312038030791L;

	@Override
	public String getSyncName() {
		return UserBatchDelVO.class.getName();
	}

}
