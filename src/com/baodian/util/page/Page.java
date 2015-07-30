package com.baodian.util.page;

public abstract class Page {

	private int countNums;//总数
	private int pageNums;//页数
	private int page;//当前页码
	private int num;//每页条数
	private int firstNum;//第一条编号
	
	public Page() {}
	public Page(int page) {
		this.page = page;
	}
	public Page(int page, int num) {
		this.page = page;
		this.num = num;
	}
	public abstract void countPage(int countNums);
	/**
	 * 计算页数
	 * @param defaultNum 默认每页条数
	 * @param maxNum 最大每页条数
	 */
	public void countPage(int defaultNum, int maxNum, int countNums) {
		if(num < 1 || num > maxNum)
			num = defaultNum;
		if(countNums < 1) {
			page = 1;
			pageNums = 1;
			return ;
		}
		this.countNums = countNums;
		pageNums = countNums / num;
		if(countNums%num != 0)
			pageNums ++;
		if(page < 1) page = 1;
		if(page > pageNums) page = pageNums;
		firstNum = (page - 1) * num;
	}
//set get
	/**
	 * 总数
	 */
	public int getCountNums() {
		return countNums;
	}
	/**
	 * 总页数
	 */
	public int getPageNums() {
		return pageNums;
	}
	/**
	 * 当前页码
	 */
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getFirstNum() {
		return firstNum;
	}
	
}
