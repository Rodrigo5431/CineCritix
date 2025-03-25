package br.com.backend.ccx.dto;

public class PublicationInsertDTO {

	private Double rate;

	private String notes;

	private Long movieId;

	private Long serieId;

	public Double getRate() {
		return rate;
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

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Long getSerieId() {
		return serieId;
	}

	public void setSerieId(Long serieId) {
		this.serieId = serieId;
	}

}
