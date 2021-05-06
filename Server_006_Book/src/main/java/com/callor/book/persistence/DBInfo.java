package com.callor.book.persistence;

public class DBInfo {
	
	public static class BOOK {
		public static final String bk_isbn = "ISBN"; // CHAR(13)
		public static final String bk_title ="도서명"; //도서명	NVARCHAR2(125)
		public static final String bk_cname = "출판사명"; //출판사명	NVARCHAR2(125)
		public static final String bk_cceo = "출판사대표";//출판사대표	NVARCHAR2(20)
		public static final String bk_auname = "저자명";//저자명	NVARCHAR2(50)
		public static final String bk_autel = "저자연락처";//저자연락처	VARCHAR2(20)
		public static final String bk_title1 = "출판일";//출판일	VARCHAR2(10)
		public static final String bk_price = "가격";//가격	NUMBER
		public static final String bk_pages = "페이지";//페이지	NUMBER
		
	}

}
