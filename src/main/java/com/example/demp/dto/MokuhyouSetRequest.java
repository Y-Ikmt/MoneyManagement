package com.example.demp.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class MokuhyouSetRequest implements Serializable {
	@NotNull(message = "目標金額を入力してください")
	@PositiveOrZero(message="目標金額は0以上の値にしてください。")
	private Long nowMokuhyou;
}
