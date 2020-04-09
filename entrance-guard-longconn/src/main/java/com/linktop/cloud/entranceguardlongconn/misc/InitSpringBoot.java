package com.linktop.cloud.entranceguardlongconn.misc;


import com.linktop.cloud.entranceguardlongconn.busi.StartLongConn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2) // 通过order值的大小来决定启动的顺序
public class InitSpringBoot implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(InitSpringBoot.class);
    @Autowired
    StartLongConn startLongConn;
    @Override
    public void run(String... args) throws Exception {
        try {

            log.info("springboot初始化完成");
            startLongConn.start();
        } catch (Exception e) {
            log.error("springboot初始化异常", e);
        }
    }

}
