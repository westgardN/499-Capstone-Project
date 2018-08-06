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

    /**
     * Gets id
     *
     * @return value of id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id to the specified value in id
     *
     * @param id the new value for id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets createdTime
     *
     * @return value of createdTime
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets createdTime to the specified value in createdTime
     *
     * @param createdTime the new value for createdTime
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Gets description
     *
     * @return value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description to the specified value in description
     *
     * @param description the new value for description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets fromId
     *
     * @return value of fromId
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * Sets fromId to the specified value in fromId
     *
     * @param fromId the new value for fromId
     */
    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    /**
     * Gets fromName
     *
     * @return value of fromName
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * Sets fromName to the specified value in fromName
     *
     * @param fromName the new value for fromName
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * Gets messageId
     *
     * @return value of messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Sets messageId to the specified value in messageId
     *
     * @param messageId the new value for messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * Gets messageLink
     *
     * @return value of messageLink
     */
    public String getMessageLink() {
        return messageLink;
    }

    /**
     * Sets messageLink to the specified value in messageLink
     *
     * @param messageLink the new value for messageLink
     */
    public void setMessageLink(String messageLink) {
        this.messageLink = messageLink;
    }

    /**
     * Gets message
     *
     * @return value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message to the specified value in message
     *
     * @param message the new value for message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets sentiment
     *
     * @return value of sentiment
     */
    public Integer getSentiment() {
        return sentiment;
    }

    /**
     * Sets sentiment to the specified value in sentiment
     *
     * @param sentiment the new value for sentiment
     */
    public void setSentiment(Integer sentiment) {
        this.sentiment = sentiment;
    }

    /**
     * Gets socialNetwork
     *
     * @return value of socialNetwork
     */
    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    /**
     * Sets socialNetwork to the specified value in socialNetwork
     *
     * @param socialNetwork the new value for socialNetwork
     */
    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    /**
     * Gets type
     *
     * @return value of type
     */
    public InteractionType getType() {
        return type;
    }

    /**
     * Sets type to the specified value in type
     *
     * @param type the new value for type
     */
    public void setType(InteractionType type) {
        this.type = type;
    }

    /**
     * Gets state
     *
     * @return value of state
     */
    public InteractionState getState() {
        return state;
    }

    /**
     * Sets state to the specified value in state
     *
     * @param state the new value for state
     */
    public void setState(InteractionState state) {
        this.state = state;
    }

    /**
     * Gets flag
     *
     * @return value of flag
     */
    public InteractionFlag getFlag() {
        return flag;
    }

    /**
     * Sets flag to the specified value in flag
     *
     * @param flag the new value for flag
     */
    public void setFlag(InteractionFlag flag) {
        this.flag = flag;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((socialNetwork == null) ? 0 : socialNetwork.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Interaction)) {
			return false;
		}
		Interaction other = (Interaction) obj;
		if (messageId == null) {
			if (other.messageId != null) {
				return false;
			}
		} else if (!messageId.equals(other.messageId)) {
			return false;
		}
		if (socialNetwork != other.socialNetwork) {
			return false;
		}
		return true;
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
