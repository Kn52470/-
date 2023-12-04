package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.BudgeForm;
import com.example.demo.model.Budge;
import com.example.demo.model.Payments;
import com.example.demo.repository.BudgeMapper;
import com.example.demo.service.BudgeService;

@Controller
public class BudgeController {

	@Autowired
	private BudgeMapper budgeMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BudgeService budgeService;

	@GetMapping("/list")
	public String getList(Model model) {
		
		//支払い合計（月）の取得
		String targetMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
		List<Map<String, Object>> total = budgeService.getSumMoneyByMonth(targetMonth);
		model.addAttribute("total", total);
		
		// 月年が存在しない場合に追加する
		budgeService.MonthYear();
		for (Map<String, Object> map : total) {
			Object value = map.get("total_money");
			if (value instanceof Number) {
				int intValue = ((Number) value).intValue();
				//支払い合計（記録）
				budgeMapper.updateTotalPayment(intValue);
			} else if (value != null) {

			}
		}
		//現在の時間の取得
		LocalDate currentDate = LocalDate.now();
		LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
		String targetMonthYear = firstDayOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		//予算上限の取得
		List<Integer> budgetAmount = budgeService.getBudgetAmount(targetMonthYear);
		model.addAttribute("budgetAmount", budgetAmount);
		
		//固定費の合計（当月）の取得
		Integer fixedCosts = budgeMapper.getfixedCosts();
		model.addAttribute("fixedCosts", fixedCosts);
		
		//過去の支払いの取得
		List <Payments> pastPay = budgeService.getPastPayments();
		model.addAttribute("pastPay", pastPay);
		
		return "list";

	}

	@GetMapping("/addition")
	public String addition(Model model, @ModelAttribute BudgeForm budgeForm) {

		model.addAttribute("budgeForm", budgeForm);

		List<String> options1 = Arrays.asList("住宅費", "食費", "水道光熱費", "通信費", "車両・交通費", "保険料", "学費", "その他");
		model.addAttribute("options1", options1);

		List<String> options2 = Arrays.asList("変動費", "固定費");
		model.addAttribute("options2", options2);

		return "addition";
	}

	@PostMapping("/addition")
	public String addition(Model model, @ModelAttribute @Validated BudgeForm budgeForm, BindingResult result) {

		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		budgeForm.setRegistrationdate(currentDate);

		if (result.hasErrors()) {
			// バリデーションエラーがある場合の処理
			return addition(model, budgeForm);
		}

		Budge budge = modelMapper.map(budgeForm, Budge.class);
		
		//支払い追加
		budgeService.insertBudge(budge);

		return "redirect:/list";
	}

	
}