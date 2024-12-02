package com.softluc.menudigital;

import com.softluc.menudigital.modelo.*;
import com.softluc.menudigital.repositorio.PermisosRepositorio;
import com.softluc.menudigital.repositorio.PlanRepositorio;
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

	;

	public static void main(String[] args) {
		SpringApplication.run(MenudigitalApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepositorio usuarioRepositorio,
						   RolesRepositorio rolesRepositorio,
						   PermisosRepositorio permisosRepositorio,
						   PasswordEncoder passwordEncoder,
						   PlanRepositorio planRepositorio){
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

			if(planRepositorio.count() == 0){
				Plan plan1 = Plan.builder()
						.nombre("Plan Gratuito")
						.descripcion("Este plan es ideal para pequeños negocios o quienes deseen iniciarse en la digitalización de sus productos. Podrás crear y personalizar hasta 10 productos con imágenes y descripciones, permitiendo a tus clientes explorar tu oferta de manera rápida y sencilla desde cualquier dispositivo. ¡Empieza a mostrar lo mejor de tu negocio sin compromisos!")
						.cantidadProductos(10)
						.build();

				Plan plan2 = Plan.builder()
						.nombre("Plan Intemedio")
						.descripcion("Con el Plan Intermedio, obtienes más capacidad para mostrar tu oferta. Este plan te permite crear hasta 30 productos con imágenes, descripciones y detalles, ideal para negocios en crecimiento que buscan una presencia digital sólida. Mantén a tus clientes informados y mejora su experiencia mostrando una mayor variedad de opciones.")
						.cantidadProductos(30)
						.build();

				Plan plan3 = Plan.builder()
						.nombre("Plan Ilimitado")
						.descripcion("Nuestro Plan Ilimitado está diseñado para los negocios que desean aprovechar todo el potencial de la digitalización sin restricciones. Podras adaptar y actualizar tu menú a medida que lo necesites. Ofrece a tus clientes una experiencia completa y profesional con un menú que refleja toda la amplitud de tu oferta.")
						.cantidadProductos(100)
						.build();

				planRepositorio.saveAll(List.of(plan1, plan2, plan3));

			}

		};

	}

}
