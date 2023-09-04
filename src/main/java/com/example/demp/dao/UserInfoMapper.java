package com.example.demp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demp.entity.UserInfo;

@Mapper
public interface UserInfoMapper {
	
	//ユーザー情報検索
	UserInfo findUserData(String id);
	
	//お金増やす
	void inclease(String id, int money);
	
	//目標を設定
	void updateMokuhyou(Long mokuhyouKingaku);
}
