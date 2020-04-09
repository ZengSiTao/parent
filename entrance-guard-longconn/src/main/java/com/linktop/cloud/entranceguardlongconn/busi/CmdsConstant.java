package com.linktop.cloud.entranceguardlongconn.busi;

public interface CmdsConstant {
	public static final int MSSMAX = 1460;
	public static final int DECREASERATE = 10;

	enum CMDSTR {
		knock, login, hb,sub_qr,sub,push,re_push
	}
}
