package com.example.demp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demp.dao.UserInfoMapper;
import com.example.demp.entity.UserInfo;

@Service
public class UserInfoService {

	@Autowired
	UserInfoMapper userInfoMapper;
	
	public UserInfo findUserData(String id) {
		return userInfoMapper.findUserData(id);
	}
	
	
	public void increase(String id, int money, String kbn) {
		
		//区分が"down"なら-1をかけてマイナスにする。
		if(kbn.equals("down")) {
			money = money * -1;
		}
		
		userInfoMapper.inclease(id, money);
	}
	
	public void mokuhyouSet(Long mokuhyouKingaku) {
		userInfoMapper.updateMokuhyou(mokuhyouKingaku);
	}
}
