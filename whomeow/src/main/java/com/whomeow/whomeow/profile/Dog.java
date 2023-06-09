package com.whomeow.whomeow.profile;

import com.whomeow.whomeow.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOGKEY", nullable = false)
    private Long dogKey;

    @Column(name = "DOGPHOTO")
    private String dogPhoto;
    @Column(name = "DOGNAME", nullable = false)
    private String dogName;
    @Column(name = "DOGAGE")
    private int dogAge;
    @Column(name = "DOGSEX", nullable = false)
    private String dogSex;
    @Column(name = "DOGWEIGHT")
    private float dogWeight;
    @Column(name = "DOGBREAD")
    private String dogBread;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "USERKEY", nullable = false)
    private User user;

    @Column(name = "CREATEDAT", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @Column(name = "UPDATEDAT", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @Builder
    Dog(String dogPhoto, String dogName, int dogAge, String dogSex, float dogWeight, String dogBread, User user, Date createdAt, Date updatedAt) {
        this.dogPhoto = dogPhoto;
        this.dogName = dogName;
        this.dogAge = dogAge;
        this.dogSex = dogSex;
        this.dogWeight = dogWeight;
        this.dogBread = dogBread;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Dog update(String dogPhoto, String dogName, int dogAge, String dogSex, float dogWeight, String dogBread, Date updatedAt) {
        this.dogPhoto = dogPhoto;
        this.dogName = dogName;
        this.dogAge = dogAge;
        this.dogSex = dogSex;
        this.dogWeight = dogWeight;
        this.dogBread = dogBread;
        this.updatedAt = updatedAt;

        return this;
    }
}
