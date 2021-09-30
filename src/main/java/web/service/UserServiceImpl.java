package web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private UserDao userDao;
    private RoleDao roleDao;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getOneUser(Long id) {
        return userDao.getOneUser(id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        Set<Role> roles = new HashSet<>();
        if (user.getRoles().contains(new Role("ROLE_ADMIN"))) {
            roles.add(roleDao.findRoleById(1L));
        } else if (user.getRoles().contains(new Role("ROLE_USER"))) {
            roles.add(roleDao.findRoleById(2L));
        }
        user.setRoles(roles);
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User user) {
        User userToBeUpdated = userDao.getOneUser(id);
        userToBeUpdated.setUsername(user.getUsername());
        userToBeUpdated.setName(user.getName());
        userToBeUpdated.setSurname(user.getSurname());
        userToBeUpdated.setEmail(user.getEmail());
        userToBeUpdated.setAge(user.getAge());
        //пароль сразу в зашифрованном виде
        userToBeUpdated.setPassword(user.getPassword());

        Set<Role> roles = new HashSet<>();

        if (user.getRoles().contains(new Role("ROLE_ADMIN"))) {
            roles.add(roleDao.findRoleById(1L));
        }
        roles.add(roleDao.findRoleById(2L));
        userToBeUpdated.setRoles(roles);

        userDao.updateUser(id, userToBeUpdated);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
