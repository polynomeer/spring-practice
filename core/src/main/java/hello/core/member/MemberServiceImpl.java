package hello.core.member;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * ✓ DIP 원칙을 잘 지키고 있는가?
     * MemberServiceImpl은 MemberService라는 인터페이스를 구현하므로 인터페이스에 의존한다.
     * 하지만 MemberRepositry는 구현체인 MemoryMemberRepository에 의존한다.
     * 즉, 만약 구현부분인 new MemoryMemberRepositry()를 다른 구현체로 변경하게 되면 코드의 변경이 일어나므로,
     * DIP 원칙을 위반하는 것이다.
     */

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
