package com.soolsul.soolsulserver.domain.menu;

import com.soolsul.soolsulserver.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlcoholPhotos extends BaseEntity {

    @Column(nullable = false)
    private String alcoholId;

    private String originalFileName;
    private String uuidFileUrl;
    private String extension;

    public AlcoholPhotos(String alcoholId, String originalFileName, String uuidFileUrl, String extension) {
        this.alcoholId = alcoholId;
        this.originalFileName = originalFileName;
        this.uuidFileUrl = uuidFileUrl;
        this.extension = extension;
    }
}
