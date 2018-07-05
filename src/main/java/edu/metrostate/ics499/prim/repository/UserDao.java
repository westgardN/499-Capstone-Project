package edu.metrostate.ics499.prim.repository;

import java.util.List;

import edu.metrostate.ics499.prim.model.User;

/**
 * The UserDao interface defines the available persistence operations that can be performed for
 * a User.
 */
public interface UserDao {

    User findById(int id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findBySsoId(String ssoId);

    void save(User user);

    void deleteById(int id);

    void deleteByUsername(String username);

    void deleteByEmail(String email);

    void deleteBySsoId(String ssoId);

    List<User> findAll();

}