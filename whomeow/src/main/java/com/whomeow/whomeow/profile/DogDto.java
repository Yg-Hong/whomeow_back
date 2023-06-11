package com.whomeow.whomeow.profile;

import com.whomeow.whomeow.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.text.DateFormat;
import java.util.Date;

@Data
public class DogDto {
    private Long DogKey;

    private String dogPhoto;
    @NotBlank
    private String dogName;
    private int dogAge;
    @NotBlank
    private String dogSex;
    private float dogWeight;

    private String dogBreed;

    @Builder
    public DogDto(String dogPhoto, String dogName, int dogAge, String dogSex, float dogWeight, String dogBreed) {
        this.dogPhoto = dogPhoto;
        this.dogName = dogName;
        this.dogAge = dogAge;
        this.dogSex = dogSex;
        this.dogWeight = dogWeight;
        this.dogBreed = dogBreed;
    }

    Dog toEntity() {
        return Dog.builder()
                .dogPhoto(dogPhoto)
                .dogName(dogName)
                .dogAge(dogAge)
                .dogSex(dogSex)
                .dogWeight(dogWeight)
                .dogBreed(dogBreed)
                .build();
    }

}
