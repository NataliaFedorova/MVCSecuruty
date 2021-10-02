package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void addRole(Role role);

    void updateRole(Role role);

    Role findRoleById(Long id);

    Role findRoleByName(String name);
}
