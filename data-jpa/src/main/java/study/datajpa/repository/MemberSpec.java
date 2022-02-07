package study.datajpa.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.criteria.*;

public class MemberSpec {

    public static Specification<Member> teamName(final String teamName) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(teamName)) {
                return null;
            }
            Join<Member, Team> team = root.join("team", JoinType.INNER);
            return criteriaBuilder.equal(team.get("name"), teamName);
        };
    }

    public static Specification<Member> username(final String username) {
        return (root, query, builder) ->
                builder.equal(root.get("username"), username);

    }
}

