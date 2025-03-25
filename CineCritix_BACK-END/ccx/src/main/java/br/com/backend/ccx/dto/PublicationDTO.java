package br.com.backend.ccx.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.backend.ccx.entities.Movies;
import br.com.backend.ccx.entities.Publication;
import br.com.backend.ccx.entities.Series;
import br.com.backend.ccx.entities.User;

public class PublicationDTO {

	private Long id;

	private Double rate;

	private String notes;

	@JsonIgnoreProperties("publications")
	private User user;

	@JsonIgnoreProperties("publications")
	private Movies movies;

	@JsonIgnoreProperties("publications")
	private Series series;

	public Movies getMovies() {
		return movies;
	}

	public void setMovies(Movies movies) {
		this.movies = movies;
	}

	public PublicationDTO() {
		super();
	}

	public PublicationDTO(Publication publication) {
		this.id = publication.getId();
		this.rate = publication.getRate();
		this.notes = publication.getNotes();
		this.user = publication.getUser();
		this.movies = publication.getMovie();
		this.series = publication.getSeries();
	}

	public Double getRate() {
		return rate;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
