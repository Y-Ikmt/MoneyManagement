package com.example.demp.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PaymentSearchRequest implements Serializable {
	String userId;
	String startDate;
	String endDate;
	Boolean plusFlg;
	Boolean minusFlg;
	
}
