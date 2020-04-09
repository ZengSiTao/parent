package com.linktop.cloud.entranceguardlongconn.busi;

import com.alibaba.fastjson.JSON;
import com.linktop.cloud.coder.DESCoder;
import com.linktop.cloud.commonutils.BusiSvrUploadFileUsage;
import com.linktop.cloud.entranceguardclient.feignclient.*;
import com.linktop.cloud.entranceguardlongconn.busi.util.Rc4Utils;
import com.linktop.cloud.entranceguardmodel.GetPtuidListResult;
import com.linktop.cloud.entranceguardmodel.database.DevUl;
import com.linktop.cloud.entranceguardmodel.database.Device;
import com.linktop.cloud.entranceguardmodel.database.GateEvent;
import com.linktop.cloud.entranceguardmodel.database.ShareFile;
import com.linktop.cloud.entranceguardmodel.sync.PushBody;
import com.linktop.cloud.entranceguardmodel.sync.PushBodyDevUl;
import com.linktop.cloud.entranceguardmodel.sync.PushBodyDoor;
import com.linktop.cloud.entranceguardmodel.sync.PushBodyKeyChange;
import com.linktop.cloud.file.FileUtil;
import com.linktop.cloud.jnaaes.JnaAES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.linktop.cloud.commonutils.BusiSvrUploadFileUsage.USER_INFO;
import static com.linktop.cloud.entranceguardlongconn.busi.util.Rc4Utils.charToByteArray;

@Component
public class CmdSocket implements CmdLongConInterface {
    private static Logger log = LoggerFactory.getLogger(CmdSocket.class);
	@Autowired
	private GateEventClient gateEventClient;
    @Autowired
    private DevUlClient devUlClient;
	@Autowired
    private DeviceConfigClient deviceConfigClient;
	@Autowired
    private Constans constans;
    @Autowired
    private BusiServerApiClient busiServerApiClient;
    @Autowired
    private DeviceClient deviceClient;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	private InetAddress serverAddress;
	private Socket cmdSocket;
	private OutputStream cmdOutputStream;
	private ParsePackKits parsePackKits;
	private InputStream inputStream;
	private ParsePackKits.ResultType decodeResp;
	private boolean excute;
	private AtomicInteger seq;
	private int oldSeq;
	private static final String UTF8 = "UTF-8";
	private static final String HMAC_SHA1 = "HmacSHA1";
	private byte[] decrypts;
	private int hbFlag = 0;

	public void subNewDevice(String id) throws IOException {

		StringBuffer sb = new StringBuffer();
		getIDHexStr(id, sb);
		String string = sb.toString();
		System.out.println("=====================");
		System.out.println(string);
		System.out.println("=====================");
		byte[] encrypt = JnaAES.encrypt(charToByteArray(string.toCharArray()), decrypts);
		StringBuffer strbuf = new StringBuffer(encrypt.length);
		for (int i = 0; i < encrypt.length; i++) {
			char c = (char) encrypt[i];
			System.out.println("*********/********");
			System.out.println(c);
			strbuf.append(c);
		}
		sub(count, strbuf.toString());
		count += 1;
	}

	public void connect(String host, int port) throws IOException {
	    log.info("connect to host:{}, port:{}", host, port);
		InetAddress serverAddress = InetAddress.getByName(host);
		cmdSocket = new Socket(serverAddress, port);
		cmdOutputStream = cmdSocket.getOutputStream();
        parsePackKits = new ParsePackKits();// 解析工具类
        excute = true;
        seq = new AtomicInteger();
    }

