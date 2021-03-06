package com.goree.api.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Member {
    private long id;
    private String email;
    private String password;
    private String fullName;
    private String nickname;
    private String facebook;
    private String facebookUserId;
    private String twitter;
    private String kakaotalk;
    private String job;
    private int age;
    private Gender gender;
    private String phone;
    private List<Tag> tags;
    private String imagePath;

    public enum Gender {
        M, F
    }
    
    @Override
    public boolean equals(Object otherObj) {
        if (!(otherObj instanceof Member))
            return false;

        Member other = (Member)otherObj;
        return getEmail() == null ? other.getEmail() == null : getEmail().equals(other.getEmail()) &&
                getFullName() == null ? other.getFullName() == null : getFullName().equals(other.getFullName()) &&
                getNickname() == null ? other.getNickname() == null : getNickname().equals(other.getNickname());
    }
}
