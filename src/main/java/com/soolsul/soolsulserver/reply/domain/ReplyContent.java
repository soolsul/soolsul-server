package com.soolsul.soolsulserver.reply.domain;

import com.soolsul.soolsulserver.reply.exception.InvalidReplyContentException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyContent {

    private static final int LIMIT_LENGTH = 500;

    @Column(length = LIMIT_LENGTH)
    private String reviewContent;

    public void modify(String contents) {
        this.reviewContent = contents;
    }

    public ReplyContent(String reviewContent) {
        if (Objects.isNull(reviewContent)
                || reviewContent.isBlank()
                || reviewContent.length() > LIMIT_LENGTH) {
            throw new InvalidReplyContentException();
        }

        this.reviewContent = reviewContent;
    }
}
