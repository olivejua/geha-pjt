package com.bit.geha.criteria;

import lombok.Data;
import lombok.ToString;

//페이징에 필요한 데이터들을 하나의 객체로 묶기위한 클래스

@ToString
@Data
public class AdminPageCriteria {

	private int page; // 보여줄 페이지 번호
	private int perPageNum; // 페이지당 보여줄 게시글의 개수

	private String type; //검색할 타입
	private String keyword; //검색 키워드
	
	public AdminPageCriteria() {
		//최초 게시판에 진입할 때를 위해서 기본 값을 설정 해야 한다.
		this.page = 1;
		this.perPageNum = 10;
	}

	public AdminPageCriteria(int page,int perPageNum) {
		this.page=page;
		this.perPageNum=perPageNum;
	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
			return;
		}

		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {

		if (perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}

		this.perPageNum = perPageNum;
	}

	/*
	 * limit 구문에서 시작 위치를 지정할 때 사용된다. 예를 들어 10개씩 출력하는 경우 3페이지의 데이터는 linit 20, 10 과 같은
	 * 형태가 되어야 한다.
	 */
	/* this.page 가 1이면 0이 되어야 한다 mysql limit 0, 10 해야 처음부터 10개씩 나온다. */
	/* 마이바티스 조회쿼리의 #{pageStart}에 전달된다. */
	public int getPageStart() {
		return (this.page - 1) * perPageNum;
	}
	
	

}
