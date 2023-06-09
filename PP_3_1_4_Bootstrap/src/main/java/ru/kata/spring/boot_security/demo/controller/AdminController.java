package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String show(Principal principal, Model model) {
        User admin = userService.findByUsername(principal.getName());
        model.addAttribute("admin", admin);

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userRoles", roleService.getAllRoles());

        model.addAttribute("userNew", new User());
        model.addAttribute("rolesNew", roleService.getAllRoles());

        return "admin";
    }

    @PostMapping("")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping(value = "/{id}")
    public String updateUser(@ModelAttribute("user") User updatedUser, @PathVariable("id") Long id) {
        User existingUser = userService.findUserById(id);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setName(updatedUser.getName());
        existingUser.setSurname(updatedUser.getSurname());
        existingUser.setAge(updatedUser.getAge());
        if (updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(existingUser.getPassword());
        } else {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getRoles() == null) {
            existingUser.setRoles(existingUser.getRoles());
        } else {
            existingUser.setRoles(updatedUser.getRoles());
        }
        userService.updateUser(existingUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
