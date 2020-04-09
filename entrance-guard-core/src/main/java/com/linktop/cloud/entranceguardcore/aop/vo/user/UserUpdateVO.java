package com.linktop.cloud.entranceguardcore.aop.vo.user;


import com.linktop.cloud.entranceguardcore.aop.IntersectorBase;

public class UserUpdateVO extends IntersectorBase {

	private static final long serialVersionUID = 6163018366476327029L;

	@Override
	public String getSyncName() {
		return UserUpdateVO.class.getName();
	}

}
