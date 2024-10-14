package com.softluc.menudigital;

import com.softluc.menudigital.modelo.Permisos;
import com.softluc.menudigital.modelo.RoleEnum;
import com.softluc.menudigital.modelo.Roles;
import com.softluc.menudigital.modelo.Usuario;
import com.softluc.menudigital.repositorio.PermisosRepositorio;
import com.softluc.menudigital.repositorio.RolesRepositorio;
import com.softluc.menudigital.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MenudigitalApplication {

	@Value("${usuario.root}")
	private String usuarioRoot;

	@Value("${usuario.pass}")
	private String usuarioPass;

	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MenudigitalApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepositorio usuarioRepositorio, RolesRepositorio rolesRepositorio, PermisosRepositorio permisosRepositorio){
		return args -> {

			/*CREAR PERMISOS*/
			Permisos permiso1 = Permisos.builder()
					.nombre("CREATE")
					.build();
			Permisos permiso2 = Permisos.builder()
					.nombre("READ")
					.build();
			Permisos permiso3 = Permisos.builder()
					.nombre("UPDATE")
					.build();
			Permisos permiso4 = Permisos.builder()
					.nombre("DELETE")
					.build();

			if(permisosRepositorio.count() == 0) permisosRepositorio.saveAll(List.of(permiso1, permiso2, permiso3, permiso4));

			/*CREAR ROLES*/
			Roles rolAdmin = Roles.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permisos(Set.of(permiso1, permiso2, permiso3, permiso4))
					.build();

			Roles rolUser = Roles.builder()
					.roleEnum(RoleEnum.USER)
					.permisos(Set.of(permiso1, permiso2))
					.build();

			Roles rolInvited = Roles.builder()
					.roleEnum(RoleEnum.INVITED)
					.permisos(Set.of(permiso2))
					.build();

			Roles rolDeveloper = Roles.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permisos(Set.of(permiso1, permiso2, permiso3, permiso4))
					.build();

			if (rolesRepositorio.count() == 0) rolesRepositorio.saveAll(List.of(rolAdmin, rolUser, rolInvited, rolDeveloper));

			/*USER CREATE*/
			Usuario usuario = Usuario.builder()
					.nombre(usuarioRoot)
					.password(passwordEncoder.encode(usuarioPass))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialsNoExpired(true)
					.roles(Set.of(rolDeveloper))
					.build();

			if (usuarioRepositorio.count() == 0) usuarioRepositorio.save(usuario);

		};

	}

}
