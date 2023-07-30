package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void addRole(Role role) {
        em.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return em.createQuery("from Role", Role.class).getResultList();
    }

}
