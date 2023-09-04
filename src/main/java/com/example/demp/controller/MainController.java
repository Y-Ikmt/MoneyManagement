package com.example.demp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demp.dto.LoginRequest;
import com.example.demp.dto.MokuhyouSetRequest;
import com.example.demp.dto.PaymentAddRequest;
import com.example.demp.dto.PaymentSearchRequest;
import com.example.demp.entity.PaymentInfo;
import com.example.demp.entity.UserInfo;
import com.example.demp.service.PaymentService;
import com.example.demp.service.UserInfoService;

@Controller
public class MainController {
	
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	PaymentService paymentService;
	
	String id = "1";
	
	//検索条件保存用
	String beforeStartDate;
	String beforeEndDate;
	
	//index.html表示
	@GetMapping(value = "/")
	public String viewIndex(Model model) {
		
		UserInfo userInfo = userInfoService.findUserData(id);
		model.addAttribute("userInfo", userInfo);
		
		PaymentAddRequest paymentAddRequest = new PaymentAddRequest();
		model.addAttribute("paymentAddRequest", paymentAddRequest);
		return "index";
	}
	
	//お金増加
	@PostMapping(value = "/increase")
	public String viewIncrease(@Validated @ModelAttribute PaymentAddRequest paymentAddRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }

    		UserInfo userInfo = userInfoService.findUserData(id);
    		model.addAttribute("userInfo", userInfo);
            model.addAttribute("validationError", errorList);
    		model.addAttribute("paymentAddRequest", paymentAddRequest);
            return "index";
        }
		
		userInfoService.increase(id, paymentAddRequest.getMoney(),paymentAddRequest.getKbn());
		paymentService.insert(id, paymentAddRequest.getMoney(),paymentAddRequest.getReason(),paymentAddRequest.getKbn());
		
		UserInfo userInfo = userInfoService.findUserData(id);
		model.addAttribute("userInfo", userInfo);
		return "redirect:/";
	}
	
	//収支履歴画面へ遷移
	@GetMapping(value = "/history")
	public String viewHistory(Model model) {
		
		//収支一覧を検索して渡す
		List<PaymentInfo> paymentInfo = paymentService.findPaymentData(id);
		model.addAttribute("paymentInfo", paymentInfo);
		
		//検索条件の日付の初期値を渡す
        Calendar cl = Calendar.getInstance();
        //SimpleDateFormatクラスでフォーマットパターンを設定する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = sdf.format(cl.getTime());
		model.addAttribute("endDate", endDate);

		cl.add(Calendar.DAY_OF_MONTH, -30);
        String startDate = sdf.format(cl.getTime());
		model.addAttribute("startDate", startDate);
		
		//他でも使えるようにグローバル変数にセット
		beforeStartDate = startDate;
		beforeEndDate = endDate;
		
		return "history";
	}
	
	//収支履歴検索実行
	@PostMapping(value = "/paymentSearch")
	public String searchPayment(@ModelAttribute("paymentSearchRequest") PaymentSearchRequest req, Model model) {
		System.out.println("searchPayment");
		
		List<PaymentInfo> paymentInfo = paymentService.searchPaymentData(id, req);
		model.addAttribute("paymentInfo", paymentInfo);
		
		String startDate = req.getStartDate();
		String endDate = req.getEndDate();
		Boolean plusFlg = req.getPlusFlg();
		Boolean minusFlg = req.getMinusFlg();
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("plusFlg", plusFlg);
		model.addAttribute("minusFlg", minusFlg);
		
		//他でも使えるようにグローバル変数にセット
		beforeStartDate = startDate;
		beforeEndDate = endDate;
		
		return "history";
	}	
	
	//収支履歴検索実行
	@GetMapping(value = "/paymentDelete")
	public String deletePayment(@RequestParam("value1") String userId, @RequestParam("value2") Long seq , Model model) {
		System.out.println("deletePayment");
		System.out.println(userId);
		System.out.println(seq);
		
		paymentService.deletePaymentData(userId, seq);

		model.addAttribute("startDate", beforeStartDate);
		model.addAttribute("endDate", beforeEndDate);
		
		PaymentSearchRequest req = new PaymentSearchRequest();
		req.setUserId(userId);
		req.setStartDate(beforeStartDate);
		req.setEndDate(beforeEndDate);
		req.setPlusFlg(true);
		req.setMinusFlg(true);
		List<PaymentInfo> paymentInfo = paymentService.searchPaymentData(id, req);
		model.addAttribute("paymentInfo", paymentInfo);
		
		return "history";
	}
	
	Long MokuhyouKingaku;
	//目標設定画面を開く
	@GetMapping(value = "/mokuhyou")
	public String mokuhyouView(@RequestParam("value") Long nowMokuhyou, Model model) {
		MokuhyouSetRequest mokuhyouSetRequest = new MokuhyouSetRequest();
		MokuhyouKingaku = nowMokuhyou;
		mokuhyouSetRequest.setNowMokuhyou(nowMokuhyou);
		model.addAttribute("mokuhyouSetRequest",mokuhyouSetRequest);
		return "mokuhyou";
	}
	
	//目標金額をセット
	@PostMapping(value = "/mokuhyouSet")
	public String mokuhyouSet(@Validated @ModelAttribute MokuhyouSetRequest mokuhyouSetRequest, BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }

            model.addAttribute("validationError", errorList);
            return "mokuhyou";
        }
		userInfoService.mokuhyouSet(mokuhyouSetRequest.getNowMokuhyou());
		UserInfo userInfo = userInfoService.findUserData(id);
		model.addAttribute("userInfo", userInfo);
		PaymentAddRequest paymentAddRequest = new PaymentAddRequest();
		model.addAttribute("paymentAddRequest", paymentAddRequest);
		return "index";
	}
	
	@GetMapping(value = "/login")
	public String loginView(Model model) {
		LoginRequest loginRequest = new LoginRequest();
		model.addAttribute("loginRequest", loginRequest);
		return "login";
	}
	
	@GetMapping(value = "/loginAction")
	public String login(@Validated @ModelAttribute LoginRequest loginRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }

    		model.addAttribute("loginRequest", loginRequest);
            return "login";
        }
		
		//userInfoService.increase(id, paymentAddRequest.getMoney(),paymentAddRequest.getKbn());

		
		UserInfo userInfo = userInfoService.findUserData(loginRequest.getUserId());
		model.addAttribute("userInfo", userInfo);

		return "index";
	}


}
