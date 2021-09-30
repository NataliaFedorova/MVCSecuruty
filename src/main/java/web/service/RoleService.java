package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void addRole(Role role);

    void updateRole(Role role);

    void removeRoleById(Long id);

    Role findRoleById(Long id);
}
