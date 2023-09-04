package com.example.demp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
	
	@NotNull(message = "ユーザーIDを入力してください")
	private String userId;
	@NotNull(message = "パスワードを入力してください")
	private String password;
}
