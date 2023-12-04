package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.demo.model.Budge;
import com.example.demo.model.Payments;

public interface BudgeService {

	//支払い追加
	public int insertBudge(Budge budge);

	//支払い合計（月）
	public List<Map<String, Object>> getSumMoneyByMonth(String targetMonth);

	//支払い合計（記録）
	public void updateTotalPayment(int total);

	//支払い合計取得(今月)
	public Payments monthPayment();

	//過去の支払い合計取得
	public Payments pastPayments(String formattedmonthyear);

	//支払い内容の取得（今月)
	List<Budge> getAllBudgetItems();

	// 月年が存在しない場合に追加する	
	public int MonthYear();

	//予算上限の取得
	public List<Integer> getBudgetAmount(String targetMonthYear);

	//予算の変更・登録
	public void updateBudgeAmount(String budgebox);

	//固定費の合計（当月）
	public Integer getfixedCosts();

	//今月の固定費詳細
	List<Budge> getDetailFixedCosts();

	//過去の固定費詳細
	List<Budge> getPastFixedCosts();

	//指定月の固定費詳細
	List<Budge> getDetailPastFixedCosts(String formattedDate);

	//固定費取得
	List<Budge> getPastFixed(int itemid);

	//過去の支払い
	public List<Payments> getPastPayments();

	//支払いの削除
	public int deleteItem(int itemid);

	//指定日の情報取得
	List<Budge> selectDay(LocalDate date);

	//指定年月の情報取得
	List<Budge> pastPayDetail(String formattedmonthyear);
}
