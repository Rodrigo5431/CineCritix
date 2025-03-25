package br.com.backend.ccx.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.backend.ccx.entities.Series;

public interface SeriesRepository extends JpaRepository<Series, Long>{

	Optional<Series> findByTitle(String title);
}
