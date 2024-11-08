package daw2a.gestionbiblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class GestionBibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionBibliotecaApplication.class, args);
	}

}
