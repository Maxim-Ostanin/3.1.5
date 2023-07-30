package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {


    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    @Transactional
    public void removeUserById(int id) {
        Query query = em.createQuery("delete from User u where id = :userId");
        query.setParameter("userId", id);
        query.executeUpdate();
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getUserById(int id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public void updateUser(int id, User updatedUser) { em.merge(updatedUser); }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<User> query = em.createQuery("select u from User u left join fetch u.roles where u.username=:username", User.class);
        User user = query.setParameter("username", username).getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("Username not found!");
        }
        return user;
    }
}
