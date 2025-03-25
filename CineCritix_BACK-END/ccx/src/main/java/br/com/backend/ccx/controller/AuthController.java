package br.com.backend.ccx.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.backend.ccx.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Endpoints para autenticação do usuário")
public class AuthController {

    @Operation(summary = "Login no sistema")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login feito com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida"),
			@ApiResponse(responseCode = "403", description = "Operação proibida e não pode ser concluída"),
			@ApiResponse(responseCode = "401", description = "Operação não autorizada"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação") })
    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok("O login retorna o token pelo header da resposta");
    }
}
