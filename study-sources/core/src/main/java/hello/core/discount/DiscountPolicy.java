package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

    /**
     * @param member 적용 회원
     * @param price 판매 금액
     * @return 할인 적용 금액
     */
    int discount(Member member, int price);
}
