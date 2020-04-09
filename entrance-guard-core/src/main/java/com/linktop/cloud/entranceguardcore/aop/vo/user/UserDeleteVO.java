package com.linktop.cloud.entranceguardcore.aop.vo.user;

import com.linktop.cloud.entranceguardcore.aop.IntersectorBase;

public class UserDeleteVO extends IntersectorBase {

    private static final long serialVersionUID = 59802371826009650L;

    @Override
	public String getSyncName() {
		return UserDeleteVO.class.getName();
	}

}
