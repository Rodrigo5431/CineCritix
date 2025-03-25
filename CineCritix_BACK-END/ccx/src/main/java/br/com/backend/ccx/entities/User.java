package br.com.backend.ccx.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.backend.ccx.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Email
	@Column(unique = true)
	private String email;

	@Column(name = "full_name")
	private String fullName;

	@Column
	private String password;

	@Column
	@Enumerated(EnumType.STRING)
	private Role profile;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Publication> publications = new ArrayList<>();

	@Column
	private String avatar;

	public User(Long id, String email, String fullName, String password) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.password = password;
	}

	public User(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.fullName = user.getFullName();
		this.password = user.getPassword();
		this.profile = user.getProfile();
	}

	public User() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + profile.getRoleName()));
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Role getProfile() {
		return profile;
	}

	public void setProfile(Role profile) {
		this.profile = profile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
