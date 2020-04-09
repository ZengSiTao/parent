package com.linktop.cloud.entranceguardlongconn.busi;

import java.io.IOException;

public interface CmdLongConInterface {

	void knock(int seqNo, String key) throws IOException;

	void login(int seqNo, String key, String new_cipher, String new_sign)
			throws IOException;

	void heartbeat(int seqNo) throws IOException;

    void pok(String seqNo) throws IOException;

	void sub_qr(int seqNo, String qr) throws IOException;

	void sub(int seqNo, String ptuid) throws IOException;
}
