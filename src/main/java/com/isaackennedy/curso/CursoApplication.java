package com.isaackennedy.curso;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.isaackennedy.curso.domain.Categoria;
import com.isaackennedy.curso.repositories.CategoriaRepository;

@SpringBootApplication
public class CursoApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository catRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		catRepository.saveAll(Arrays.asList(cat1, cat2));
	}
}
