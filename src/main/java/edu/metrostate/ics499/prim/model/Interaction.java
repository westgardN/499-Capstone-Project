package edu.metrostate.ics499.prim.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

/**
 * This is a JPA Entity class that maps the Interaction object to the Interaction table used
 * for persistence. It is just a POJO with a default constructor.
 */
@Entity
@Table(name = "INTERACTION")
public class Interaction implements Serializable {

    /**
     * The auto generated Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The time the social media interaction was created on the
     * social network. If this isn't provided it is the date and time
     * the interaction was pulled in to PRIM.
     */
    @Column(name = "created_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    /**
     * The description as provided by the social network. May be null
     * if the social network doesn't provide this information.
     */
    @Column(name = "description", length = 512, nullable = true)
    private String description;

    /**
     * The id of the user from the social network this interaction was
     * retrieved from. May be null if the social network doesn't provide this.
     */
    @Column(name = "from_id", length = 128, nullable = true)
    private String fromId;

    /**
     * The name of the user from the social network this interaction was
     * retrieved from. May be null if the social network doesn't provide this.
     */
    @Column(name = "from_name", length = 128, nullable = true)
    private String fromName;

    /**
     * The message id from the social network this interaction was
     * retrieved from. May be null if the social network doesn't provide this.
     */
    @Column(name = "message_id", length = 128, nullable = true)
    private String messageId;

    /**
     * The message link from the social network this interaction was
     * retrieved from. May be null if the social network doesn't provide this.
     */
    @Column(name = "message_link", length = 512, nullable = true)
    private String messageLink;

    /**
     * The message from the social network this interaction was
     * retrieved from. May be null if the social network doesn't provide this.
     */
    @Column(name = "message", nullable = true)
    private String message;

    /**
     * The sentiment score of the message that was retrieved from the social
     * network site. The score is < 0 if there is no message to process. The
     * score is null if there is a message but it hasn't been processed yet.
     */
    @Column(name = "sentiment", nullable = true)
    private Integer sentiment;

    /**
     * The social network this interaction was retrieved from.
     */
    @Column(name = "social_network", length = 128, nullable = true)
    @Enumerated(EnumType.STRING)
    private SocialNetwork socialNetwork;

    /**
     * The type of this interaction. Types are of InteractionType. Some of the types are
     * Post, Photo, Tagged, Tweet, Mention, and Image. Mainly used for determining which interactions
     * to process for sentiment and for use in the view with displaying the interaction.
     */
    @Column(name = "type", length = 128, nullable = true)
    @Enumerated(EnumType.STRING)
    private InteractionType type;

    /**
     * The state this interaction is currently in. The state indicates if this interaction is open, closed,
     * deferred, or deleted.
     *
     * @see edu.metrostate.ics499.prim.model.InteractionState
     */
    @Column(name = "state", length = 128, nullable = false)
    @Enumerated(EnumType.STRING)
    private InteractionState state = InteractionState.OPEN;

    /**
     * The flag as determined by the sentiment score. The flag is used to indicate
     * the heat level of the sentiment score.
     *
     * @see edu.metrostate.ics499.prim.model.InteractionFlag
     */
    @Column(name = "flag", length = 128, nullable = true)
    @Enumerated(EnumType.STRING)
    private InteractionFlag flag;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageLink() {
        return messageLink;
    }

    public void setMessageLink(String messageLink) {
        this.messageLink = messageLink;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSentiment() {
        return sentiment;
    }

    public void setSentiment(Integer sentiment) {
        this.sentiment = sentiment;
    }

    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public InteractionType getType() {
        return type;
    }

    public void setType(InteractionType type) {
        this.type = type;
    }

    public InteractionState getState() {
        return state;
    }

    public void setState(InteractionState state) {
        this.state = state;
    }

    public InteractionFlag getFlag() {
        return flag;
    }

    public void setFlag(InteractionFlag flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interaction that = (Interaction) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Interaction{");
        sb.append("id=").append(id);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", description='").append(description).append('\'');
        sb.append(", fromId='").append(fromId).append('\'');
        sb.append(", fromName='").append(fromName).append('\'');
        sb.append(", messageId='").append(messageId).append('\'');
        sb.append(", messageLink='").append(messageLink).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", sentiment=").append(sentiment);
        sb.append(", socialNetwork='").append(socialNetwork).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", flag='").append(flag).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
