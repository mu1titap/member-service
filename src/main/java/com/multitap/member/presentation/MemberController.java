package com.multitap.member.presentation;

import com.multitap.member.application.HashtagService;
import com.multitap.member.application.MemberProfileService;
import com.multitap.member.application.ReactionService;
import com.multitap.member.common.response.BaseResponse;
import com.multitap.member.dto.in.HashtagIdRequestDto;
import com.multitap.member.dto.in.MenteeProfileRequestDto;
import com.multitap.member.dto.in.MentorProfileRequestDto;
import com.multitap.member.dto.in.ReactionRequestDto;
import com.multitap.member.infrastructure.kafka.producer.KafkaProducerService;
import com.multitap.member.infrastructure.kafka.producer.ProfileImageDto;
import com.multitap.member.vo.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "마이페이지 관리 API", description = "마이페이지 관련 API endpoints")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final ReactionService reactionService;
    private final HashtagService hashtagService;
    private final MemberProfileService memberProfileService;
    private final KafkaProducerService kafkaProducerService;

    @Operation(summary = "특정 회원에 대한 반응(좋아요/블랙리스트) 등록", description = "특정 회원에 대한 반응(좋아요 또는 싫어요)을 등록합니다.")
    @PostMapping("/{targetUuid}/reaction")
    public BaseResponse<Void> addReaction(@RequestBody ReactionRequestVo reactionRequestVo, @PathVariable("targetUuid") String targetUuid, @RequestHeader("Uuid") String uuid) {
        reactionService.toggleReaction(ReactionRequestDto.from(reactionRequestVo, targetUuid, uuid));
        return new BaseResponse<>();
    }

    @Operation(summary = "회원 해시태그 등록, 수정", description = "자신이 원하는 해시태그를 등록 또는 수정합니다.")
    @PostMapping("/hashtag")
    public BaseResponse<Void> addHashtag(@RequestHeader("Uuid") String uuid, @RequestBody List<HashtagIdRequestVo> hashtagIdRequestVo) {
        hashtagService.addOrUpdateHashtags(HashtagIdRequestDto.from(hashtagIdRequestVo, uuid), uuid);
        return new BaseResponse<>();
    }

    @Operation(summary = "멘토 프로필 등록", description = "멘토의 프로필을 등록합니다.")
    @PostMapping("/mentor/profile")
    public BaseResponse<Void> addMentorProfile(@RequestHeader("Uuid") String uuid, @RequestBody MentorProfileRequestVo mentorProfileRequestVo) {
        memberProfileService.addMentorProfile(MentorProfileRequestDto.from(mentorProfileRequestVo, uuid));
        return new BaseResponse<>();
    }

    @Operation(summary = "멘티 프로필 등록", description = "멘티의 프로필을 등록합니다.")
    @PostMapping("/mentee/profile")
    public BaseResponse<Void> addMenteeProfile(@RequestHeader("Uuid") String uuid, @RequestBody MenteeProfileRequestVo menteeProfileRequestVo) {
        memberProfileService.addMenteeProfile(MenteeProfileRequestDto.from(menteeProfileRequestVo, uuid));

        return new BaseResponse<>();
    }

    @Operation(summary = "멘토 프로필 수정", description = "멘토의 프로필을 수정합니다.")
    @PutMapping("/mentor/profile")
    public BaseResponse<Void> changeMentorProfile(@RequestHeader("Uuid") String uuid, @RequestBody MentorProfileRequestVo mentorProfileRequestVo) {
        memberProfileService.changeMentorProfile(MentorProfileRequestDto.from(mentorProfileRequestVo, uuid));


        return new BaseResponse<>();
    }

    @Operation(summary = "멘티 프로필 수정", description = "멘티의 프로필을 수정합니다.")
    @PutMapping("/mentee/profile")
    public BaseResponse<Void> changeMenteeProfile(@RequestHeader("Uuid") String uuid, @RequestBody MenteeProfileRequestVo menteeProfileRequestVo) {
        memberProfileService.changeMenteeProfile(MenteeProfileRequestDto.from(menteeProfileRequestVo, uuid));
        return new BaseResponse<>();
    }

    @Operation(summary = "회원 프로필 이미지 등록", description = "회원의 프로필 이미지를 등록합니다.")
    @PostMapping("/profile-image")
    public BaseResponse<Void> addProfileImage(@RequestBody ProfileImageVo profileImageVo) {
        kafkaProducerService.sendCreateProfileImageUrl(ProfileImageDto.from(profileImageVo));
        return new BaseResponse<>();
    }


}


