package edu.metrostate.ics499.prim.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * This is a JPA Entity class that maps the DataRefreshRequest object to the DataRefreshRequest table used
 * for persistence. It is just a POJO with a default constructor.
 */
@Entity
@Table(name = "DATA_REFRESH_REQUEST")
public class DataRefreshRequest implements Serializable {

    /**
     * The auto generated Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The time the refresh request was received by the system.
     */
    @Column(name = "created_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    /**
     * Who requested the refresh? If user initiated, it is their ssoId. If system initiated,
     * the field is null.
     */
    @Column(name = "requested_by", length = 128, nullable = true)
    private String requestedBy;

    /**
     * The time the system started processing the refresh request.
     * If this field is null, then the system hasn't started processing the request yet.
     */
    @Column(name = "start_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    /**
     * The time the system finished processing the refresh request. If this field
     * is null, then the system hasn't started and / or finished processing the request yet.
     */
    @Column(name = "finish_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishTime;

    /**
     * The type of refresh request this is for.
     */
    @Column(name = "type", length = 128, nullable = true)
    @Enumerated(EnumType.STRING)
    private DataRefreshRequestType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public DataRefreshRequestType getType() {
        return type;
    }

    public void setType(DataRefreshRequestType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataRefreshRequest that = (DataRefreshRequest) o;
        return Objects.equals(getCreatedTime(), that.getCreatedTime()) &&
                Objects.equals(getRequestedBy(), that.getRequestedBy());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCreatedTime(), getRequestedBy());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DataRefreshRequest{");
        sb.append("id=").append(id);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", requestedBy='").append(requestedBy).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", finishTime=").append(finishTime);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
