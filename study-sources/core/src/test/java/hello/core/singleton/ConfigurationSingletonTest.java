package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = memberService.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        /**
         * [expected]
         * bean = class.hello.core.AppConfig
         *
         * [executed]
         * bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$11f650a7
         *
         * 이와 같이 출력 되는것은 스프링이 AppConfig를 그대로 생성하는게 아니라
         * 이름이 같은(AppConfig를 상속하는) 임의의 다른 클래스를 직접 생성해준다.
         * 이를 통해 싱글톤이 가능하게 해주는 것이다.
         *
         * @Configuration 을 빼면 싱글톤이 깨진다.
         * 이 애노테이션을 붙여주어야 CGLIB을 이용하여 클래스를 만들고
         * @Bean 이 싱글톤으로 생성됨을 보장해준다.
         */
    }
}
