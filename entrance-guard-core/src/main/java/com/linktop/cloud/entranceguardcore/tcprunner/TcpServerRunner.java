package com.linktop.cloud.entranceguardcore.tcprunner;



import com.linktop.cloud.entranceguardcore.tcp.TcpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @创建人 baojielei
 * @创建时间 2020/3/20
 * @描述
 */

@Component
@Slf4j
public class TcpServerRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TcpServer.fun();
                } catch (InterruptedException e) {
//                    log.error("消费数据失败", e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
