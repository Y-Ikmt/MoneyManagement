package com.example.demp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demp.dao.PaymentMapper;
import com.example.demp.dto.PaymentSearchRequest;
import com.example.demp.entity.PaymentInfo;

@Service
public class PaymentService {

	@Autowired
	PaymentMapper paymentMapper;
	
	/*public UserInfo findUserData(Long id) {
		return paymentMapper.findUserData(id);
	}*/
	
	public void insert(Long id, int money, String reason, String kbn) {
		
		//区分が"down"なら-1をかけてマイナスにする。
		if(kbn.equals("down")) {
			money = money * -1;
		}
		
		paymentMapper.insert(id, money, reason);
	}
	
	public List<PaymentInfo> findPaymentData(Long id) {
		return paymentMapper.selectAll(id);
		
	}
	
	//収支履歴を検索
	public List<PaymentInfo> searchPaymentData(Long id,PaymentSearchRequest req) {
		req.setUserId(id);
		System.out.println("id:"+req.getUserId()+" minusFlg:" + req.getMinusFlg() + " plusFlg:" + req.getPlusFlg());
		return paymentMapper.searchPaymentData(req);
		
	}
	
	//収支履歴を削除
	public void deletePaymentData(Long id, Long seq) {
		paymentMapper.deletePaymentData(id, seq);
	}
}
