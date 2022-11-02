package com.soolsul.soolsulserver.domain.curation;

import com.soolsul.soolsulserver.domain.common.OwnerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationPhotos extends OwnerEntity {

    @Column(nullable = false)
    private String curationId;

    private String originalFileName;
    private String uuidFileUrl;
    private String extension;

    public CurationPhotos(String curationId, String originalFileName, String uuidFileUrl, String extension) {
        this.curationId = curationId;
        this.originalFileName = originalFileName;
        this.uuidFileUrl = uuidFileUrl;
        this.extension = extension;
    }
}
