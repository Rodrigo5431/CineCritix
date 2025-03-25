package br.com.backend.ccx.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "publications")
public class Publication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull
	private Double rate;

	@Column(nullable = true)
	private String notes;

	@ManyToOne
    @JoinColumn(name = "movie_id")
    private Movies movie;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Series series;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

	public Publication(Long id, Double rate, String notes, Movies movie, User user, Series series) {
		super();
		this.id = id;
		this.rate = rate;
		this.notes = notes;
		this.movie = movie;
		this.series = series;
		this.user = user;
	}

	public Publication() {
		super();
	}

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

	public User getUser() {
		return user;
	}
	

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Movies getMovie() {
		return movie;
	}

	public void setMovie(Movies movie) {
		this.movie = movie;
	}

	

}
