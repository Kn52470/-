package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Budge;
import com.example.demo.model.Payments;
import com.example.demo.repository.BudgeMapper;
import com.example.demo.service.BudgeService;

@Controller
public class BudgeDetailController {

	@Autowired
	private BudgeMapper budgeMapper;

	@Autowired
	private BudgeService budgeService;

	@RequestMapping(value = "/pastPayDetail/{formattedmonthyear}", method = RequestMethod.GET)
	public String pastPayDetail(Model model ,@PathVariable String formattedmonthyear) {
		
		//指定年月の情報取得
		List<Budge> pastPayDetail = budgeService.pastPayDetail(formattedmonthyear);
		model.addAttribute("pastPayDetail", pastPayDetail);
		
		//過去の支払い合計取得
		LocalDate localDate = LocalDate.parse(formattedmonthyear + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Payments monthPayments =  budgeService.pastPayments(formattedDate);
		model.addAttribute("monthPayments", monthPayments);
		
		//指定月の固定費詳細
		 List<Budge>  detailPastFixedCosts = budgeService.getDetailPastFixedCosts(formattedDate);
		 model.addAttribute("detailPastFixedCosts", detailPastFixedCosts);
		
        return "pastPayDetail";
    }


	
	@GetMapping("/goToClickedDatePage")
	public String goToClickedDatePage(Model model, @RequestParam("year") int year, @RequestParam("month") int month,
			@RequestParam("day") int day) {

		//指定日の情報取得
		LocalDate date = LocalDate.of(year, month, day);
		List<Budge> selectDay = budgeService.selectDay(date);
		model.addAttribute("selectDay", selectDay);
		
		return "clickedDatePage";
	}

	@GetMapping("/monthDetail")
	public String MonthDetail(Model model) {

		//支払い合計（月）の取得
		String targetMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
		List<Map<String, Object>> total = budgeService.getSumMoneyByMonth(targetMonth);
		for (Map<String, Object> map : total) {
			Object value = map.get("total_money");
			if (value instanceof Number) {
				int intValue = ((Number) value).intValue();
				budgeMapper.updateTotalPayment(intValue);
			} else if (value !=	null) {

			}
		}
		
		//支払い合計取得(今月)
		Payments monthPayments = budgeService.monthPayment();
		model.addAttribute("monthPayments", monthPayments);
		
		//支払い内容の取得（今月)
		List<Budge> budge = budgeService.getAllBudgetItems();
		model.addAttribute("budge", budge);

		//今月の固定費詳細の取得
		List<Budge> detailFixed = budgeService.getDetailFixedCosts();
		model.addAttribute("detailFixed", detailFixed);

		//過去の固定費詳細の取得
		List<Budge> pastFixed = budgeService.getPastFixedCosts();
		model.addAttribute("pastFixed", pastFixed);

		return "/monthDetail";
	}

	@PostMapping("/budgetChange")
	public String budgetChange(@RequestParam("budgebox") String budgebox) {
		
		//予算の変更・登録
		budgeService.updateBudgeAmount(budgebox);

		return "redirect:/monthDetail";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam int itemid) {

		//支払いの削除
		budgeService.deleteItem(itemid);

		return "redirect:/monthDetail";
	}

	@PostMapping("/insert")
	public String insert(@RequestParam int itemid) {

		//固定費取得
		List<Budge> pastFixed = budgeService.getPastFixed(itemid);

		Calendar currentDate = Calendar.getInstance();

		for (Budge budge : pastFixed) {
			budge.setRegistrationdate(currentDate.getTime());
		}

		Budge firstBudge = pastFixed.get(0);

		//支払い追加
		budgeService.insertBudge(firstBudge);

		return "redirect:/monthDetail";
	}
}
