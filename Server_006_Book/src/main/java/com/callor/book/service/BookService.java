package com.callor.book.service;

import java.util.List;

import com.callor.book.model.BookDTO;

public interface BookService {
	
	public List<BookDTO> selectAll();
	
	public BookDTO findById(String bk_isbn);
	public List<BookDTO> findByTitle(String bk_title);
	public List<BookDTO> findByCName(String bk_cnmae);
	public List<BookDTO> findByAName(String bk_aname);
	
	public void insert();
	public void update();
	public void delete();

}
