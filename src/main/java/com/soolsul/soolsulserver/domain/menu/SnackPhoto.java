package com.soolsul.soolsulserver.domain.menu;

import com.soolsul.soolsulserver.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnackPhoto extends BaseEntity {

    @Column(nullable = false)
    private String snackId;

    private String originalFileName;
    private String uuidFileUrl;
    private String extension;

    public SnackPhoto(String snackId, String originalFileName, String uuidFileUrl, String extension) {
        this.snackId = snackId;
        this.originalFileName = originalFileName;
        this.uuidFileUrl = uuidFileUrl;
        this.extension = extension;
    }
}
