package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    private final RoleDao roleDao;

    public UserDaoImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u from User u", User.class).getResultList();
    }

    @Override
    public User getOneUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        Query query = entityManager.createQuery("UPDATE User set username=:username, name=:name, " +
                                                "surname=:surname, email=:email, age=:age, password=:password WHERE id=:id");
        query.setParameter("id", user.getId());
        query.setParameter("username",user.getUsername());
        query.setParameter("name",user.getName());
        query.setParameter("surname",user.getSurname());
        query.setParameter("email",user.getEmail());
        query.setParameter("age",user.getAge());
        query.setParameter("password",user.getPassword());

        query.executeUpdate();
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username=:username",
                User.class).setParameter("username", username);
        return query.getSingleResult();
    }

}
