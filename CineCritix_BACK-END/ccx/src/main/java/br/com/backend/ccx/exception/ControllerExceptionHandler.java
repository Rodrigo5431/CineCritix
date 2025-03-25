package br.com.backend.ccx.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	private ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
	@ExceptionHandler(NotAuthorizedException.class)
	private ResponseEntity<Object> handleNotFoundException(NotAuthorizedException ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> erros = new ArrayList<>();
		for (FieldError e : ex.getBindingResult().getFieldErrors()) {
			erros.add(e.getField() + ": " + e.getDefaultMessage());
		}

		ResponseError erroResposta = new ResponseError(status.value(), "Existem campos inválidos", LocalDateTime.now(),
				erros);

		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}
}
