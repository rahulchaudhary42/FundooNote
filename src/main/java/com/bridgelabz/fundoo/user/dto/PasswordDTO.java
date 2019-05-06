package com.bridgelabz.fundoo.user.dto;

public class PasswordDTO {

	private String newPassword;
	private String confirmPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "PasswordDTO [newPassword=" + newPassword + ", confirmPassword=" + confirmPassword + "]";
	}

}