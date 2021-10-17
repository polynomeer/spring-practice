package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

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
