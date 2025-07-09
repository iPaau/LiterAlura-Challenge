package com.literalura.literalura;

import com.literalura.literalura.model.Book;
import com.literalura.literalura.model.GutendexResponse;
import com.literalura.literalura.model.Author;
import com.literalura.literalura.service.GutendexService;
import com.literalura.literalura.repository.LibroRepository;
import com.literalura.literalura.repository.AutorRepository;
import com.literalura.literalura.entity.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication {
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}
}

@Component
class MenuRunner implements CommandLineRunner {
	@Autowired
	private GutendexService gutendexService;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Override
	public void run(String... args) {
		mostrarMenu();
	}

	private void mostrarMenu() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\n=== Menú Literalura ===\n");
			System.out.println("1. Buscar libro por título y guardar en base de datos");
			System.out.println("2. Listar todos los libros de la base de datos");
			System.out.println("3. Listar libros por idioma");
			System.out.println("4. Listar autores");
			System.out.println("5. Listar autores vivos en un año");
			System.out.println("6. Estadísticas: cantidad de libros por idioma (en/es)");
			System.out.println("0. Salir\n");
			System.out.print("Seleccione una opción: ");
			String opcion = scanner.nextLine();
			switch (opcion) {
				case "1":
					System.out.print("\nIngrese el título a buscar: ");
					String titulo = scanner.nextLine();
					try {
						GutendexResponse respuesta = gutendexService.searchBooksByTitle(titulo);
						List<Book> libros = respuesta.getResults();
						if (libros == null || libros.isEmpty()) {
							System.out.println("\nNo se encontraron libros para ese título.\n");
						} else {
							Book libro = libros.get(0);
							// Guardar autor en la base de datos (si no existe)
							Autor autorEntidad = null;
							if (libro.getAuthors() != null && !libro.getAuthors().isEmpty()) {
								Author autor = libro.getAuthors().get(0);
								autorEntidad = autorRepository.findByNombre(autor.getName())
									.orElseGet(() -> {
										Autor nuevo = new Autor(autor.getName(), autor.getBirthYear(), autor.getDeathYear());
										Autor guardado = autorRepository.save(nuevo);
										return guardado;
									});
							}
							// Guardar libro en la base de datos
							com.literalura.literalura.entity.Libro libroEntidad = new com.literalura.literalura.entity.Libro(
								libro.getTitle(),
								libro.getFirstLanguage(),
								libro.getDownloadCount(),
								autorEntidad
							);
							libroRepository.save(libroEntidad);
							System.out.println("\nLibro guardado en la base de datos: " + libroEntidad.getTitulo() + "\n");
						}
					} catch (Exception e) {
						System.out.println("\nError al buscar libros: " + e.getMessage() + "\n");
					}
					break;
				case "2":
					List<com.literalura.literalura.entity.Libro> todos = libroRepository.findAll();
					if (todos.isEmpty()) {
						System.out.println("\nLa base de datos está vacía.\n");
					} else {
						System.out.println("\nLibros en la base de datos:\n");
						todos.forEach(b -> System.out.println("   - " + b.getTitulo() + " | Autor: " + (b.getAutor() != null ? b.getAutor().getNombre() : "Desconocido") + " | Idioma: " + b.getIdioma() + " | Descargas: " + b.getDescargas()));
						System.out.println();
					}
					break;
				case "3":
					System.out.print("\nIngrese el idioma (ej: 'en', 'es'): ");
					String idioma = scanner.nextLine();
					List<com.literalura.literalura.entity.Libro> porIdioma = libroRepository.findAll().stream()
						.filter(b -> idioma.equalsIgnoreCase(b.getIdioma()))
						.toList();
					if (porIdioma.isEmpty()) {
						System.out.println("\nNo hay libros en ese idioma.\n");
					} else {
						System.out.println("\nLibros en idioma '" + idioma + "':\n");
						porIdioma.forEach(b -> System.out.println("   - " + b.getTitulo() + " | Autor: " + (b.getAutor() != null ? b.getAutor().getNombre() : "Desconocido") + " | Descargas: " + b.getDescargas()));
						System.out.println();
					}
					break;
				case "4":
					List<Autor> autores = autorRepository.findAll();
					if (autores.isEmpty()) {
						System.out.println("\nNo hay autores en la base de datos.\n");
					} else {
						System.out.println("\nAutores en la base de datos:\n");
						autores.forEach(a -> System.out.println("   - " + a.getNombre() + (a.getAnioNacimiento() != null ? " (" + a.getAnioNacimiento() + (a.getAnioFallecimiento() != null ? " - " + a.getAnioFallecimiento() : "") + ")" : "")));
						System.out.println();
					}
					break;
				case "5":
					System.out.print("\nIngrese el año para buscar autores vivos: ");
					try {
						int anio = Integer.parseInt(scanner.nextLine());
						List<Autor> vivos = autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanOrAnioFallecimientoIsNull(anio, anio);
						if (vivos.isEmpty()) {
							System.out.println("\nNo hay autores vivos en ese año.\n");
						} else {
							System.out.println("\nAutores vivos en el año " + anio + ":\n");
							vivos.forEach(a -> System.out.println("   - " + a.getNombre() + (a.getAnioNacimiento() != null ? " (" + a.getAnioNacimiento() + (a.getAnioFallecimiento() != null ? " - " + a.getAnioFallecimiento() : "") + ")" : "")));
							System.out.println();
						}
					} catch (NumberFormatException e) {
						System.out.println("\nAño inválido.\n");
					}
					break;
				case "6":
					long totalEn = libroRepository.countByIdioma("en");
					long totalEs = libroRepository.countByIdioma("es");
					System.out.println("\nEstadísticas:");
					System.out.println("   - Libros en inglés (en): " + totalEn);
					System.out.println("   - Libros en español (es): " + totalEs + "\n");
					break;
				case "0":
					System.out.println("\n¡Hasta luego!\n");
					SpringApplication.exit(context, () -> 0);
					return;
				default:
					System.out.println("\nOpción no válida. Intente de nuevo.\n");
			}
		}
	}
}
