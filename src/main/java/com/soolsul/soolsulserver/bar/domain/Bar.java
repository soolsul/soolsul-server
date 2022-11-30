package com.soolsul.soolsulserver.bar.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import com.soolsul.soolsulserver.region.domain.Location;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bar extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String regionId;

    private String barCategoryId;

    private String name;

    private String description;

    private String phoneNumber;

    @Embedded
    private StreetNameAddress streetNameAddress;

    @Embedded
    private Location location;

    public Bar(
            String regionId,
            String barCategoryId,
            String name,
            String description,
            String phoneNumber,
            StreetNameAddress streetNameAddress,
            Location location
    ) {
        this.regionId = regionId;
        this.barCategoryId = barCategoryId;
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.streetNameAddress = streetNameAddress;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar that = (Bar) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
