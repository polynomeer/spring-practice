package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    1. 필드주입 -> 테스트하기 까다롭고, 안티패턴이라서 권장하지 않는다.
//    @Autowired private final MemberRepository memberRepository;
//    @Autowired private final DiscountPolicy discountPolicy;

//    2. 생성자 주입 -> 여러가지로 편리하므로 주로 사용한다.
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

//    3. 수정자 주입 -> 웬만하면 잘 사용하지 않는다.
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

//    4. 일반 메서드 주입 -> 수정자 주입과 큰 차이가 없다.
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    /*
     * ✓ 관심사의 분리
     * 애플리케이션이 하나의 공연이라면 OrderServiceImpl에서 직접 new RixDiscountPolicy() 생성하는 것은
     * 로미오와 줄리엣 공연의 로미오 역할 배우가 직접 줄리엣 역할의 배우를 섭외하는 것과 같다.
     * 즉, 관심사가 잘 분리되지 않아서 본인의 역할(연기)만 수행하지않고, 연출자의 역할(섭외)까지 담당하게 된 것이다.
     * 따라서 이를 분리하도록 별도의 설정자를 도입해야한다. -> AppConfig
     *
     * ✓ AppConfig 추가
     * AppConfig에서 구현체를 직접 설정해줌으로써 이 코드에서는 구현부분을 신경쓰지 않아도 된다.
     * MemberRepository가 MemoryMemberRepository인지 DataBaseMemberRepository인지 모르고,
     * DiscountPolicy가 FixDiscountPolicy인지 RateDiscountPolicy인지 모른다.
     * 이 모든 설정을 외부의 설정자(AppConfig)에 의해 관리한다.
     */
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    /*
     * ✓ DIP 위반
     * DIP를 잘 지키는 것 처럼 보이지만 위반하였다.
     * FixDiscountPolicy 에서 RateDiscountPolicy로 구현체를 변경하기 위해서
     * 객체를 생성하는 부분의 코드를 변경해야하므로 DIP를 위반한 것이다.
     * <p>
     * 그렇다면 구현체를 코드에 직접 작성하지않고 어떻게 교체할 수 있을까?
     * 그냥 필드만 선언해서는 NullPointerException만 발생할 뿐이다.
     * 결국 누군가가 대신 구현체를 넣어주는 일을 해주어야 DIP를 지킬 수 있을 것이다.
     */

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

/*
 * ✓ SRP(Single Responsibility Principle)
 * 할인에 대한 변경이 발생하면 할인 부분만 변경하면 된다. 주문에서 변경사항이 일어나지 않는다.
 * 따라서 이 부분은 단일 책임 원칙이 잘 지켜진 설계가 되었다고 할 수 있다.
 */
