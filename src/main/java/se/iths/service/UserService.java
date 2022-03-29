package se.iths.service;

import se.iths.entity.Item;
import se.iths.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UserService {

    @PersistenceContext
    EntityManager entityManager;

    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

}
