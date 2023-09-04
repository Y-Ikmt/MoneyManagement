package com.example.demp.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PaymentAddRequest implements Serializable {
	@NotNull(message="収支金額を入力してください。")
	@PositiveOrZero(message="収支金額は0以上の値にしてください。")
	Integer money;
	@NotEmpty(message="収支の理由を入力してください。")
	String reason;
	String kbn;
	
}
