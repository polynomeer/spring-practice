package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        /*
         * AppConfig에 @Configuration이 있으므로 무시하기 위함
         * @Configuration 에도 @Component가 포함되어있음
         *
         * 권장사항은 AppConfig를 루트경로에 두고
         * 해당 설정클래스의 위치에서부터 스캔하도록 하는 것이다.
         *
         * @SpringBootApplication 에는 @ComponentScan이 포함되어있어서
         * 자동으로 컴포넌트 스캔이 진행된다.
         *
         * @Component 이외에도 컴포넌트 스캔의 대상이 되는 것들:
         * @Controller, @Service, @Repository, @Configuration
        */
//        basePackages = "hello.core.member",
//        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
