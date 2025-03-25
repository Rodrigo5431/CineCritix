package br.com.backend.ccx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.backend.ccx.dto.PublicationDTO;
import br.com.backend.ccx.dto.PublicationInsertDTO;
import br.com.backend.ccx.entities.Movies;
import br.com.backend.ccx.entities.Publication;
import br.com.backend.ccx.entities.Series;
import br.com.backend.ccx.entities.User;
import br.com.backend.ccx.exception.NotAuthorizedException;
import br.com.backend.ccx.exception.NotFoundException;
import br.com.backend.ccx.repository.MoviesRepository;
import br.com.backend.ccx.repository.PublicationRepository;
import br.com.backend.ccx.repository.SeriesRepository;
import br.com.backend.ccx.repository.UserRepository;
import br.com.backend.ccx.security.JwtUtil;

@Service
public class PublicationService {

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MoviesRepository moviesRepository;
	@Autowired
	private SeriesRepository seriesRepository;

	@Autowired
	private JwtUtil jwt;

	public List<PublicationDTO> listAll() {
		return publicationRepository.findAll().stream().map(PublicationDTO::new).toList();
	}

	public PublicationDTO findById(Long id) {
		Optional<Publication> publicatipnOpt = publicationRepository.findById(id);
		if (publicatipnOpt.isEmpty()) {
			throw new NotFoundException("Publication Not Found");
		}

		PublicationDTO dto = new PublicationDTO();
		return dto;
	}

	public PublicationDTO save(PublicationInsertDTO insertDto, String token) {
		Long userId = jwt.getId(token);

		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isEmpty()) {
			throw new NotFoundException("User not Authenticated");
		}

		Publication publication = new Publication();

		if (insertDto.getMovieId() != null) {
			Optional<Movies> moviesOpt = moviesRepository.findById(insertDto.getMovieId());
			if (moviesOpt.isEmpty()) {
				throw new NotFoundException("Movie Not Found");
			}
			publication.setMovie(moviesOpt.get());
		}
		if (insertDto.getSerieId() != null) {
			Optional<Series> seriesOpt = seriesRepository.findById(insertDto.getSerieId());
			if (seriesOpt.isEmpty()) {
				throw new NotFoundException("Serie Not Found");
			}
			publication.setSeries(insertDto.getSerieId() != null ? seriesOpt.get() : null);
		}

		publication.setNotes(insertDto.getNotes());
		publication.setRate(insertDto.getRate());
		publication.setUser(userOpt.get());
		
		publicationRepository.save(publication);

		PublicationDTO publicationDTO = new PublicationDTO(publication);
		return publicationDTO;

	}

	public PublicationDTO update(Long id, PublicationInsertDTO publicationInsert, String token) {

		Long userId = jwt.getId(token);
		Optional<User> userOpt = userRepository.findById(userId);

		if (userOpt.isEmpty()) {
			throw new NotFoundException("User not Authenticated");
		}

		Optional<Publication> publicationOpt = publicationRepository.findById(id);

		if (publicationOpt.isEmpty()) {
			throw new NotFoundException("Publication not found");
		}
		if (publicationOpt.get().getUser().getId() != userId) {
			throw new NotAuthorizedException("Not Authorized to change someone's else publication");
		}
		Publication publication = new Publication();
		publication.setId(id);
		publication.setNotes(publicationInsert.getNotes());
		publication.setRate(publicationInsert.getRate());
		publication.setUser(userOpt.get());

		publicationRepository.save(publication);

		PublicationDTO dto = new PublicationDTO(publication);
		return dto;

	}

	public String delete(Long id) {

		Optional<Publication> publicationOpt = publicationRepository.findById(id);

		if (publicationOpt.isEmpty()) {
			throw new NotFoundException("Publication not found");
		}

		publicationRepository.deleteById(id);

		return "Publication deleted with success";
	}
}
