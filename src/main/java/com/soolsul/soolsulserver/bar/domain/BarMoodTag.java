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
public class BarMoodTag {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String barId;

    @Column(nullable = false)
    private String moodId;

    @Column(nullable = false)
    private String name;

    private boolean isCuration;

    private Integer count;

    public BarMoodTag(String barId, String moodId, String name, boolean isCuration, Integer count) {
        this.barId = barId;
        this.moodId = moodId;
        this.name = name;
        this.isCuration = isCuration;
        this.count = count;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarMoodTag that = (BarMoodTag) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
