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

import br.com.backend.ccx.dto.SeriesInsertDTO;
import br.com.backend.ccx.dto.SeriesDTO;
import br.com.backend.ccx.service.SeriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/series")
public class SeriesController {

	@Autowired
	private SeriesService seriesService;

	@Operation(summary = "Cadastra uma lista de filmes no sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de filmes cadastrada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PostMapping("/getSeriesOmdb")
	public ResponseEntity<List<SeriesDTO>> insertSeriesOmdb() {
		List<SeriesDTO> seriesDTO = seriesService.insertOmdbSeries();
		return ResponseEntity.ok(seriesDTO);
	}

	@Operation(summary = "Lista todos os filmes do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Filmes localizados e listados com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping
	public ResponseEntity<List<SeriesDTO>> list() {
		List<SeriesDTO> seriesDTO = seriesService.listAll();
		return ResponseEntity.ok(seriesDTO);
	}

	@Operation(summary = "Lista todos os usuários do sistema divididos por página")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Filmes localizados e paginados com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping("/listpag")
	public ResponseEntity<Page<SeriesDTO>> listPageable(
			@PageableDefault(direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		Page<SeriesDTO> seriesDTO = seriesService.listPag(pageable);
		return ResponseEntity.ok(seriesDTO);
	}

	@Operation(summary = "Pega um filme no sistema através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Filme localizado e pego com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping("/{id}")
	public ResponseEntity<SeriesDTO> searchById(@PathVariable Long id) {
		SeriesDTO seriesDTO = seriesService.findById(id);
		return ResponseEntity.ok(seriesDTO);
	}

	@Operation(summary = "Pega um usuário no sistema através do seu título")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Filme localizado e pego com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping("/{title}")
	public ResponseEntity<SeriesDTO> searchByTitle(@PathVariable String title) {
		SeriesDTO seriesDTO = seriesService.findByTitle(title);
		return ResponseEntity.ok(seriesDTO);
	}

	@Operation(summary = "Cadastra um filme no sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Filme cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SeriesDTO> insert(@Valid @RequestBody SeriesInsertDTO insert,
			@RequestHeader("Authorization") String bearerToken) {
		SeriesDTO seriesDTO = seriesService.create(insert, bearerToken);
		return ResponseEntity.ok(seriesDTO);
	}

	@Operation(summary = "Atualiza o filme através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Filme localizado e atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SeriesDTO> update(@PathVariable Long id, @Valid @RequestBody SeriesInsertDTO update) {
		SeriesDTO seriesDTO = seriesService.update(id, update);
		return ResponseEntity.ok(seriesDTO);
	}

	@Operation(summary = "Deleta um filme no sistema através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Filme localizado e deletado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<SeriesDTO> delete(@PathVariable Long id) {
		seriesService.delete(id);
		return ResponseEntity.ok().build();
	}
}
