package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements  RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List <Role> getAllRoles() {
        return entityManager.createQuery("SELECT r from Role r", Role.class).getResultList();
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void updateRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public void removeRoleById(Long id) {
        entityManager.remove(entityManager.find(Role.class, id));
    }

    @Override
    public Role findRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }
}
