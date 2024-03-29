package com.cba.core.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "device_vendor")
@EntityListeners(AuditingEntityListener.class) // enable entity level auditing for create,modified attributes
@Data
@NoArgsConstructor
public class DeviceVendor implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private Status status;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "img", length = 65535)
    private String img;
    @Column(name = "created_by", nullable = false, length = 45)
    @CreatedBy
    private String createdBy;
    @Column(name = "modified_by", length = 45)
    @LastModifiedBy
    private String modifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, length = 19)
    @CreatedDate
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", length = 19)
    @LastModifiedDate
    private Date updatedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deviceVendor")
    private Set<DeviceModel> deviceModels = new HashSet<DeviceModel>(0);


    public DeviceVendor(Integer id) {
        this.id = id;
    }

}



