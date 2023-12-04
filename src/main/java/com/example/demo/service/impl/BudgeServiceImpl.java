package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Budge;
import com.example.demo.model.Payments;
import com.example.demo.repository.BudgeMapper;
import com.example.demo.service.BudgeService;

@Service
public class BudgeServiceImpl implements BudgeService {

	@Autowired
	private BudgeMapper budgeMapper;

	//支払い追加
	@Override
	public int insertBudge(Budge budge) {

		return budgeMapper.insertBudge(budge);
	}

	//支払い合計（月）
	@Override
	public List<Map<String, Object>> getSumMoneyByMonth(String targetMonth) {

		return budgeMapper.sumMoneyByMonth(targetMonth);
	}

	//支払い合計（記録）
	@Override
	public void updateTotalPayment(int total) {

		budgeMapper.updateTotalPayment(total);
	}

	//支払い合計取得(今月)
	@Override
	public Payments monthPayment() {

		return budgeMapper.monthPayment();
	}

	//過去の支払い合計取得
	@Override
	public Payments pastPayments(String formattedmonthyear) {

		return budgeMapper.pastPayments(formattedmonthyear);
	}

	//支払い内容の取得（今月)
	@Override
	public List<Budge> getAllBudgetItems() {

		return budgeMapper.getAllBudgetItems();
	}

	// 月年が存在しない場合に追加する	
	@Override
	public int MonthYear() {

		return budgeMapper.MonthYear();
	}

	//予算上限の取得
	@Override
	public List<Integer> getBudgetAmount(String targetMonthYear) {

		return budgeMapper.getBudgetAmount(targetMonthYear);
	}

	//予算の変更・登録
	@Override
	public void updateBudgeAmount(String budgebox) {

		budgeMapper.updateBudgeAmount(budgebox);
	}

	//固定費の合計（当月）
	@Override
	public Integer getfixedCosts() {

		return budgeMapper.getfixedCosts();
	}

	//今月の固定費詳細
	@Override
	public List<Budge> getDetailFixedCosts() {

		return budgeMapper.getDetailFixedCosts();
	}

	//過去の固定費詳細
	@Override
	public List<Budge> getPastFixedCosts() {

		return budgeMapper.getPastFixedCosts();
	}

	//指定月の固定費詳細
	@Override
	public List<Budge> getDetailPastFixedCosts(String formattedDate){
		
		return budgeMapper.getDetailPastFixedCosts(formattedDate);
	}

	//固定費取得
	@Override
	public List<Budge> getPastFixed(int itemid) {

		return budgeMapper.getPastFixed(itemid);
	}

	//過去の支払い
	@Override
	public List<Payments> getPastPayments() {

		return budgeMapper.getPastPayments();
	}

	//支払いの削除
	@Override
	public int deleteItem(int itemid) {

		return budgeMapper.deleteItem(itemid);
	}

	//指定日の情報取得
	@Override
	public List<Budge> selectDay(LocalDate date) {

		return budgeMapper.selectDay(date);
	}

	//指定年月の情報取得
	@Override
	public List<Budge> pastPayDetail(String formattedmonthyear) {

		return budgeMapper.pastPayDetail(formattedmonthyear);
	}
}
