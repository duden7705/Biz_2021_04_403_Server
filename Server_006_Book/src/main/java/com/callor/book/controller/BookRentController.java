package com.callor.book.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.callor.book.model.BookRentDTO;
import com.callor.book.model.BookRentVO;
import com.callor.book.model.BuyerDTO;
import com.callor.book.service.BookRentService;
import com.callor.book.service.BuyerService;
import com.callor.book.service.impl.BookRentServiceImplV1;
import com.callor.book.service.impl.BuyerServiceImplV1;

/*
 * Web Browser의 Request를 처리할 클래스
 */
@WebServlet("/rent/*") // rent로 시작되는 모든 요청을 네가 처리해라
public class BookRentController extends HttpServlet {

	private static final long serialVersionUID = 921652892464670154L;

	protected BookRentService brService;
	protected BuyerService buService;
	

	public BookRentController() {
		brService = new BookRentServiceImplV1(); // new... 추가하기
		buService = new BuyerServiceImplV1();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// rent/* 로 요청이 되면 * 위치에 부착되는 Sub 요청을 분리해낸다
		// rent/seq 라고 요청을 하면 subPath 에는 /seq 라는 문자열이 담길것이다
		String subPath = req.getPathInfo();

		// outputStream을 사용하여 문자열 방식으로 응답을 하기위한 준비
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();

		// rent/seq 로 요청이 들어오면...
		if (subPath.equals("/seq")) {

			// 주문번호로 찾기
			String strSeq = req.getParameter("id"); // "id"라는 변수에 값을 담아보낸다

			if (strSeq == null || strSeq.equals("")) { // null값이거나 또는 아무것도 입력하지 않고 전송하면
				out.println("주문번호가 없음");
				out.close();
			} else {
				Long nSeq = Long.valueOf(strSeq);
				BookRentDTO brDTO = brService.findById(nSeq);

				// view에서 보여줄 데이터 생성
				/*
				 * ServletContext
				 * Tomcat을 기반으로 작성된 Web APP Service에서
				 * 요청(Req)과 응답(Res)을 총괄하는 정보가 담긴 객체.
				 * 
				 * Web App Service를 구현하기 위하여
				 * Req, Res를 처리하는 여러가지 기능을 구현해야 하는데
				 * 그러한 기능을 미리 구현해 놓았기 때문에
				 * ServletContext를 getter 하는 것만으로도 충분하다.
				 * 
				 * DB 등으로부터 조회된 데이터를 Web 에게 응답하고자 할때
				 * 쉬운 방법으로 전달할 수 있도록 하는 기능이 미리 구현되어 있다.
				 */
				ServletContext app = this.getServletContext();

				// bService가 return한 brDTO를
				// app객체에 BOOK 이라는 속성변수로 세팅하기
				// app 객체에 BOOK 이라는 객체변수를 생성하고
				// BOOK 변수에 brDTO 값을 저장한다.
				// BookRentDTO BOOK = brDTO 이런 형식의 코드가 실행된다
				// 세팅된 BOOK 객체변수는 jsp 파일에서 참조하여 값을 표현할 수 있다.
				app.setAttribute("BOOK", brDTO);

				// book.jsp파일을 읽어서
				// app에 setting한 BOOK 변수와 함께
				// Rendering 을 하라
				// webapp/WEB-INF/views/book.jsp 파일을 읽어서
				//	Java 코드로 변환하고, 실행할 준비를 하라
				RequestDispatcher disp = app.getRequestDispatcher("/WEB-INF/views/book.jsp");
				// Rendering 된 view 데이터를
				// web browser로 response 하라
				disp.forward(req, resp);
			}

		} else if (subPath.equals("/list")) {
			// 도서대여 전체 목록
			brService.selectAll();
			out.println("도서대여 전체목록 보기");
		} else if (subPath.equals("/isbn")) {
			// 도서코드로 찾기
			brService.findByBISBN("isbn");

		} else if (subPath.equals("/buyer")) {
			// 회원코드로 찾기
			brService.findByBuyerCode("buyercode");

			// rent/order로 요청하면 주문서작성 처음화면 보여주기
			// 회원이름을 입력하는 화면을 보여주기
		} else if (subPath.equals("/order")) {
			
			RequestDispatcher disp = req.getRequestDispatcher("/WEB-INF/views/order.jsp");
			disp.forward(req, resp);
			
		} else if (subPath.equals("/order/page1")){
			
			String bu_name = req.getParameter("bu_name");
			if (bu_name == null || bu_name.equals("")) {
				out.println("회원 이름을 반드시 입력해야 합니다");
				out.close();
			} else {
				List<BuyerDTO> buList = buService.findByName(bu_name);

				// Service에서 전달된 데이터가 잘 왔나 테스트
				System.out.println("=".repeat(50));
				for(BuyerDTO d : buList) {
					System.out.println(d.toString());
				}
				System.out.println("=".repeat(50));
				
				// ServletContext를 생성하여 속성(변수)세팅하기
				// ServletContext app = req.getServletContext();
				// app.setAttribute("BUYERS", buList);
				
				// req 객체에 바로 세팅하기
				req.setAttribute("BUYERS", buList);
				
				// page1.jsp 파일을 열고 BUYERS 변수와 함께
				// Rendering을 하여 HTML 코드를 생성하라
				RequestDispatcher disp = req.getRequestDispatcher("/WEB-INF/views/page1.jsp");
				disp.forward(req, resp);
			}
			
		} else if (subPath.equals("/order/page2")) {
			
			String bu_code = req.getParameter("bu_code");
			
			// bu_code 값에 해당하는 회원정보 추출
			BuyerDTO buyerDTO = buService.findById(bu_code);
			
			// ServletContext app = req.getServletContext();
			// app.setAttribute("BUYER", buyerDTO);
			req.setAttribute("BUYER", buyerDTO);
			
			// BUYER에 담긴 회원정보를 page2.jsp에
			// Rendering 하여 보여라
			RequestDispatcher disp = req.getRequestDispatcher("/WEB-INF/views/page2.jsp");
			
			disp.forward(req, resp);
			
		} else if(subPath.equals("/drder/book")) {
			
			
		} else if (subPath.equals("/return")) {
			// 반납하기
			BookRentVO bookRentVO = new BookRentVO();
			brService.update(bookRentVO);

		} else {
			// 더이상 그만하기
			out.println("NOT FOUND");
			out.close();
		}
	}

}
