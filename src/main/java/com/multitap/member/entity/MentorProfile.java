package com.multitap.member.entity;

import com.multitap.member.common.response.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MentorProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String mentoringField;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String jobExperience;

    @Builder
    public MentorProfile(Long id, String uuid, String mentoringField, Integer age, Gender gender, String jobExperience) {
        this.id = id;
        this.uuid = uuid;
        this.mentoringField = mentoringField;
        this.age = age;
        this.gender = gender;
        this.jobExperience = jobExperience;
    }
}
