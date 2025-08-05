package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

    /**
     * ✓ 역할과 구현을 분리
     * DiscountPolicy를 생성하는 메서드,
     * MemberRepository를 생성하는 메서드를
     * 별도로 분리한다.
     */

    /**
     * @Bean memberService -> new MemoryMemberRepository()
     * @Bean orderService -> new MemoryMemberRepository()
     * 생성자가 두 번 호출되면 두 개의 인스턴스가 생성될 것 같지만 실제로 스프링은 그렇지 않다.
     * 그렇다면 어떻게 한번만 생성하게 되는 것일까?
     */

    /**
     * [expected]
     * call AppConfig.memberService
     * call AppConfig.memberRepository
     * call AppConfig.memberRepository
     * call AppConfig.orderService
     * call AppConfig.memberRepository
     *
     * [executed]
     * call AppConfig.memberService
     * call AppConfig.memberRepository
     * call AppConfig.orderService
     *
     * 스프링이 싱글톤을 보장해주기 위해서 매번 생성자를 호출하지 않는다.
     * 별도의 방법을 통해서 생성자를 한번만 호출하도록 한다.
     */
}
