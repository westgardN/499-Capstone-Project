package edu.metrostate.ics499.prim.model;

import javax.persistence.*;
import java.util.Date;

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
    @Column(name = "message")
    private String message;

    /**
     * The type of response.
     */
    @Column(name = "type")
    private String type;

    /**
     * The state of the response.
     */
    @Column(name = "state")
    private String state;
}
