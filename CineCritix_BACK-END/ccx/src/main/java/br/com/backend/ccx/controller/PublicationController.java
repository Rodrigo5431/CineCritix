package br.com.backend.ccx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.backend.ccx.dto.PublicationDTO;
import br.com.backend.ccx.dto.PublicationInsertDTO;
import br.com.backend.ccx.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/publications")
public class PublicationController {

	@Autowired
	private PublicationService publicationService;

	@Operation(summary = "Lista todas as publicações do sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Publicações localizadas e listadas com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping
	public ResponseEntity<List<PublicationDTO>> listAll() {
		return ResponseEntity.ok(publicationService.listAll());
	}

	@Operation(summary = "Pega uma publicação no sistema através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publicação localizada e pega com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@GetMapping("/{id}")
	public ResponseEntity<PublicationDTO> searchById(@PathVariable Long id) {
		return ResponseEntity.ok(publicationService.findById(id));
	}

	@Operation(summary = "Cadastra uma publicação no sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Publicação cadastrada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PostMapping
	public ResponseEntity<PublicationDTO> create(@RequestBody PublicationInsertDTO publicationInsert,
			@RequestHeader("Authorization") String bearerToken) {
		return ResponseEntity.ok(publicationService.save(publicationInsert, bearerToken));
	}
	
	@Operation(summary = "Atualiza a Publicação através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publicação localizada e atualizada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@PutMapping("/{id}")
	public ResponseEntity<PublicationDTO> update(@PathVariable Long id, PublicationInsertDTO publicationInsert,
            @RequestHeader("Authorization") String bearerToken) {
        return ResponseEntity.ok(publicationService.update(id, publicationInsert, bearerToken));
    }
	
	@Operation(summary = "Deleta uma publicação no sistema através do seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publicação localizada e deletada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
	@DeleteMapping("/{id}")
	public ResponseEntity<String>delete(@PathVariable Long id){
		return ResponseEntity.ok(publicationService.delete(id));
	}

}
