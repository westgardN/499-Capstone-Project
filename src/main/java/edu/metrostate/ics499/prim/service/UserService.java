package edu.metrostate.ics499.prim.service;

import java.util.List;

import edu.metrostate.ics499.prim.model.User;

/**
 * An interface for managing a User by id, username, email, or sso id that a service class would implement.
 */
public interface UserService {

    User findById(int id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findBySsoId(String ssoId);

    void save(User user);

    void update(User user);

    void deleteById(int id);

    void deleteByUsername(String username);

    void deleteByEmail(String email);

    void deleteUserBySsoId(String ssoId);

    List<User> findAll();

    boolean isUsernameUnique(Integer id, String username);

    boolean isEmailUnique(Integer id, String email);

    boolean isSsoIdUnique(Integer id, String ssoId);
}