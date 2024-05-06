package Service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import Model.User;
import Model.UserRole;

@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

   public User registerUser(User user) {
        // Check if a user with the provided email already exists
        User existingUser = getUserByEmail(user.getEmail());
        if (existingUser != null) {
            return existingUser; // User with the same email already exists, return the existing user
        }
        if (user.getRole() == null) {  // Set the role provided by the user, or assign DEVELOPER role by default
            user.setRole(UserRole.USER); // Set user role by default
        }
        entityManager.persist(user);
        return user;
    }

    public User login(String email, String password) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (user.getPassword().equals(password)) {
                return user;
            }
        } catch (NoResultException e) {
            // User not found
        }
        return null; // Invalid credentials
    }

    public User updateUser(User user) {
        return entityManager.merge(user);
    }

    public User getUserById(Long userId) {
        return entityManager.find(User.class, userId);
    }

    public void deleteUser(Long userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    // Helper method to get user by email
    public User getUserByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
