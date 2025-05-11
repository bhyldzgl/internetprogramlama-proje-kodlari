package com.habersitesi;

import com.habersitesi.model.Rol;
import com.habersitesi.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HaberSitesiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaberSitesiBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initRoles(RolRepository rolRepository) {
		return args -> {
			for (Rol.RolTipi tip : Rol.RolTipi.values()) {
				rolRepository.findByAd(tip).orElseGet(() -> rolRepository.save(new Rol(null, tip)));
			}
		};
	}

}
