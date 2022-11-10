package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarPhoto extends BaseEntity {

    @Column(nullable = false)
    private String barId;

    private String originalFileName;

    private String uuidFileUrl;

    private String extension;

    public BarPhoto(String barId, String originalFileName, String uuidFileUrl, String extension) {
        this.barId = barId;
        this.originalFileName = originalFileName;
        this.uuidFileUrl = uuidFileUrl;
        this.extension = extension;
    }
}
