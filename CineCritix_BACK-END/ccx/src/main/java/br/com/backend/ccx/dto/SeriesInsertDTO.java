package br.com.backend.ccx.dto;

public class SeriesInsertDTO {

	private String title;
	private String year;
	private String type;
	private String poster;
	private String Genre;
	private String Runtime;
	private String Released;
	private String Plot;
	private String Director;
	private String Writer;
	private Double imdbRating;
	private String idImdb;

	public SeriesInsertDTO() {
		super();
	}

	public SeriesInsertDTO(String title, String year, String type, String poster) {
		super();
		this.title = title;
		this.year = year;
		this.type = type;
		this.poster = poster;
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

	public String getGenre() {
		return Genre;
	}

	public void setGenre(String genre) {
		Genre = genre;
	}

	public String getRuntime() {
		return Runtime;
	}

	public void setRuntime(String runtime) {
		Runtime = runtime;
	}

	public String getReleased() {
		return Released;
	}

	public void setReleased(String released) {
		Released = released;
	}

	public String getPlot() {
		return Plot;
	}

	public void setPlot(String plot) {
		Plot = plot;
	}

	public String getDirector() {
		return Director;
	}

	public void setDirector(String director) {
		Director = director;
	}

	public String getWriter() {
		return Writer;
	}

	public void setWriter(String writer) {
		Writer = writer;
	}

	public Double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(Double imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getIdImdb() {
		return idImdb;
	}

	public void setIdImdb(String idImdb) {
		this.idImdb = idImdb;
	}

	
}
