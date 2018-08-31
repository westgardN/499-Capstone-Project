package edu.metrostate.ics499.prim.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * This is a JPA Entity class that maps the SentimentQueueItem object to the Sentiment Queue table used
 * for persistence. It is just a POJO with a default constructor that also implements the comparable
 * interface to support min priority queue.
 */
@Entity
@Table(name = "SENTIMENT_QUEUE")
public class SentimentQueueItem implements Serializable, Comparable<SentimentQueueItem> {
    public static final int DEFAULT_PRIORITY = 10;

    /**
     * The auto generated Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The time the interaction was placed in the queue
     */
    @Column(name = "created_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    /**
     * The interaction this response is for.
     */
    @ManyToOne
    @JoinColumn(name = "interaction_id", nullable = false)
    private Interaction interaction;

    /**
     * The priority of this interaction item. Interactions with lower priority numbers are processed before
     * interactions with higher priority numbers. Interactions with the same priority are processed based on
     * the time they entered the queue.
     */
    @Column(name = "priority", nullable = false)
    private int priority;

    /**
     * Indicates if the item has been processed
     */
    @Column(name = "processed", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean processed;

    /**
     * Creates a new SentimentQueueItem with default priority and no Interaction.
     */
    public SentimentQueueItem() {
        this(null);
    }

    /**
     * Creates a new SentimentQueueItem with default priority with
     * the specified Interaction.
     *
     * @param interaction the Interaction to associate with this queue item.
     */
    public SentimentQueueItem(Interaction interaction) {
        if (interaction != null) {
            this.interaction = interaction;
        }
        this.createdTime = new Date();
        this.priority = DEFAULT_PRIORITY;
        this.processed = false;
    }

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

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentimentQueueItem that = (SentimentQueueItem) o;
        return getPriority() == that.getPriority() &&
                Objects.equals(getCreatedTime(), that.getCreatedTime()) &&
                Objects.equals(getInteraction(), that.getInteraction());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCreatedTime(), getInteraction(), getPriority());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SentimentQueueItem{");
        sb.append("id=").append(id);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", interaction=").append(interaction);
        sb.append(", priority=").append(priority);
        sb.append(", processed=").append(processed);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * First the priority of the two items are compared. Lower priorities come
     * before those with higher priorities.
     *
     * If the priorities are equal, then the created time is compared and the one
     * that has the earliest date and time comes before the other.
     *
     * Both items must have valid dates or else an exception may be thrown.
     *
     * @param o the object to be compared. Must not be null.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(SentimentQueueItem o) {
        int result = 0; // assume they are equal

        if (o == null) {
            throw new NullPointerException("o cannot be null.");
        }

        if (getPriority() < o.getPriority()) {
            result = -1;
        } else if (getPriority() > o.getPriority()) {
            result = 1;
        }

        if (result == 0) {
            result = getCreatedTime().compareTo(o.getCreatedTime());
        }

        return result;
    }
}
