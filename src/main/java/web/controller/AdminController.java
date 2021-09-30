package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getAllUsers(ModelMap model) {
        // показывает данные всех людей из бд
        model.addAttribute("users",userService.getAllUsers());
        return "get_all_users";
    }

    @GetMapping("/{id}")
    public String getOneUser(@PathVariable("id") Long id, ModelMap model) {
        //показывает данные одного человека из бд по ID
        model.addAttribute("user", userService.getOneUser(id));
        return "get_one_user";
    }

    @GetMapping("/new_user")
    public String createUserForm (Model model) {
        //возвращает html-страницу для добавления нового пользователя
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "new";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm (Model model, @PathVariable("id") Long id) {
        //возвращает html-страницу для редактирования человека
        model.addAttribute("user", userService.getOneUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/new_user")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        // добавляет пользователя в бд
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser (@PathVariable("id") Long id) {
        // удаляет пользователя
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
