package edu.metrostate.ics499.prim.service;

import java.util.List;
import java.util.Objects;

import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The UserServiceImpl implements the UserService
 * interface for easily working with the Users.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Finds and returns a User based on the primary key. Returns null if no user is found.
     *
     * @param id the id of the User to retrieve.
     * @return a User based on the primary key. Returns null if no user is found.
     */
    @Override
    public User findById(int id) {
        return dao.findById(id);
    }

    /**
     * Finds and returns a User based on the username. Returns null if no user is found.
     *
     * @param username the username of the user.
     * @return a User based on the username. Returns null if no user is found.
     */
    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    /**
     * Finds and returns a User based on the email. Returns null if no user is found.
     *
     * @param email the email of the user.
     * @return a User based on the email. Returns null if no user is found.
     */
    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    /**
     * Finds and returns a User based on the SSO Id. Returns null if no user is found.
     *
     * @param ssoId the ssoId of the user.
     * @return a User based on the SSO Id. Returns null if no user is found.
     */
    @Override
    public User findBySsoId(String ssoId) {
        return dao.findBySsoId(ssoId);
    }

    /**
     * Immediately saves the specified User to the backing store.
     *
     * @param user the User to save.
     */
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    /**
     * Updates the specified User in the backing store.
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     *
     * @param user the User to update in the backing store.
     */
    @Override
    public void update(User user) {
        User entity = dao.findById(user.getId());
        if(entity!=null){
            entity.setUsername(user.getUsername());
            if(!user.getPassword().equals(entity.getPassword())){
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            entity.setSsoId(user.getSsoId());
            entity.setRoles(user.getRoles());
            entity.setActivatedOn(user.getActivatedOn());
            entity.setFailedLogins(user.getFailedLogins());
            entity.setLastVisitedFrom(user.getLastVisitedFrom());
            entity.setLastVisitedOn(user.getLastVisitedOn());
            entity.setStatus(user.getStatus());
            entity.setUserKey(user.getUserKey());
        }
    }

    /**
     * Deletes a User from the backing store based on the Primary Key.
     *
     * @param id the id of the user.
     */
    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    /**
     * Deletes a User from the backing store based on the username.
     *
     * @param username the username of the user.
     */
    @Override
    public void deleteByUsername(String username) {
        dao.deleteByUsername(username);
    }

    /**
     * Deletes a User from the backing store based on the email.
     *
     * @param email the email of the user.
     */
    @Override
    public void deleteByEmail(String email) {
        dao.deleteByEmail(email);
    }

    /**
     * Deletes a User from the backing store based on the SSO Id.
     *
     * @param ssoId the SSO Id of the user.
     */
    @Override
    public void deleteBySsoId(String ssoId) {
        dao.deleteBySsoId(ssoId);
    }

    /**
     * Returns a List of all users. If no users are found, an empty list is returned.
     *
     * @return a List of all users. If no users are found, an empty list is returned.
     */
    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    /**
     * Returns true if the specified username is in fact unique. That is, if the username
     * is in the backing store for another user other than the one with the specified id, then it is not unique.
     *
     * @param id the id of the user record that we are checking against.
     * @param username the username we are checking for uniqueness.
     *
     * @return true if the username is unique
     */
    @Override
    public boolean isUsernameUnique(Integer id, String username) {
        User user = findByUsername(username);
        return ( user == null || ((id != null) && (Objects.equals(user.getId(), id))));
    }

    /**
     * Returns true if the specified email is in fact unique. That is, if the email
     * is in the backing store for another user other than the one with the specified id, then it is not unique.
     *
     * @param id the id of the user record that we are checking against.
     * @param email the email we are checking for uniqueness.
     *
     * @return true if the email is unique
     */
    @Override
    public boolean isEmailUnique(Integer id, String email) {
        User user = findByEmail(email);
        return ( user == null || ((id != null) && (Objects.equals(user.getId(), id))));
    }

    /**
     * Returns true if the specified ssoId is in fact unique. That is, if the ssoId
     * is in the backing store for another user other than the one with the specified id, then it is not unique.
     *
     * @param id the id of the user record that we are checking against.
     * @param ssoId the ssoId we are checking for uniqueness.
     *
     * @return true if the email is unique
     */
    @Override
    public boolean isSsoIdUnique(Integer id, String ssoId) {
        User user = findBySsoId(ssoId);
        return ( user == null || ((id != null) && (Objects.equals(user.getId(), id))));
    }


}
