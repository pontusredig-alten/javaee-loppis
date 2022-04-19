package se.iths.service;

import se.iths.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

@Transactional
public class UserService {

    @PersistenceContext
    EntityManager entityManager;


    private final Pbkdf2PasswordHash passwordEncoder;

    @Inject
    public UserService(Pbkdf2PasswordHash passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public User createUser(User user) {
        user.setPassword(passwordEncoder.generate(user.getPassword().toCharArray()));
        entityManager.persist(user);
        return user;
    }

    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

}
