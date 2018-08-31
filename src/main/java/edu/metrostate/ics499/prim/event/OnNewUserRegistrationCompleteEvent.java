package edu.metrostate.ics499.prim.event;

import edu.metrostate.ics499.prim.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;
import java.util.Objects;

public class OnNewUserRegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private Locale locale;
    private String appUrl;

    /**
     * Creates a new event instance for the specified user, locale, and app URL.
     *
     * @param user the newly created User
     * @param locale the user's locale
     * @param appUrl the application's URL
     */
    public OnNewUserRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    /**
     * Gets user
     *
     * @return value of user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user to the specified value in user
     *
     * @param user the new value for user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets locale
     *
     * @return value of locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets locale to the specified value in locale
     *
     * @param locale the new value for locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Gets appUrl
     *
     * @return value of appUrl
     */
    public String getAppUrl() {
        return appUrl;
    }

    /**
     * Sets appUrl to the specified value in appUrl
     *
     * @param appUrl the new value for appUrl
     */
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OnNewUserRegistrationCompleteEvent{");
        sb.append("user=").append(user);
        sb.append(", locale=").append(locale);
        sb.append(", appUrl='").append(appUrl).append('\'');
        sb.append(", source=").append(source);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnNewUserRegistrationCompleteEvent that = (OnNewUserRegistrationCompleteEvent) o;
        return Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getLocale(), that.getLocale()) &&
                Objects.equals(getAppUrl(), that.getAppUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getLocale(), getAppUrl());
    }
}
