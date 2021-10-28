package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
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

    /*
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    */

    /*
     * 수동 빈이 자동 빈(@Component)보다 우선권을 갖는다.
     * 하지만 수동과 자동이 섞여있으면 잡기어려운 버그가 발생할 수 있다.
     * 그래서 최근 스프링부트에서는 애매한 것을 아예 에러로 던져버린다.
     * 오버라이딩을 기본적으로 차단함으로써 막고자한다.
     */

}
