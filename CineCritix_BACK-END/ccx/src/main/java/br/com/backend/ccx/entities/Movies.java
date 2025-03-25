package br.com.backend.ccx.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@JsonIgnoreProperties({"publications"})
public class Movies {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String year;
	private String type;
	private String poster;
	private String genre;
	private String runtime;
	private String released;
	private String plot;
	private String director;
	private String writer;
	private String imdbRating;
	private String idImdb;

	@OneToMany(mappedBy = "movie")
	private List<Publication> publications;

	public Movies(Long id, String title, String year, String type, String poster, String genre, String runtime,
			String released, String plot, String director, String writer, String imdbRating, String idImdb,
			List<Publication> publications) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.type = type;
		this.poster = poster;
		this.genre = genre;
		this.runtime = runtime;
		this.released = released;
		this.plot = plot;
		this.director = director;
		this.writer = writer;
		this.imdbRating = imdbRating;
		this.idImdb = idImdb;
		this.publications = publications;
	}

	public Movies() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getIdImdb() {
		return idImdb;
	}

	public void setIdImdb(String idImdb) {
		this.idImdb = idImdb;
	}


}
