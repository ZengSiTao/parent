package com.linktop.cloud.entranceguardcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.linktop.cloud.entranceguardclient")
@ComponentScan(basePackages = {"com.linktop.*", "com.cloud.*"})
@ServletComponentScan
public class EntranceGuardCoreApplication {

	public static void main(String[] args) {
		//PropertyConfigurator.configure("log4j.properties");
/*        byte[] mp4bytes = FileUtil.file2Byte("/home/x/projects/linktop/data/1.mp4");
        try {
            String mp4B64 = Coder.encryptBASE64(mp4bytes);
            FileUtil.byte2File(mp4B64.getBytes(), "/home/x/projects/linktop/data/", "b64.dat");
        } catch (Exception e) {

        }*/
		System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		SpringApplication.run(EntranceGuardCoreApplication.class, args);
	}

}
