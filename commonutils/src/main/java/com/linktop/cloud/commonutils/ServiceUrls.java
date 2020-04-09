package com.linktop.cloud.commonutils;

public class ServiceUrls {

    public static final class Login {
        public static final String LOGIN = "login";
    }

    public static final class Account {

        public static final String LISTALL = "account/listall";
        public static final String GETBYID = "account/get/{id}";
        public static final String UPDATEBYID = "account/update/{id}";
        public static final String DELBYID = "account/del/{id}";
        public static final String ADD = "account/add";
    }

    public static final class Device {

        public static final String LISTALL = "device/listall";
        public static final String GETBYID = "device/get";
        public static final String UPDATEBYID = "device/update";
        public static final String DELBYID = "device/del";
        public static final String BATCHDEL = "device/batchdel";
        public static final String DELBYALIAID = "device/delbyaliaid";
        public static final String BATCHDELBYALIAID = "device/batchdelbyaliaid";
        public static final String ADD = "device/add";
        public static final String PAGE = "device/page";
        public static final String GETINITFILELIST = "device/getinitfilelist";
        public static final String DELINITFILELIST = "device/delinitfilelist";
    }

    public static final class DeviceConfig {

        public static final String GETBYDEVID = "devcfg/getbydevid";
        public static final String UPDATEBYID = "devcfg/update";
        public static final String UPDATEBYDEVICEALIAID = "devcfg/update_by_device_aliaid";
        public static final String DELBYID = "devcfg/del";
        public static final String BATCHDEL = "devcfg/batchdel";
        public static final String ADD = "devcfg/add";
        public static final String PUSHDEFAULTBYDEVID = "devcfg/pushdefaultbydevid";
        public static final String PUSHDEFAULTBYPTUID = "devcfg/pushdefaultbyptuid";
    }

    public static final class DevUl {
        public static final String ADD = "devul/add";
    }

    public static final class ICCard {

        public static final String LISTALL = "iccard/listall";
        public static final String GETBYID = "iccard/get";
        public static final String UPDATEBYID = "iccard/update";
        public static final String DELBYID = "iccard/del";
        public static final String BATCHDEL = "iccard/batchdel";
        public static final String ADD = "iccard/add";
        public static final String PAGE = "iccard/page";
    }

    public static final class Users {

        public static final String LISTALL = "users/listall";
        public static final String GETBYID = "users/get";
        public static final String UPDATEBYID = "users/update";
        public static final String DELBYID = "users/del";
        public static final String BATCHDEL = "users/batchdel";
        public static final String ADD = "users/add";
        public static final String PAGE = "users/page";
        public static final String GENUSERID = "users/genuserid";
    }

    public static final class Face {

        public static final String LISTALL = "face/listall";
        public static final String GETBYID = "face/get";
        public static final String UPDATEBYID = "face/update";
        public static final String DELBYID = "face/del";
        public static final String BATCHDEL = "face/batchdel";
        public static final String ADD = "face/add";
        public static final String PAGE = "face/page";
    }

    public static final class BlacklistFace {

        public static final String LISTALL = "blacklistface/listall";
        public static final String GETBYID = "blacklistface/get";
        public static final String UPDATEBYID = "blacklistface/update";
        public static final String DELBYID = "blacklistface/del";
        public static final String BATCHDEL = "blacklistface/batchdel";
        public static final String ADD = "blacklistface/add";
        public static final String BATCHADD = "blacklistface/batchadd";
        public static final String PAGE = "blacklistface/page";
    }

    public static final class BlacklistICCard {

        public static final String LISTALL = "blacklisticcard/listall";
        public static final String GETBYID = "blacklisticcard/get";
        public static final String UPDATEBYID = "blacklisticcard/update";
        public static final String DELBYID = "blacklisticcard/del";
        public static final String BATCHDEL = "blacklisticcard/batchdel";
        public static final String ADD = "blacklisticcard/add";
        public static final String BATCHADD = "blacklisticcard/batchadd";
        public static final String PAGE = "blacklisticcard/page";
    }

    public static final class GateEvent {

        public static final String LISTALL = "gateevent/listall";
        public static final String GETBYID = "gateevent/get";
        public static final String UPDATEBYID = "gateevent/update";
        public static final String DELBYID = "gateevent/del";
        public static final String BATCHDEL = "gateevent/batchdel";
        public static final String ADD = "gateevent/add";
        public static final String PAGE = "gateevent/page";
    }

    public static final class BusiServerApi {
        public static final String GETFILETOKEN = "busi/get_file_token";
        public static final String FILEUPLOAD = "busi/file_upload";
        public static final String FILEDOWNLOAD = "busi/file_download";
        public static final String SHAREFILE = "busi/share_file";
        public static final String REMOVEILE = "busi/rm_file";
        public static final String LONGCONNSTATE = "busi/long_conn_state";
        public static final String GETPTUIDCANDIDATELIST = "busi/get_ptuid_candidate_list";
        public static final String GETPTUIDLIST = "busi/get_ptuid_list";
        public static final String GETCONNSTATE = "busi/get_conn_state";
        public static final String REMOVEILEBYPTUID = "busi/rm_file_by_ptuid";
    }

    public static final class HardUsers {

        public static final String LISTALL = "hardusers/listall";
        public static final String GETBYID = "hardusers/get";
        public static final String UPDATEBYID = "hardusers/update";
        public static final String DELETE = "hardusers/del";
        public static final String BATCHDEL = "hardusers/batchdel";
        public static final String ADD = "hardusers/add";
        public static final String PAGE = "hardusers/page";
        public static final String GENUSERID = "hardusers/genuserid";
        public static final String SELECT = "hardusers/select";
    }
    public static final class DoorType {
        public static final String ADDDOORTYPE = "doorType/addType";
    }
}
