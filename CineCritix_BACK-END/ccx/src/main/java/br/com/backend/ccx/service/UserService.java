package br.com.backend.ccx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.backend.ccx.dto.UserDTO;
import br.com.backend.ccx.dto.UserInsertDTO;
import br.com.backend.ccx.entities.User;
import br.com.backend.ccx.enums.Role;
import br.com.backend.ccx.exception.NotAuthorizedException;
import br.com.backend.ccx.exception.NotFoundException;
import br.com.backend.ccx.repository.UserRepository;
import br.com.backend.ccx.security.JwtUtil;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository repository;

	@Autowired
	private JwtUtil jwt;

	public List<UserDTO> listAll() {
		return repository.findAll().stream().map(UserDTO::new).toList();
	}

	public UserDTO searchById(Long id) {
		Optional<User> user = repository.findById(id);
		if (user.isEmpty()) {
			throw new NotFoundException("User not found");
		}
		return new UserDTO(user.get());
	}

	public UserDTO searchByEmail(String email) {
		Optional<User> user = repository.findByEmail(email);
		if (user.isEmpty()) {
			throw new NotFoundException("User not found");
		}
		return new UserDTO(user.get());
	}

	public User save(UserInsertDTO insertDTO) {
		if (!insertDTO.getConfirmPassword().equals(insertDTO.getPassword())) {
			new RuntimeException("As senhas estão diferentes");
		}
		User user = new User();
		user.setEmail(insertDTO.getEmail().toLowerCase());
		user.setFullName(insertDTO.getFullName());
		user.setPassword(bCryptPasswordEncoder.encode(insertDTO.getPassword()));
		user.setAvatar(insertDTO.getAvatar());
		user.setProfile(insertDTO == null ? Role.USER : insertDTO.getProfile());
		repository.save(user);
		return user;
	}

	public UserDTO update(UserInsertDTO userInsert, String token) {
		Long idUser = jwt.getId(token);

		Optional<User> userOpt = repository.findById(idUser);

		if (userOpt.isEmpty()) {
			throw new NotFoundException("User not found");
		}
		
		if (userInsert.getPassword() != null) {
			if (!userInsert.getConfirmPassword().equals(userInsert.getPassword())) {
				throw new RuntimeException("As senhas estão diferentes");
			}
		}
		

		User user = new User();
		user.setId(idUser);
		user.setEmail(userInsert.getEmail() != null ? userInsert.getEmail().toLowerCase() : userOpt.get().getEmail());
		user.setFullName(userInsert.getFullName() != null ? userInsert.getFullName() : userOpt.get().getFullName());
		user.setPassword(userInsert.getPassword() != null ? bCryptPasswordEncoder.encode(userInsert.getPassword()) : userOpt.get().getPassword());
		user.setProfile(userInsert.getProfile() != null ? userInsert.getProfile() : userOpt.get().getProfile());
		user.setAvatar(userInsert.getAvatar() != null ? userInsert.getAvatar() : userOpt.get().getAvatar());
		repository.save(user);

		return new UserDTO(user);
	}

	public void deleteById(Long id, String token) {
		if (id != null) {
			Optional<User> userOpt = repository.findById(id);

			if (userOpt.isEmpty()) {
				throw new NotAuthorizedException("User not Authenticated");
			} else {
				repository.deleteById(id);
				return;
			}
		}

		Long userid = jwt.getId(token);

		Optional<User> userOpt = repository.findById(userid);

		if (userOpt.isEmpty()) {
			throw new NotAuthorizedException("User not Authenticated");
		}

		repository.deleteById(userid);
		return;
	}
}
