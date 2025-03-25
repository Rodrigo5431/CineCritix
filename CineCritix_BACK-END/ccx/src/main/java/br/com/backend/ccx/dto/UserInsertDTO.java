package br.com.backend.ccx.dto;

import br.com.backend.ccx.enums.Role;

public class UserInsertDTO {

	private String fullName;
	private String email;
	private String password;
	private String confirmPassword;
	private String avatar;
	private Role profile;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Role getProfile() {
		return profile;
	}

	public void setProfile(Role profile) {
		this.profile = profile;
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