	@Override
	public void knock(int seqNo, String key) throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("knock");
		list.add(seqNo + "");
		list.add(key);
		String buildRequestString = parsePackKits.buildRequestString(list,
				false);
		String pack = ParsePackKits.buildPack(buildRequestString);
		cmdOutputStream.write(toByteArr(pack));
	}

	/**
	 * 读取服务器发回来的数据，并且解析
	 *
	 * @throws IOException
	 */
	public void readData() throws IOException {
		while (excute) {
			inputStream = cmdSocket.getInputStream();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			// 如果decodeResp的数据为空或者decode成功
			if (checkRespDecode()) {
                log.error("longconn to read data");
				len = read(buffer);
				log.error("longconn read data,len:{}", len);
			}
			if (len == -1 && excute) {
				inputStream.close();
				excute = false;
				break;
			}
			// ------------------------拿到服务包的内容--------------
			outStream.write(buffer, 0, len);
			byte[] byteArray = outStream.toByteArray();
			StringBuilder sb = ParsePackKits.byteArrayToStr(byteArray);
			String resp = sb.toString();
            log.info("resp = {}", resp);
			// ------------------------------------------------------
			if (isRemain()) {
				resp = decodeResp.finalResult.remain + resp;
			}
			decodeResp = parsePackKits.decodeResp(resp);

			// 根据包的正则判断包
			boolean decodeOK = decodeResp.finalResult.decodeOK;
			log.info("decodeOK:{}, decodeResp:{}", decodeOK,
                    JSON.toJSONString(decodeResp));
			if (!decodeOK) {
				continue;
			}
			List<String> listResult = decodeResp.finalResult.resultList;
            log.info(Arrays.toString(listResult.toArray()));
			if(CmdsConstant.CMDSTR.valueOf(listResult.get(0)) == CmdsConstant.CMDSTR.push ||
					CmdsConstant.CMDSTR.valueOf(listResult.get(0)) == CmdsConstant.CMDSTR.re_push) {
				FileUtil.byte2File(byteArray, "./", "push");
			}
			// 解析内容，关掉当前的流
			boolean checkPack = !checkRespPack(listResult);
			if (checkPack) {
                log.info("checkPack fail, close inputStream");
				inputStream.close();
				excute = false;
				break;
			}
		}
	}

	int count = 0;

	@SuppressWarnings("deprecation")
	private boolean checkRespPack(List<String> listResult) throws IOException {
		// 包的类型
		String commandWord = listResult.get(0);
		// 包的序号
		String seqNo = listResult.get(1);
		if (handlerError(listResult))
			return false;
		log.info("checkRespPack,commandWord:{}", commandWord);
		// 然后数据没有错误，就要根据commandWord然后分类解析
		switch (CmdsConstant.CMDSTR.valueOf(commandWord)) {
		case knock:
			// long reqnum = num_de+1L;
			decodeSeq(listResult);
			break;
		case login:
			decodeLogin(listResult);
            constans.setLogin(true);
			heartbeat(2);

			break;
		case hb:
			count = count + 1;
			if (constans.isLogin()) {
                String deviceIds = "";
                GetPtuidListResult res = busiServerApiClient.getPtuidList();
				if(!StringUtils.isEmpty(res.getListPtuid())) {
                    deviceIds = "".join("", res.getListPtuid());
                }
                log.error("deviceIds:{}", deviceIds);
				if(deviceIds.isEmpty()) {
					heartbeat(count);
                    constans.setLogin(false);
                    log.error("error! no device ptuid get!");
					break;
				}
				StringBuffer sb = new StringBuffer();
				getIDHexStr(deviceIds, sb);
				//RC4 rc4 = new RC4(decrypts);
				String string = sb.toString();
				System.out.println("=====================");
				System.out.println(string);
				System.out.println("=====================");
				//byte[] encrypt = rc4.encrypt(charToByteArray(string
				//		.toCharArray()));
				byte[] encrypt = JnaAES.encrypt(charToByteArray(string.toCharArray()), decrypts);
				StringBuffer strbuf = new StringBuffer(encrypt.length);
				for (int i = 0; i < encrypt.length; i++) {
					char c = (char) encrypt[i];
					System.out.println("*********/********");
					System.out.println(c);
					strbuf.append(c);
				}
				sub(3, strbuf.toString());
			} else {
				if (isThreadStart) {
					System.out.println("hh");
                    hbFlag = 1;
				} else {
					Thread thread = new Thread(runnable);
					thread.start();
				}
			}
			break;
		case sub:
            constans.setLogin(false);
			count = 4;
			heartbeat(count);
			break;
		case push:
		case re_push:
			System.out.println(""+Arrays.toString(listResult.toArray()));
			decodePush(listResult);
			break;
		default:
			break;
		}
		return true;
	}

	private void getIDHexStr(String qr, StringBuffer sb) {
		System.out.println("getIDHexStr ----------");
		int idStrLenth = qr.length();
		int idStrcounts = idStrLenth / 2;
		int n = 0;
		for (int i = 1; i <= idStrcounts; i++) {
			String string = qr.substring(n, i * 2);
			n = i * 2;
			int int1 = Integer.valueOf(string, 16);
			char c = (char) int1;
			System.out.println(c);
			sb.append(c);
		}
		System.out.println("getIDHexStr ----------");
	}

	private void decodeLogin(List<String> listResult) {
		// TODO Auto-generated method stub
		String cmdword = listResult.get(0);
		String num = listResult.get(1);
		String new_session_secret = listResult.get(2);
		decrypts = JnaAES.decrypt(new_session_secret, constans.getSecret());
		FileUtil.byte2File(decrypts, "./", "decrypts");
		log.info("decodeLogin -------------------");
		for (byte b : decrypts) {
			System.out.println(b & 0xff);
		}
		log.info("decodeLogin -------------------");

	}

	private boolean decodeSeq(List<String> listResult) {
		if (null == listResult) {
			return false;
		}
		// 加密方式
		String encrypt_method = listResult.get(2);
		String random_cipher = listResult.get(3);
		String random_sign = listResult.get(4);
		byte[] decodeStr = decodeStr(random_cipher);
		long radom_int = getRandomand1(random_sign, decodeStr);
		byte[] encrypt = JnaAES.encrypt("" + radom_int, constans.getSecret());
		byte[] decrypt = JnaAES.decrypt(encrypt, charToByteArray(constans.getSecret().toCharArray()));
		try {
			byte[] doSign = parsePackKits.doSign(("" + radom_int), new String(constans.getSecret()));
			byte[] doSign2 = DESCoder.encryptSHA(("" + radom_int).getBytes());
			login(1, constans.getKey(), new String(encrypt, "ISO-8859-1"),
					new String(doSign, "ISO-8859-1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private long getRandomand1(String random_sign, byte[] decodeStr) {
		String string = new String(decodeStr);
		long byteArrayToInt = Long.valueOf(string);
		long radom_int = byteArrayToInt + 1;
		checkSignIsSame(byteArrayToInt, random_sign);
		return radom_int;
	}

	private boolean checkSignIsSame(long radom_self, String severSign) {

		try {
			byte[] doSign = parsePackKits.doSign("" + radom_self,
                    constans.getSecret());
			byte[] array = Rc4Utils.charToByteArray(severSign.toCharArray());
			String str_doSign = new String(doSign, "ISO-8859-1");
			String str_array = new String(array, "ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private byte[] decodeStr(String random_cipher) {
		byte[] decrypt = JnaAES.decrypt(random_cipher, constans.getSecret());
		return decrypt;
	}


	private boolean decodePush(List<String> listResult) {
		if (null == listResult) {
			return false;
		}

		String body = listResult.get(2);
		String jsonBody = new String(JnaAES.decrypt(charToByteArray(body.toCharArray()), decrypts));
		log.info("push, {}",jsonBody);
		PushBody pb = (PushBody)JSON.parseObject(jsonBody, PushBody.class);

		try {
			handlePushBody(pb);
			pok(listResult.get(1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private void handlePushBody(PushBody pb) {
		if(null == pb.get_pld() || null == pb.getCb()) {
			return;
		}
		if(pb.getCb().contains("door")) {
			handlePushBodyDoor(pb);
		} else if(pb.getCb().contains(BusiSvrUploadFileUsage.DEV_UL)) {
            handlePushBodyDevUl(pb);
        } else if(pb.getCb().contains("key_change")) {
		    handlePushBodyKeyChange(pb);
        } else {
			log.error("unhandled cb:{}", pb.getCb());
		}
	}

	private void handlePushBodyDoor(PushBody pb) {

		if(null == pb) {
			return;
		}
		PushBodyDoor pbd = JSON.parseObject(pb.get_pld(), PushBodyDoor.class);
		if(null == pbd) {
			return;
		}
        log.error("PushBodyDoor, {}", JSON.toJSONString(pbd));
		GateEvent ge = new GateEvent();
		ge.setLockCat(pbd.getLock_cat());
		ge.setSrcTs(pbd.getSrc_ts());
		ge.setMedia(pbd.getMedia());
		ge.setUserAliaId(pbd.getUid());
		ge.setDeviceAliaId(pb.getId());
		ge.setReceipt(pbd.getReceipt());
		gateEventClient.add(ge);

	}


	private void handlePushBodyDevUl(PushBody pb) {

		if(null == pb) {
			return;
		}
		PushBodyDevUl pbDevUl = JSON.parseObject(pb.get_pld(), PushBodyDevUl.class);
		if(null == pbDevUl) {
			return;
		}

		if(pbDevUl.getUsage().equals(BusiSvrUploadFileUsage.DEVICE_CONFIG)) {
		    deviceConfigClient.updateByDeviceAliaId(
		            pb.getId(),
                    pbDevUl.getFn()
            );
		    return;
        }
        DevUl devUl = new DevUl();
		devUl.setReceipt(pbDevUl.getReceipt());
		devUl.setUsage(pbDevUl.getUsage());
		devUl.setFn(pbDevUl.getFn());
        devUlClient.add(devUl);
	}

    private void handlePushBodyKeyChange(PushBody pb) {

        if(null == pb) {
            return;
        }
        PushBodyKeyChange pbkc = JSON.parseObject(pb.get_pld(), PushBodyKeyChange.class);
        if(null == pbkc) {
            return;
        }
        log.error("PushBodyKeyChange, {}", JSON.toJSONString(pbkc));
        if(!StringUtils.isEmpty(pbkc.getTo()) &&
                !StringUtils.isEmpty(pbkc.getPid())) {
            if(pbkc.getTo().equalsIgnoreCase(constans.getKey())) {
                try {
                    subNewDevice(pbkc.getPid());
					deviceClient.delInitFileList(pbkc.getPid());
                } catch (Exception e) {
                    log.error("key change, subNewDevice exception:{}",
                            e.getMessage());
                }
            }
        }
        if(!StringUtils.isEmpty(pbkc.getFrom()) &&
                pbkc.getFrom().equalsIgnoreCase(constans.getKey())) {
            log.error("key change to other appkey");
            deviceClient.deleteByAliaId(pbkc.getPid());
        }
    }


	/**
	 * 判断是否有错误
	 *
	 * @param listResult
	 * @return
	 */
	private boolean handlerError(List<String> listResult) {
		if (listResult.size() > 2
				&& ParsePackKits.checkIsError(listResult.get(2))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断包是否需要拼接
	 *
	 * @return
	 */
	private boolean isRemain() {
		return decodeResp != null && decodeResp.finalResult != null
				&& decodeResp.finalResult.remain != null
				&& !decodeResp.finalResult.remain.equals("");
	}

	public int read(byte[] buffer) throws IOException {
		int bytes = -1;
		if (inputStream != null)
			bytes = inputStream.read(buffer);
		return bytes;
	}

	/**
	 * 判断是否解包成功
	 */
	private boolean checkRespDecode() {
		boolean isDecodeRespNull = (decodeResp == null);
		boolean isdecodeOK = (decodeResp != null && decodeResp.finalResult.decodeOK);
		boolean remainEmpty = (decodeResp != null
				&& decodeResp.finalResult.remain != null && decodeResp.finalResult.remain
				.equals(""));
		return isDecodeRespNull || !isdecodeOK || (isdecodeOK && remainEmpty);
	}

	/**
	 * 将String byte array
	 *
	 * @param str
	 * @return
	 */
	public byte[] toByteArr(String str) {
		return ParsePackKits.charToByteArray(str.toCharArray());
	}

	@Override
	public void login(int seqNo, String key, String new_cipher, String new_sign)
			throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("login");
		list.add(seqNo + "");
		list.add(key + "");
		list.add(new_cipher + "");
		list.add(new_sign + "");
		String buildRequestString = parsePackKits.buildRequestString(list,
				false);
		String pack = ParsePackKits.buildPack(buildRequestString);
		cmdOutputStream.write(toByteArr(pack));
	}

	@Override
	public void heartbeat(int seqNo) throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("hb");
		list.add(seqNo + "");
		String buildRequestString = parsePackKits.buildRequestString(list,
				false);
		String pack = ParsePackKits.buildPack(buildRequestString);
		cmdOutputStream.write(toByteArr(pack));
	}

    @Override
    public void pok(String seqNo) throws IOException {
        List<String> list = new ArrayList<String>();
        list.add("pok");
        list.add(seqNo);
        String buildRequestString = parsePackKits.buildRequestString(list,
                false);
        String pack = ParsePackKits.buildPack(buildRequestString);
        cmdOutputStream.write(toByteArr(pack));
    }

	@Override
	public void sub_qr(int seqNo, String qr) throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("sub");
		list.add(seqNo + "");
		list.add(qr + "");
		String buildRequestString = parsePackKits.buildRequestString(list,
				false);
		String pack = ParsePackKits.buildPack(buildRequestString);
		cmdOutputStream.write(toByteArr(pack));
	}

	@Override
	public void sub(int seqNo, String ptuid) throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("sub");
		list.add(seqNo + "");
		list.add(ptuid + "");
		String buildRequestString = parsePackKits.buildRequestString(list,
				false);
		String pack = ParsePackKits.buildPack(buildRequestString);
		cmdOutputStream.write(toByteArr(pack));
	}

	final long timeInterval = 10000;
	public boolean isThreadStart = false;
	Runnable runnable = new Runnable() {
		public void run() {
			while (true) {
				// ------- code for task to run
				isThreadStart = true;
				log.info("Hello !!isThreadStart:{}", isThreadStart);
				// ------- ends here
				try {
					Thread.sleep(timeInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					heartbeat(count);
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
		}
	};

	public void testDecodePush() {

		byte[] byteArray = FileUtil.file2Byte("./push.dat");
		decrypts = FileUtil.file2Byte("./decrypts.dat");
		StringBuilder sb = ParsePackKits.byteArrayToStr(byteArray);
		String resp = sb.toString();
		ParsePackKits parsePackKits = new ParsePackKits();// 解析工具类
		ParsePackKits.ResultType decodeResp;

		decodeResp = parsePackKits.decodeResp(resp);
		System.err.print("decodeResp = " + decodeResp);
		// 根据包的正则判断包
		boolean decodeOK = decodeResp.finalResult.decodeOK;
		if (!decodeOK) {
			return;
		}
		List<String> listResult = decodeResp.finalResult.resultList;
		System.out.println(Arrays.toString(listResult.toArray()));

		// 解析内容，关掉当前的流
		try {
			boolean checkPack = !checkRespPack(listResult);

		} catch (Exception e) {
			System.out.println("Exception:" + e.getMessage());
		}

	}


	//每隔30秒执行一次
	@Scheduled(fixedRate = 30000)
	public void chkHb() {
        log.info("chkHb, hbFlag:{}", hbFlag);
	    if(hbFlag == 1) {
            hbFlag = 2;
        } else {
            hbFlag = 0;
            log.error("chkHb, hbFlag:{}", hbFlag);
            try {
                inputStream.close();
            } catch (Exception e) {
                log.error("close inputStream exception:{}",
                        e.getMessage());
            }
        }
	}

	//每天10：40执行
	@Scheduled(cron = "0 40 10 ? * *")
	public void testTasks() {
		System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
	}

    public boolean isLongConnOk() {
        if(hbFlag > 0) {
            return true;
        }
        return false;
    }
}
