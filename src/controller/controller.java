package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dao.BbsDao;
import dao.MemberDao;
import dto.BbsDto;
import dto.MemberDto;

@WebServlet("/control")
public class controller extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 페이지에서 요청한 작업
		String work = req.getParameter("work");
		
		// 회원의 정보
		MemberDao mdao = MemberDao.getInstance();
		
		// 게시물의 정보
		BbsDao bdao = BbsDao.getInstance();
		
		resp.setContentType("text/html; charset=utf-8"); 
		
		
		
		
		// 세션이 만료됐을 때
		if(work.equals("login")) {
			
			resp.sendRedirect("login.jsp");
		}
		
		
		// 아이디 중복확인
		else if(work.equals("ch_id")){
			
			String id = req.getParameter("id");
			boolean ch = false;
			ch = mdao.checkId(id);
			
			PrintWriter pw = resp.getWriter();
			pw.println(ch);
			
		}
		
		
		// 조건에 맞는 게시물을 10개씩 출력해서 전달
		else if(work.equals("list")) {
			
			String sel = req.getParameter("sel");
			String keyword = req.getParameter("keyword");
			
			
			
			// 1)검색 시 항목과 키워드 검사
			
			// 처음 들어왔을 때
			if(sel==null || sel.equals("")) {
				
				sel = "CHOICE";
			}
			// 목록에서 선택을 선택하고 키워드를 입력했을 때
			if(sel.equals("CHOICE")) {
				
				keyword = "";
			}
			// 선택을 제외하고 목록을 선택했지만 키워드를 입력 안했을 때
			if(keyword==null) {
				sel = "CHOICE";
				keyword = "";
			}
			req.setAttribute("sel", sel);
			req.setAttribute("keyword", keyword);		
			
			
			
			// 2)페이지 전환시 페이지 번호 저장
			
			String rcvPage = req.getParameter("rcvPage");
			
			int currPage = 0;
			
			if(rcvPage != null && !rcvPage.equals("") ) {
				currPage = Integer.parseInt(rcvPage);
			}
			
			req.setAttribute("currPage", currPage);
			
			
			
			// 3)게시물의 갯수를 추출
			
			int bbsAmount = 0;		
			int len = bdao.getBbsAmount(sel, keyword);	
			
			// 페이지의 갯수를 저장한다.
			bbsAmount = len/10;
			// 한자릿수가 0이 아닐 경우 페이지를 1증가시켜준다.
			if(len%10 > 0) {
				bbsAmount += 1;
			}
			req.setAttribute("bbsAmount", bbsAmount);
			
			
			
			// 4)항목-키워드, 페이지 번호를 적용해서 게시물을 10개 추출, 전송
			
			//List<BbsDto> list = bdao.getBbslist();
			//List<BbsDto> list =bdao.getSelBbslist(sel, keyword);
			List<BbsDto> list =bdao.getPagingBbslist(sel, keyword, currPage);
	
			req.setAttribute("bbslist", list);
			
			forward("bbslist.jsp", req, resp);		
		}
		
		
		// 글추가 페이지 이동
		else if(work.equals("writeView")) {
			
			resp.sendRedirect("bbswrite.jsp");
		}
		
		// 글추가
		else if(work.equals("write")) {
			
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			boolean ch = false;
			
			ch = bdao.writeBbs(id, title, content);
			// 글 추가에 성공했을 때
			if(ch) {
				resp.sendRedirect("control?work=list");
			}
			// 글 추가에 실패했을 때
			else {
				resp.sendRedirect("bbswrite.jsp");
			}
		}
		
		
		// 게시물 상세페이지 이동
		else if(work.equals("detail")) {
			
			int seq =Integer.parseInt(req.getParameter("seq"));
			
			HttpSession session = req.getSession(true);
			
			// 현재 접속한 id
			MemberDto mdto = (MemberDto)session.getAttribute("login");
			
			// 게시물을 쓴 id
			BbsDto bdto = bdao.getDetailBbs(seq);
			
			if(!mdto.getId().equals(bdto.getId())) {
				
				bdao.getReadcount(seq);				
			}
					
			req.setAttribute("bdto", bdto);
			
			forward("bbsdetail.jsp", req, resp);
		}
		
		
		// 댓글 페이지 이동
		else if(work.equals("replyView")) {
			
			int seq =Integer.parseInt(req.getParameter("seq"));
			
			
			BbsDto bdto = bdao.getDetailBbs(seq);
			
			req.setAttribute("bdto", bdto);
			
			forward("reply.jsp", req, resp);
		}
		
		
		// 댓글 입력
		else if(work.equals("reply")) {
			String id = req.getParameter("id");
			String title= req.getParameter("title");
			String content = req.getParameter("content");
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			boolean ch = false;
			
			ch = bdao.writeReplyBbs(seq, new BbsDto(id, title, content));
			
			PrintWriter pw = resp.getWriter();
			
			pw.println(ch);
				
		}
		
		
		// 회원가입 페이지 이동
		else if(work.equals("registerView")) {
			
			resp.sendRedirect("register.jsp");
		}
		
		
		// 게시물 수정 페이지 이동
		else if(work.equals("updateView")) {
			
			
			int seq = Integer.parseInt(req.getParameter("seq"));
			BbsDto bdto = bdao.getDetailBbs(seq);
			
			req.setAttribute("bdto", bdto);
			forward("bbsupdate.jsp", req, resp);	
		}
		
		
		// 게시물 수정 입력
		else if(work.equals("update")) {
			
			int seq = Integer.parseInt(req.getParameter("seq"));
			String title = req.getParameter("title");
			String content = req.getParameter("content");
					
			bdao.updateBbs(seq, title, content);
			
				
			resp.sendRedirect("control?work=list");		
		}
		
		
		// 게시물 삭제 입력
		else if(work.equals("delete")) {
			
			int seq = Integer.parseInt(req.getParameter("seq"));
			bdao.deleteBbs(seq);
			
			resp.sendRedirect("control?work=list");		
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String work = req.getParameter("work");
		
		MemberDao mdao = MemberDao.getInstance();
		BbsDao bdao = BbsDao.getInstance();

		
		// 아이디와 패스워드를 확인, 게시판으로 이동
		if(work.equals("login_ck")) {
			
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			
			MemberDto dto = mdao.loginMember(id, pwd);
			
			// 아이디와 패스워드에 일치하는 회원 정보가 없다면 문자열 "no" 보내고 로그인화면으로 이동
			if(dto == null) {
				
				PrintWriter pw = resp.getWriter();		
				pw.println("no");
			}
			// 일치한다면 세션에 id를 저장하고 다시 로그인화면으로 돌아간다. 
			else {
				
				
				// 세션에 회원 정보를 저장한다.
				HttpSession session = req.getSession(true);
				
				session.setAttribute("login", dto);			
			}	
		}
		
		
		// 회원가입 정보 저장
		else if(work.equals("register")) {
			
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");		
			String addr = req.getParameter("addr");	
			String email = req.getParameter("email");
			
			boolean ch = false;
			
			// 회원 정보 저장
			ch = mdao.regiMember(id, pwd, addr, email);
			
			// ajax로 받지 않았기 때문에 구분이 필요없는 제어문
			if(ch) {
				
				resp.sendRedirect("login.jsp");
			}
			
			else {
				resp.sendRedirect("login.jsp");
			}			
		}
		
	}
	
	public void forward(String link, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher dis = req.getRequestDispatcher(link);
		dis.forward(req, resp);
	}
}
