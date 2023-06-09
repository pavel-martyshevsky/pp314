package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;
    @Column(name = "rolename")
    private String roleName;

    public Role() {
    }

    public Role(Long id_role) {
        this.id_role = id_role;
    }

    public Role(Long id_role, String roleName) {
        this.id_role = id_role;
        this.roleName = roleName;
    }

    public Long getId() {
        return id_role;
    }

    public void setId(Long id_role) {
        this.id_role = id_role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id_role +
                ", name='" + roleName + '\'' +
                '}';
    }

    public String getNameRole() {
        return roleName.substring("ROLE_".length());
    }
}
