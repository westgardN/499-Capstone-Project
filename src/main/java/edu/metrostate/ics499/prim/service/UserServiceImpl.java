package edu.metrostate.ics499.prim.service;

import java.util.List;

import edu.metrostate.ics499.prim.model.User;
import edu.metrostate.ics499.prim.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findById(int id) {
        User user = dao.findById(id);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = dao.findByUsername(username);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = dao.findByEmail(email);
        return user;
    }

    @Override
    public User findBySsoId(String ssoId) {
        User user = dao.findBySsoId(ssoId);
        return user;
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
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

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        dao.deleteByUsername(username);
    }

    @Override
    public void deleteByEmail(String email) {
        dao.deleteByEmail(email);
    }

    @Override
    public void deleteUserBySsoId(String ssoId) {
        dao.deleteBySsoId(ssoId);
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public boolean isUsernameUnique(Integer id, String username) {
        User user = findByUsername(username);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }

    @Override
    public boolean isEmailUnique(Integer id, String email) {
        User user = findByEmail(email);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }

    @Override
    public boolean isSsoIdUnique(Integer id, String ssoId) {
        User user = findBySsoId(ssoId);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }
}
