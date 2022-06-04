package hello.proxy;

import hello.proxy.config.AppV1Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppV1Config.class) // 수동으로 컴포넌트 스캔 대상 지정
@SpringBootApplication(scanBasePackages = "hello.proxy.app") // 주의: 자동 컴포너트 스캔의 범위를 제한
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
