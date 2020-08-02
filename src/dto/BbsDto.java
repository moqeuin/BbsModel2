package dto;

import java.io.Serializable;

// BBS : Bulletin Board System (게시판)
public class BbsDto implements Serializable {
/*
 DROP TABLE BBS
CASCADE CONSTRAINTS;

DROP SEQUENCE SEQ_BBS;

CREATE TABLE BBS(

	SEQ NUMBER(8) PRIMARY KEY,
	ID VARCHAR2(50) NOT NULL,
	
	REF NUMBER(8) NOT NULL,
	STEP NUMBER(8) NOT NULL,
	DEPTH NUMBER(8) NOT NULL,
	
	TITLE VARCHAR2(200) NOT NULL,
	CONTENT VARCHAR2(4000) NOT NULL,
	WDATE DATE NOT NULL,
	
	DEL NUMBER(1) NOT NULL,
	READCOUNT NUMBER(8) NOT NULL
);

CREATE SEQUENCE SEQ_BBS
START WITH 1
INCREMENT BY 1;

ALTER TABLE BBS
ADD CONSTRAINT FK_BBS_ID FOREIGN KEY(ID)
REFERENCES MEMBER(ID);

 */
	
	private int seq; // 게시물 구분, 게시물 바깥쪽 정렬
	private String id; // 등록한사람의 id
	
	private int ref; // 게시물의 그룹번호
	private int step; // 게시물의 안쪽 정렬
	private int depth; // 게시물의 댓글 구분
	
	private String title; // 게시물의 제목
	private String content; // 게시물의 내용
	private String wdate; // 게시 날짜
	
	private int del; // 게시물 삭제 구분 (삭제  0 -> 1)
	private int readcount; // 게시물 조회수
	
	
	public BbsDto() {
	
	}


	public BbsDto(int seq, String id, int ref, int step, int depth, String title, String content, String wdate,
			int del, int readcount) {
		super();
		this.seq = seq;
		this.id = id;
		this.ref = ref;
		this.step = step;
		this.depth = depth;
		this.title = title;
		this.content = content;
		this.wdate = wdate;
		this.del = del;
		this.readcount = readcount;
	}
	
	public BbsDto(int seq, String id, String title, String content, String wdate, int readcount) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.wdate = wdate;
		this.readcount = readcount;
	}
	
	

	


	public BbsDto(String id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public int getRef() {
		return ref;
	}


	public void setRef(int ref) {
		this.ref = ref;
	}


	public int getStep() {
		return step;
	}


	public void setStep(int step) {
		this.step = step;
	}


	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getWdate() {
		return wdate;
	}


	public void setWdate(String wdate) {
		this.wdate = wdate;
	}


	public int getDel() {
		return del;
	}


	public void setDel(int del) {
		this.del = del;
	}


	public int getReadcount() {
		return readcount;
	}


	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}


	@Override
	public String toString() {
		return "BbsDto [seq=" + seq + ", id=" + id + ", ref=" + ref + ", step=" + step + ", depth=" + depth + ", title="
				+ title + ", content=" + content + ", wdate=" + wdate + ", del=" + del + ", readcount=" + readcount
				+ "]";
	}


	
	
	
	
}
