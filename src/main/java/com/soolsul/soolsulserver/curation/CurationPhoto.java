package com.soolsul.soolsulserver.curation;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationPhoto extends BaseEntity {

    @Column(nullable = false)
    private String curationId;

    private String originalFileName;
    private String uuidFileUrl;
    private String extension;

    public CurationPhoto(String curationId, String originalFileName, String uuidFileUrl, String extension) {
        this.curationId = curationId;
        this.originalFileName = originalFileName;
        this.uuidFileUrl = uuidFileUrl;
        this.extension = extension;
    }
}
