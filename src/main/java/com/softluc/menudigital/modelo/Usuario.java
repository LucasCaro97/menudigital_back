package com.softluc.menudigital.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String password;
    @ManyToOne
    @JoinColumn(name = "fk_provincia")
    private Provincia provincia;
    @ManyToOne
    @JoinColumn(name = "fk_localidad")
    private Localidad localidad;
    private String direccion;
    private String logo;
    private String razonSocial;
    private Long telefono;

    @ManyToOne
    @JoinColumn(name = "fk_plan")
    private Plan plan;
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "account_no_expired")
    private boolean accountNoExpired;
    @Column(name = "account_no_locked")
    private boolean accountNoLocked;
    @Column(name = "credentials_no_expired")
    private boolean credentialsNoExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Roles> roles = new HashSet<>();



}
