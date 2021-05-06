package com.callor.book.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.callor.book.model.BookDTO;
import com.callor.book.persistence.DBContract;
import com.callor.book.service.BookService;

public class BookServiceImplV1 implements BookService {
	
	protected Connection dbConn;
	public BookServiceImplV1() {
		dbConn = DBContract.getDBConnection();
	}
	
	protected List<BookDTD> select(PreparedStatement pStatement);
		List<BookDTO> bookList = null;
		ResultSet rSet = pStr.executeQuery();
		while(rSet.next()) {
			
			BookDTO bookDTO = new BookDTO();
			bookDTO.setBk_isbn(rSet.getString(0));
			bookDTO.setBk_title(rSet.getString(0));
			bookDTO.setBk_cceo(rSet.getString(0));
			bookDTO.setBk_cname(rSet.getString(0));
			bookDTO.setBk_auname(rSet.getString(0));
			bookDTO.setBk_autel(rSet.getString(0));
			bookDTO.setBk_date(rSet.getString(0));
			bookDTO.setBk_price(rSet.getString(0));
			bookDTO.setBk_pages(rSet.getString(0));
			bookList.add(bookDTO);
			
		}
	@Override
	public List<BookDTO> selectAll() {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM view_도서정보";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			List<BookDTO> bookList = this.select(pStr);
			
			
			PreparedStatement pStr = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BookDTO findById(String bk_isbn) {
		// TODO Auto-generated method stub
		String sql = " SELECT * FROM view_도서정보";
		sql += " WHERE ISBN = ? ";
		
		PreparedStatement pStr = null;
		pStr = dbConn.prepareStatement(sql);
		pStr.setString(1, bk_isbn.trim());
		
		List<BookDTO> bookList = this.select(pStr);
		if(bookList != null && bookList.size());
		return null;
	}

	@Override
	public BookDTO findById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> findByTitle(String bk_title) {
		// TODO Auto-generated method stub
		String sql = 
		return null;
	}

	@Override
	public List<BookDTO> findByCName(String bk_cnmae) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> findByAName(String bk_aname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

}
