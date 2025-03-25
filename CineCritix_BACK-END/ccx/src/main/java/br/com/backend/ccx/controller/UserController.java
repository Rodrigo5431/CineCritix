package br.com.backend.ccx.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.backend.ccx.dto.UserDTO;
import br.com.backend.ccx.dto.UserInsertDTO;
import br.com.backend.ccx.entities.User;
import br.com.backend.ccx.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@Operation(summary = "Lista todos os usuários do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuários localizados e listados com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> listAll() {
		return ResponseEntity.ok(service.listAll());
	}

	@Operation(summary = "Pega um usuário no sistema através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuário localizado e pego com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> searchById(@PathVariable Long id) {
		UserDTO userDto = service.searchById(id);
		return ResponseEntity.ok(userDto);
	}

	@Operation(summary = "Pega um usuário no sistema através do seu email")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuário localizado e pego com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDTO> searchByEmail(@PathVariable String email) {
		return ResponseEntity.ok(service.searchByEmail(email));
	}

	@Operation(summary = "Cadastra um usuário no sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PostMapping
	public ResponseEntity<UserDTO> save(@Valid @RequestBody UserInsertDTO userInsertDTO) {
		User user = service.save(userInsertDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDTO(user));
	}

	@Operation(summary = "Atualiza o usuário através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuário localizado e atualizado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PutMapping
	public ResponseEntity<UserDTO> update(@Valid @RequestBody UserInsertDTO userInsertDTO,
			@RequestHeader("Authorization") String bearerToken) {
		UserDTO user = service.update(userInsertDTO, bearerToken);
		return ResponseEntity.ok(user);
	}

	@Operation(summary = "Deleta um usuário no sistema através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Usuário localizado e deletado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String bearerToken) {
		service.deleteById(id, bearerToken);
		return ResponseEntity.noContent().build();
	}
}
