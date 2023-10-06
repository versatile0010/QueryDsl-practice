package study.querydsl.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.*;

@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    //@OneToOne(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    //private MemberLog memberLog;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_log_id", referencedColumnName = "member_log_id")
    private MemberLog memberLog;



    public Member(String username) {
        this(username, 0);
    }

    public Member(String username, int age) {
        this(username, age, null);
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
    public void addLog() {
        log.info("[create member_log]");
        this.memberLog = MemberLog.of(this);
    }
}
