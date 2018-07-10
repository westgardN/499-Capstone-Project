package edu.metrostate.ics499.prim.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * This is a JPA Entity class that maps the InteractionResponse object to the Interaction Response table used
 * for persistence. It is just a POJO with a default constructor.
 */
@Entity
@Table(name = "INTERACTION_RESPONSE")
public class InteractionResponse {

    /**
     * The auto generated Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The time the interaction was response was responded to.
     */
    @Column(name = "response_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseTime;

    /**
     * The interaction this response is for.
     */
    @ManyToOne
    @JoinColumn(name = "response_to", nullable = false)
    private Interaction responseTo;

    /**
     * The user that responded to the interaction
     */
    @ManyToOne
    @JoinColumn(name = "response_by", nullable = false)
    private User responseBy;

    /**
     * The message of the response; which may be null or empty
     * if the response is flag for follow-up
     */
    @Column(name = "message", nullable = true)
    private String message;

    /**
     * The type of response.
     */
    @Column(name = "type", length = 128, nullable = false)
    @Enumerated(EnumType.STRING)
    private InteractionResponseType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public Interaction getResponseTo() {
        return responseTo;
    }

    public void setResponseTo(Interaction responseTo) {
        this.responseTo = responseTo;
    }

    public User getResponseBy() {
        return responseBy;
    }

    public void setResponseBy(User responseBy) {
        this.responseBy = responseBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InteractionResponseType getType() {
        return type;
    }

    public void setType(InteractionResponseType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionResponse that = (InteractionResponse) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InteractionResponse{");
        sb.append("id=").append(id);
        sb.append(", responseTime=").append(responseTime);
        sb.append(", responseTo=").append(responseTo);
        sb.append(", responseBy=").append(responseBy);
        sb.append(", message='").append(message).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
