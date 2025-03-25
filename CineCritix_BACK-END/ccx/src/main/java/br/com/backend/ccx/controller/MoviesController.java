package br.com.backend.ccx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.backend.ccx.dto.MovieInsertDTO;
import br.com.backend.ccx.dto.MoviesDTO;
import br.com.backend.ccx.service.MoviesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/movies")
public class MoviesController {

	@Autowired
	private MoviesService moviesService;

	@Operation(summary = "Cadastra uma lista de filmes no sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de filmes cadastrada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PostMapping("/getMoviesOmdb")
	public ResponseEntity<List<MoviesDTO>> insertMoviesOmdb() {
		List<MoviesDTO> moviesDTO = moviesService.insertOmdbMovies();
		return ResponseEntity.ok(moviesDTO);
	}

	@Operation(summary = "Lista todos os filmes do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Filmes localizados e listados com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping
	public ResponseEntity<List<MoviesDTO>> list() {
		List<MoviesDTO> moviesDTO = moviesService.listAll();
		return ResponseEntity.ok(moviesDTO);
	}

	@Operation(summary = "Lista todos os usuários do sistema divididos por página")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Filmes localizados e paginados com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping("/listpag")
	public ResponseEntity<Page<MoviesDTO>> listPageable(
			@PageableDefault(direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		Page<MoviesDTO> moviesDTO = moviesService.listPag(pageable);
		return ResponseEntity.ok(moviesDTO);
	}

	@Operation(summary = "Pega um filme no sistema através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Filme localizado e pego com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping("/{id}")
	public ResponseEntity<MoviesDTO> searchById(@PathVariable Long id) {
		MoviesDTO moviesDTO = moviesService.findById(id);
		return ResponseEntity.ok(moviesDTO);
	}

	@Operation(summary = "Pega um usuário no sistema através do seu título")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Filme localizado e pego com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping("/title/{title}")
	public ResponseEntity<MoviesDTO> searchByTitle(@PathVariable String title) {
		MoviesDTO moviesDTO = moviesService.findByTitle(title);
		return ResponseEntity.ok(moviesDTO);
	}

	@Operation(summary = "Cadastra um filme no sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Filme cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MoviesDTO> insert(@Valid @RequestBody MovieInsertDTO insert,
			@RequestHeader("Authorization") String bearerToken) {
		MoviesDTO moviesDTO = moviesService.create(insert, bearerToken);
		return ResponseEntity.ok(moviesDTO);
	}

	@Operation(summary = "Atualiza o filme através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Filme localizado e atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MoviesDTO> update(@PathVariable Long id, @Valid @RequestBody MovieInsertDTO update) {
		MoviesDTO moviesDTO = moviesService.update(id, update);
		return ResponseEntity.ok(moviesDTO);
	}

	@Operation(summary = "Deleta um filme no sistema através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Filme localizado e deletado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MoviesDTO> delete(@PathVariable Long id) {
		moviesService.delete(id);
		return ResponseEntity.ok().build();
	}
}
