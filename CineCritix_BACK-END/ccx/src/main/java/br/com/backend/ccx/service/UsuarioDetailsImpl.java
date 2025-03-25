package br.com.backend.ccx.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.backend.ccx.entities.User;
import br.com.backend.ccx.repository.UserRepository;

@Service
public class UsuarioDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuarioOpt = userRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()) {
        	throw new UsernameNotFoundException("Usuário não encontrado");
        }
        User user = new User(usuarioOpt.get());
        return user;
    }
}