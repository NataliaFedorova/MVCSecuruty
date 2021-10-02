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
        getRoles(user);
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User user) {

        getRoles(user);
        userDao.updateUser(id, user);
    }

    private void getRoles(User user) {
        Set<Role> rolesFindByName = new HashSet<>();

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            rolesFindByName.add(roleDao.findRoleByName(role.getRoleName()));
        }
        user.setRoles(rolesFindByName);
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
