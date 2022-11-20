package com.soolsul.soolsulserver.bar.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarPhoto {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarPhoto that = (BarPhoto) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
