package services;

import data.User;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

//@Named("userDAO")
public class UserDAO {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public void persist(User user){
        manager.persist(user);
    }

    @Transactional
    public void remove(User user){
        manager.remove(user);
    }

    @Transactional
    public void update(User user){
        manager.merge(user);
    }

    @Transactional
    public List<User> listAll() {
        return manager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Transactional
    public User findWithUsername(String username){
        try {
            return manager
                    .createQuery("select u from User u where u.username = :username ", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    public User findWithUsernameAndPassword(String username,String password){
        try {
            return manager
                    .createQuery("select u from User u where u.username = :username and u.password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

}
