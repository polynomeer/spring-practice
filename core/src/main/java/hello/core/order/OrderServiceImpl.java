package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private DiscountPolicy discountPolicy;

//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    /**
     * ✓ DIP 위반
     * DIP를 잘 지키는 것 처럼 보이지만 위반하였다.
     * FixDiscountPolicy 에서 RateDiscountPolicy로 구현체를 변경하기 위해서
     * 객체를 생성하는 부분의 코드를 변경해야하므로 DIP를 위반한 것이다.
     *
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
}

/**
 * ✓ SRP(Single Responsibility Principle)
 * 할인에 대한 변경이 발생하면 할인 부분만 변경하면 된다. 주문에서 변경사항이 일어나지 않는다.
 * 따라서 이 부분은 단일 책임 원칙이 잘 지켜진 설계가 되었다고 할 수 있다.
 */
