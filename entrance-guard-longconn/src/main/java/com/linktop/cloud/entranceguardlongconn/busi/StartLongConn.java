package com.linktop.cloud.entranceguardlongconn.busi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StartLongConn {
    private static Logger log = LoggerFactory.getLogger(StartLongConn.class);
    @Autowired
    private CmdSocket cmdSocket;
    @Autowired
    private Constans constans;
    private boolean bConnected = false;

    public void start() {
        try {
            cmdSocket.connect(constans.getHost(), constans.getPort());
            bConnected = true;
        } catch (Exception e) {
            bConnected = false;
            System.out.println("Connect server " +
                    constans.getHost() + ":" +
                    constans.getPort() + " Exception: " + e);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        if(!bConnected) {
                            cmdSocket.connect(constans.getHost(), constans.getPort());
                            bConnected = true;
                        }
                        cmdSocket.knock(0, constans.getKey());
                        cmdSocket.readData();
                        log.error("longconn readData exit");
                        bConnected = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                        bConnected = false;
                        try {
                            Thread.sleep(3000);
                            log.error("connect {}:{}",
                                    constans.getHost(),
                                    constans.getPort());
                            cmdSocket.connect(constans.getHost(), constans.getPort());
                            bConnected = true;
                        } catch (Exception e2) {
                            bConnected = false;
                            log.error("Connect server " +
                                    constans.getHost() + ":" +
                                    constans.getPort() + " Exception: " + e2);
                        }

                    }
                }
            }
        }).start();
    }
}
