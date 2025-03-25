package br.com.backend.ccx.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.backend.ccx.entities.Movies;
import br.com.backend.ccx.entities.Publication;
import jakarta.persistence.OneToMany;

@JsonIgnoreProperties({"publications"})
public class MoviesDTO {

	private Long id;

	@JsonProperty("Title")
	private String title;

	@JsonProperty("Year")
	private String year;

	@JsonProperty("imdbID")
	private String imdbID;

	@JsonProperty("Type")
	private String type;

	@JsonProperty("Poster")
	private String poster;

	@JsonProperty("Genre")
	private String genre;

	@JsonProperty("Runtime")
	private String runtime;

	@JsonProperty("Released")
	private String released;

	@JsonProperty("Plot")
	private String plot;

	@JsonProperty("Director")
	private String director;

	@JsonProperty("Writer")
	private String writer;

	@JsonProperty("imdbRating")
	private String imdbRating;

	@OneToMany(mappedBy = "movie")
	private List<Publication> publications;

	public MoviesDTO() {
		super();
	}

	public MoviesDTO(Long id, String title, String year, String imdbID, String type, String poster, String genre,
			String runtime, String released, String plot, String director, String writer, String imdbRating,
			List<Publication> publications) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.imdbID = imdbID;
		this.type = type;
		this.poster = poster;
		this.genre = genre;
		this.runtime = runtime;
		this.released = released;
		this.plot = plot;
		this.director = director;
		this.writer = writer;
		this.imdbRating = imdbRating;
		this.publications = publications;
	}

	public MoviesDTO(Movies movies) {
		this.id = movies.getId();
		this.title = movies.getTitle();
		this.year = movies.getYear();
		this.imdbID = movies.getIdImdb();
		this.type = movies.getType();
		this.poster = movies.getPoster();
		this.genre = movies.getGenre();
		this.runtime = movies.getRuntime();
		this.released = movies.getReleased();
		this.plot = movies.getPlot();
		this.director = movies.getDirector();
		this.writer = movies.getWriter();
		this.imdbRating = movies.getImdbRating();
		this.publications = movies.getPublications();
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

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
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

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
