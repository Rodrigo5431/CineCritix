package br.com.backend.ccx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.backend.ccx.entities.Publication;
import br.com.backend.ccx.entities.User;


public interface PublicationRepository extends JpaRepository<Publication, Long>{

    List<Publication> findByUser(User user);
}
