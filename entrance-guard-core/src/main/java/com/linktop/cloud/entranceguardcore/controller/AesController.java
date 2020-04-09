package com.linktop.cloud.entranceguardcore.controller;


import com.linktop.cloud.coder.Coder;
import com.linktop.cloud.coder.DESCoder;
import com.linktop.cloud.commonutils.VersionNumbers;
import com.linktop.cloud.file.FileUtil;
import com.linktop.cloud.jnaaes.JnaAES;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(VersionNumbers.NEW_SERVICE_VERSIONS)
public class AesController {

    @RequestMapping(value = "aes/encrypt", method = RequestMethod.GET)
    byte[] encrypt(@RequestParam("secret") String secret,
                   @RequestParam("content") String content) {
        byte[] result = null;
        JnaAES.decrypt(content, "\"Nz0_Xw4F{U-qhb|");
        return result;
    }

    @RequestMapping(value = "aes/decrypt", method = RequestMethod.GET)
    byte[] decrypt(@RequestParam("secret") String secret,
                   @RequestParam("content") String content,
                   HttpServletResponse response) {
        byte[] result = null;
        byte[] output = null;
        DESCoder.setALGORITHM("AES");
        try {
            DESCoder.setALGORITHM("AES");
            result = JnaAES.decrypt(new String(
                    Coder.decryptBASE64("4frR0QXAkXKaR0lP++TbYw==")),
                    "\"Nz0_Xw4F{U-qhb|");

        } catch (Exception e) {

        }

        result = FileUtil.file2Byte("/home/x/projects/vue/vue-admin/static/20190529111256.mp4");
        return result;
    }

}