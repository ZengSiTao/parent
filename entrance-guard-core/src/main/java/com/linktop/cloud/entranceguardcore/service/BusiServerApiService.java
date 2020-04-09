package com.linktop.cloud.entranceguardcore.service;

import com.linktop.cloud.coder.RSACoder;
import com.linktop.cloud.entranceguardcore.misc.MyMappingJackson2HttpMessageConverter;
import com.linktop.cloud.entranceguardmodel.*;
import com.linktop.cloud.entranceguardmodel.database.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusiServerApiService {
    private static Logger log = LoggerFactory.getLogger(BusiServerApiService.class);
    private String ctrlGetFile = "http://218.5.96.6:9300/ctrl/%s/get_file";
    private String fileTokenUrl = "http://218.5.96.6:9300/cmn/file_tk";
    private String fileUploadUrl = "http://218.5.96.6:9304/ul";
    private String fileDownloadUrl = "http://218.5.96.6:9304/dl";
    private String fileShareUrl = "http://218.5.96.6:9300/cmn/share_file";
    private String fileRmUrl = "http://218.5.96.6:9300/cmn/rm_file";
    private String connStateUrl = "http://218.5.96.6:9300/res/%s/con_state";
    private String myDevUrl = "http://218.5.96.6:9300/cmn/my_dev";
    private String fileRmByPtuidUrl = "http://218.5.96.6:9300/ctrl/%s/rm_file";

    @Autowired
    private DeviceService deviceService;
    @Value("${app.key}")
    private String appKey;
    @Value("${app.rsa_private_key_pkcs8}")
    private String private_pem;

    public GetFileUpDownTokenResult getFileUpDownToken(String fn, int m, String usage) {

        String signContent = "POST" + fileTokenUrl + "app_key=" + appKey;
        if(null != fn && !fn.isEmpty()) {
            signContent += ("fn=" + fn);
        }
        signContent += ("m=" + m);
        if(null != usage && !usage.isEmpty()) signContent += ("usage=" + usage);

        String signResult = "";
        try {
            signContent = URLEncoder.encode(signContent, "utf-8");
        } catch (Exception e) {

        }
        try {
            //String testPrivateKey = RSACoder.getPrivateKey(RSACoder.initKey());
            signResult = RSACoder.sign(signContent.getBytes(), private_pem);
        } catch (Exception e) {
            signResult = "";
        }
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> variable = new LinkedMultiValueMap<>();

        variable.set("m", String.format("%d", m));
        if(fn != null) {
            variable.set("fn", fn);
        }
        if(usage != null) {
            variable.set("usage", usage);
        }

        variable.set("_sign", signResult);
        variable.set("app_key", appKey);
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(variable, headers);
        ResponseEntity<GetFileUpDownTokenResult> ret = restTemplate.postForEntity(fileTokenUrl, entity, GetFileUpDownTokenResult.class);
        System.out.println("返回码："+ret.toString());
        System.out.println("返回body:"+ret.getBody());
        return ret.getBody();
    }


    public FileUploadResult fileUpload(String usage, MultipartFile file) {
        byte[] fileData;
        try {
            fileData = file.getBytes();
        } catch (Exception e) {
            log.info("file upload, Exception={}", e.getMessage());
            FileUploadResult fr = new FileUploadResult();
            fr.setFn(null);
            fr.setState(500);
            fr.setMsg(e.getMessage());
            return fr;
        }
        return  fileUpload(usage, fileData);
    }


    public FileUploadResult fileUpload(String usage, byte[] fileData) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> httpEntity = null;
        GetFileUpDownTokenResult tokenResult = null;
        //ResponseEntity<FileUploadResult> ret = null;
        HttpEntity<FileUploadResult> ret = null;
        try {
            httpEntity = new HttpEntity(fileData, headers);
            tokenResult = getFileUpDownToken(null, 0, usage);
            System.out.println(tokenResult.getState()+"-=-=-=-=-=-=-=-=-=----=-===================--------------------------");
            if(tokenResult.getState() != 0) {
                throw new Exception("Get UpDownToken failed!");
            }
            String url = fileUploadUrl + "?" + "tk=" +
                    URLEncoder.encode(tokenResult.getTk(), "utf-8") +
                    "&usage=" + usage + "&app_key=" + appKey;
            log.info("file upload, token={}", tokenResult.getTk());

            HttpEntity<?> entity = new HttpEntity<>(headers);
//            byte[] response = restTemplate.getForObject(url, byte[].class);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

            URI uri = builder.build(true).toUri();
            System.out.println(uri);

            ret = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, FileUploadResult.class);

            //ret = restTemplate.postForEntity(url, httpEntity, FileUploadResult.class);
            ret.getBody().setState(0);
            log.info("file upload, fn={}", ret.getBody().getFn());
        } catch (HttpClientErrorException e) {
            log.info("file upload, HttpClientErrorException e={}", e.getMessage());
            FileUploadResult fr = new FileUploadResult();
            fr.setFn(null);
            fr.setState(e.getRawStatusCode());
            fr.setMsg(e.getMessage());
            return fr;
        } catch (Exception e) {
            log.info("file upload, Exception e={}", e.getMessage());
            FileUploadResult fr = new FileUploadResult();
            fr.setFn(null);
            fr.setState(tokenResult.getState());
            fr.setMsg(e.getMessage());
            return fr;
        }
        return ret.getBody();
    }


    public FileDownloadResult fileDownload(String usage, String fn) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> list = new ArrayList<MediaType>();
        // 指定下载文件类型
        list.add(MediaType.APPLICATION_OCTET_STREAM);
        headers.setAccept(list);

        GetFileUpDownTokenResult tokenResult = null;
        FileDownloadResult ret = null;

        try {
            tokenResult = getFileUpDownToken(fn, 1, usage);
            log.info("file download, token={}", tokenResult.getTk());
            System.out.println(tokenResult.getState()+"-=-=-=-=-=-=-=-=-=----=-===================--------------------------123");
            if(tokenResult.getState() != 0) {
                throw new Exception("Get UpDownToken failed!");
            }
            String url = fileDownloadUrl + "?tk=" + URLEncoder.encode(tokenResult.getTk(), "utf-8")
                                        + "&fn=" + URLEncoder.encode(fn, "utf-8")
                                        + "&app_key=" + appKey
                                        + "&usage=" + usage;
            HttpEntity<?> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

            URI uri = builder.build(true).toUri();
            HttpEntity<byte[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, byte[].class);

            ret = new FileDownloadResult();
            ret.setBody(response.getBody());
            ret.setState(0);
        } catch (HttpClientErrorException e) {
            log.info("file download, HttpClientErrorException e={}", e.getMessage());
            FileDownloadResult fr = new FileDownloadResult();
            fr.setBody(null);
            fr.setState(e.getRawStatusCode());
            fr.setMsg(e.getMessage());
            return fr;
        } catch (Exception e) {
            log.info("file download, Exception e={}", e.getMessage());
            FileDownloadResult fr = new FileDownloadResult();
            fr.setBody(null);
            fr.setState(tokenResult.getState());
            fr.setMsg(e.getMessage());
            return fr;
        }

        return ret;
    }


    public BusiCommonResult shareFile(String fn, String inc, String exc) {

        if(inc == null && exc == null) {
            BusiCommonResult sr = new BusiCommonResult();
            sr.setState(400);
            return sr;
        }

        String signContent = "POST" + fileShareUrl + "app_key=" + appKey;
        if(null != exc && !exc.isEmpty()) signContent += ("exc=" + exc);
        signContent += ("fn=" + fn);
        if(null != inc && !inc.isEmpty()) signContent += ("inc=" + inc);

        String signResult = "";
        try {
            signContent = URLEncoder.encode(signContent, "utf-8");
        } catch (Exception e) {

        }
        try {
            signResult = RSACoder.sign(signContent.getBytes(), private_pem);
        } catch (Exception e) {
            signResult = "";
        }
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> variable = new LinkedMultiValueMap<>();
        try {
            variable.set("fn", fn);
            if(inc != null) {
                variable.set("inc", inc);
            }
            if(exc != null) {
                variable.set("exc", exc);
            }

            variable.set("_sign", signResult);
            variable.set("app_key", appKey);

            HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(variable, headers);
            ResponseEntity<BusiCommonResult> ret = restTemplate.postForEntity(fileShareUrl, entity, BusiCommonResult.class);
            return ret.getBody();
        } catch (Exception e) {
            BusiCommonResult sr = new BusiCommonResult();
            sr.setState(500);
            return sr;
        }
    }


    public MyDevResult myDev() {

        String signContent = "POST" + myDevUrl + "app_key=" + appKey;
        String signResult = "";
        try {
            signContent = URLEncoder.encode(signContent, "utf-8");
        } catch (Exception e) {

        }
        try {
            signResult = RSACoder.sign(signContent.getBytes(), private_pem);
        } catch (Exception e) {
            signResult = "";
        }
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> variable = new LinkedMultiValueMap<>();
        try {
            variable.set("_sign", signResult);
            variable.set("app_key", appKey);

            HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(variable, headers);
            ResponseEntity<MyDevResult> ret = restTemplate.postForEntity(myDevUrl, entity, MyDevResult.class);
            return ret.getBody();
        } catch (Exception e) {
            MyDevResult mr = new MyDevResult();
            mr.setState(500);
            return mr;
        }
    }


    public BusiCommonResult rmFile(String usage, String fns) {

        if(fns == null && usage == null) {
            BusiCommonResult sr = new BusiCommonResult();
            sr.setState(400);
            return sr;
        }

        String signContent = "POST" + fileRmUrl + "app_key=" + appKey;
        signContent += ("fns=" + fns);
        signContent += ("usage=" + usage);

        String signResult = "";
        try {
            signContent = URLEncoder.encode(signContent, "utf-8");
        } catch (Exception e) {

        }
        try {
            signResult = RSACoder.sign(signContent.getBytes(), private_pem);
        } catch (Exception e) {
            signResult = "";
        }
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> variable = new LinkedMultiValueMap<>();
        try {
            variable.set("usage", usage);
            variable.set("fns", fns);
            variable.set("_sign", signResult);
            variable.set("app_key", appKey);

            HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(variable, headers);
            ResponseEntity<BusiCommonResult> ret = restTemplate.postForEntity(fileShareUrl, entity, BusiCommonResult.class);
            return ret.getBody();
        } catch (Exception e) {
            BusiCommonResult sr = new BusiCommonResult();
            sr.setState(500);
            return sr;
        }
    }

    public BusiCommonResult getFile(String ptuId, String usage) {

        if(usage == null || ptuId == null) {
            ShareFileResult sr = new ShareFileResult();
            sr.setState(400);
            return sr;
        }
        String url = String.format(ctrlGetFile, ptuId);

        String signContent = "POST" + url + "app_key=" + appKey;
        signContent += ("usage=" + usage);

        String signResult = "";
        try {
            signContent = URLEncoder.encode(signContent, "utf-8");
        } catch (Exception e) {

        }
        try {
            signResult = RSACoder.sign(signContent.getBytes(), private_pem);
        } catch (Exception e) {
            signResult = "";
        }
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> variable = new LinkedMultiValueMap<>();
        try {
            variable.set("usage", usage);

            variable.set("_sign", signResult);
            variable.set("app_key", appKey);

            HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(variable, headers);
            ResponseEntity<BusiCommonResult> ret = restTemplate.postForEntity(url, entity, BusiCommonResult.class);
            return ret.getBody();
        } catch (Exception e) {
            BusiCommonResult sr = new BusiCommonResult();
            sr.setState(500);
            return sr;
        }
    }

    public LongConnStateResult longConnState() {
        LongConnStateResult lcsr = new LongConnStateResult();
        lcsr.setConnected(false);
        return lcsr;
    }


    public GetPtuidListResult getPtuidList() {
        GetPtuidListResult ret = new GetPtuidListResult();
        MyDevResult myDev = myDev();
        if(!StringUtils.isEmpty(myDev.getList())) {
            List<String> listPtuid = CollectionUtils.arrayToList(myDev.getList().split(","));
            ret.setListPtuid(new ArrayList<>(listPtuid));
        }
        return ret;
    }

    public GetPtuidListResult getPtuidCandidateList() {
        GetPtuidListResult ret = getPtuidList();
        List<String> listPtuid = ret.getListPtuid();
        List<Device> listDevice = deviceService.getList();
        for(Device d:listDevice){
             for(String ptuid:listPtuid) {
                if(ptuid.equalsIgnoreCase(d.getAliaId())) {
                    listPtuid.remove(ptuid);
                    break;
                }
            }
        }
        ret.setListPtuid(listPtuid);
        return ret;
    }

    public ConnStateResult getConnState(String ptuId) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        ConnStateResult ret = null;

        String url = String.format(connStateUrl, ptuId);

        try {
            String signContent = "GET" + url + "app_key=" + appKey;
            String signResult = "";
            try {
                signContent = URLEncoder.encode(signContent, "utf-8");
            } catch (Exception e) {

            }
            log.info("signContent:{}", signContent);
            try {
                signResult = RSACoder.sign(signContent.getBytes(), private_pem);
            } catch (Exception e) {
                signResult = "";
            }
            log.info("signResult:{}", signResult);
            url += "?app_key=" + appKey + "&_sign=" + URLEncoder.encode(signResult, "utf-8");;
            log.info("url:{}", url);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            URI uri = builder.build(true).toUri();
            HttpEntity<ConnStateResult> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    ConnStateResult.class);
            ret = response.getBody();
        } catch (HttpClientErrorException e) {
            log.info("get conn state, HttpClientErrorException e={}", e.getMessage());
            ConnStateResult fr = new ConnStateResult();
            fr.setState(e.getRawStatusCode());
            fr.setMsg(e.getMessage());
            return fr;
        } catch (Exception e) {
            log.info("get conn state, Exception e={}", e.getMessage());
            ConnStateResult fr = new ConnStateResult();
            fr.setState(500);
            fr.setMsg(e.getMessage());
            return fr;
        }

        return ret;
    }

    public BusiCommonResult rmFileByPtuid(String ptuId, String usage, String fns) {

        if(StringUtils.isEmpty(ptuId) ||
                StringUtils.isEmpty(usage) ||
                StringUtils.isEmpty(fns)) {
            BusiCommonResult br = new BusiCommonResult();
            br.setState(400);
            return br;
        }
        String url = String.format(fileRmByPtuidUrl, ptuId);

        String signContent = "POST" + url + "app_key=" + appKey;
        signContent += ("fns=" + fns);
        signContent += ("usage=" + usage);

        String signResult = "";
        try {
            signContent = URLEncoder.encode(signContent, "utf-8");
        } catch (Exception e) {

        }
        try {
            signResult = RSACoder.sign(signContent.getBytes(), private_pem);
        } catch (Exception e) {
            signResult = "";
        }
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> variable = new LinkedMultiValueMap<>();
        try {
            variable.set("fns", fns);
            variable.set("usage", usage);
            variable.set("_sign", signResult);
            variable.set("app_key", appKey);

            HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(variable, headers);
            ResponseEntity<BusiCommonResult> ret = restTemplate.postForEntity(url, entity, BusiCommonResult.class);
            return ret.getBody();
        } catch (Exception e) {
            log.info("rmFileByPtuid, Exception e={}", e.getMessage());
            BusiCommonResult br = new BusiCommonResult();
            br.setState(500);
            return br;
        }
    }
}
