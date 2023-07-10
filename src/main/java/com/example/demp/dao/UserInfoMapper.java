package com.example.demp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demp.entity.UserInfo;

@Mapper
public interface UserInfoMapper {
	
	//ユーザー情報検索
	UserInfo findUserData(Long id);
	
	//お金増やす
	void inclease(Long id, int money);
	
	//目標を設定
	void updateMokuhyou(Long mokuhyouKingaku);
}
