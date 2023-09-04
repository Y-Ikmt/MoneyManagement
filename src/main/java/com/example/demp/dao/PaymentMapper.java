package com.example.demp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demp.dto.PaymentSearchRequest;
import com.example.demp.entity.PaymentInfo;

@Mapper
public interface PaymentMapper {
	
	//お金増やす
	void insert(String id, int money, String reason);
	
	//収支履歴全検索
	List<PaymentInfo> selectAll(String id);
	
	//収支履歴検索
	List<PaymentInfo> searchPaymentData (PaymentSearchRequest req);
	
	//収支履歴を削除
	void deletePaymentData(String id, Long seq);
}
