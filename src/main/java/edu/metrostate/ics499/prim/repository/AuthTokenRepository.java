package edu.metrostate.ics499.prim.repository;

import java.util.Date;

import edu.metrostate.ics499.prim.model.PersistentLogin;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * This class extends the Hibernate AbstractDao class to provide persistent storage and CRUD operations
 * for storing a user's auth token when selecting the remember me option during login. It does this
 * by implementing the methods of the PersistenTokenRepository Spring Security class
 */
@Repository("tokenRepositoryDao")
@Transactional
public class AuthTokenRepository extends AbstractDao<String, PersistentLogin>
        implements PersistentTokenRepository, IRepository {

    static final Logger logger = LoggerFactory.getLogger(AuthTokenRepository.class);

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        logger.info("Creating Token for user : {}", token.getUsername());

        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        persist(persistentLogin);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        logger.info("Fetch Token if any for seriesId : {}", seriesId);
        try {
            CriteriaBuilder builder = getCriteriaBuilder();
            CriteriaQuery<PersistentLogin> crit = builder.createQuery(PersistentLogin.class);
            Root<PersistentLogin> from = crit.from(PersistentLogin.class);
            Predicate clause = builder.equal(from.get("series"), seriesId);
            crit.select(from).where(clause);
            TypedQuery<PersistentLogin> query = getSession().createQuery(crit);
            PersistentLogin persistentLogin = query.getSingleResult();

            return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
                    persistentLogin.getToken(), persistentLogin.getLastUsed());
        } catch (Exception e) {
            logger.info("Token not found...");
            return null;
        }
    }

    @Override
    public void removeUserTokens(String username) {
        logger.info("Removing Token if any for user : {}", username);

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<PersistentLogin> crit = builder.createQuery(PersistentLogin.class);
        Root<PersistentLogin> from = crit.from(PersistentLogin.class);
        Predicate clause = builder.equal(from.get("username"), username);
        crit.select(from).where(clause);
        TypedQuery<PersistentLogin> query = getSession().createQuery(crit);
        try {

            PersistentLogin persistentLogin = query.getSingleResult();
            logger.info("rememberMe was selected");
            delete(persistentLogin);
        } catch (NoResultException ex){
            logger.info("No Token found for username : {}", username);
        }
    }

    @Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        logger.info("Updating Token for seriesId : {}", seriesId);

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<PersistentLogin> crit = builder.createQuery(PersistentLogin.class);
        Root<PersistentLogin> from = crit.from(PersistentLogin.class);
        Predicate clause = builder.equal(from.get("series"), seriesId);
        crit.select(from).where(clause);
        TypedQuery<PersistentLogin> query = getSession().createQuery(crit);

        try {
            PersistentLogin persistentLogin = query.getSingleResult();

            persistentLogin.setToken(tokenValue);
            persistentLogin.setLastUsed(lastUsed);
            update(persistentLogin);
        } catch (NoResultException ex) {
            logger.info("No Token found for seriesId : {}", seriesId);
        }
    }

}