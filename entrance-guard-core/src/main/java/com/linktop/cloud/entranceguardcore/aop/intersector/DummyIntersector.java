package com.linktop.cloud.entranceguardcore.aop.intersector;

import com.linktop.cloud.entranceguardcore.aop.vo.DummyVO;

public class DummyIntersector implements HandlerIntersector<DummyVO> {

	@Override
	public String getClassName() {
		return DummyVO.class.getName();
	}

	@Override
	public boolean beforeHandle(DummyVO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean afterHandle(DummyVO obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
