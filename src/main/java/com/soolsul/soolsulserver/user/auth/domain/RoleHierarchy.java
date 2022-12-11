package com.soolsul.soolsulserver.user.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ROLE_HIERARCHY")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"parentName", "roleHierarchy"})
public class RoleHierarchy implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(name = "child_name")
    private String childName;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_name", referencedColumnName = "child_name")
    private RoleHierarchy parentName;

    @OneToMany(mappedBy = "parentName", cascade = {CascadeType.ALL})
    private Set<RoleHierarchy> roleHierarchy = new HashSet<RoleHierarchy>();
}


