package com.sismics.docs.core.model.jpa;

import com.google.common.base.MoreObjects;
import com.sismics.docs.core.constant.StatusType;

import javax.persistence.*;
import java.util.Date;

/**
 * AppStatus Table
 * 
 */
@Entity
@Table(name = "ApplicantInfo")
public class AppInfo  {
    /**
     * App ID.
     */
    @Id
    @Column(name = "APP_ID", length = 36)
    private String id;
    
    /**
     * App status.
     */
    @Column(name = "ApplicantStatus", length = 36, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType status;

    /**
     * App username.
     */
    @Column(name = "ApplicantName", length = 36, nullable = false)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setReject() {
        this.status = StatusType.REJECTED;
    }

    public void setAccept() {
        this.status = StatusType.ACCEPTED;
    }

    public void setIP() {
        this.status = StatusType.INPROGRESS;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("status", status)
                .toString();
    }
}
