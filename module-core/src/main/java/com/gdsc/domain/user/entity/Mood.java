package com.gdsc.domain.user.entity;

import lombok.Getter;

@Getter
public enum Mood {
    CALM("https://storage.googleapis.com/dofarming/CALM.png",1),
    DROWSY("https://storage.googleapis.com/dofarming/DROWSY.png",2),
    HAPPY("https://storage.googleapis.com/dofarming/HAPPY.png",3),
    PROUD("https://storage.googleapis.com/dofarming/PROUD.png",4),
    EXCITED("https://storage.googleapis.com/dofarming/EXCITED.png",5),
    SAD("https://storage.googleapis.com/dofarming/SAD.png",6),
    NERVOUS("https://storage.googleapis.com/dofarming/NERVOUS.png",7),
    TIRED("https://storage.googleapis.com/dofarming/TIRED.png",8),
    ANGRY("https://storage.googleapis.com/dofarming/ANGRY.png",9);

    private final String imgUrl;
    private final int code;

    Mood(String imgUrl, int code) {
        this.imgUrl = imgUrl;
        this.code = code;
    }
}
