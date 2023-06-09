package com.whomeow.whomeow.behavior;

import com.whomeow.whomeow.profile.Dog;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Behavior {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BEHAVIORKEY", nullable = false)
    private Long behaviorKey;

    @Column(name = "BEHAVIOR")
    private String behavior;
//    [scratch, turn, feetup]
//    private int action1;
//    private int action2;
//    private int action3;

    @Column(name = "CREATEDAT", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "DOGKEY", nullable = false)
    private Dog dog;

    @Builder
    Behavior(String behavior, Date createAt, Dog dog) {
        this.behavior = behavior;
        this.createdAt = createAt;
        this.dog = dog;
    }
}
