package member.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import member.model.dao.MemberDao;
import member.model.vo.Admin;
import member.model.vo.Member;

public class MemberService {
	private MemberDao mdao = new MemberDao();
	public int searchAdmin(String userId) {
		Connection conn = getConnection();
		int result = mdao.searchAdmin(conn, userId);
		close(conn);
		return result;
	}

	public Admin loginAdmin(String userId, String password) {
		Connection conn = getConnection();
		Admin admin = mdao.loginAdmin(conn,userId,password);
		close(conn);
		return admin;
	}

	public Member loginMember(String userId, String password) {
		Connection conn = getConnection();
		Member member = mdao.loginMember(conn,userId,password);
		close(conn);
		return member;
	}

	public int selectCheckId(String userId) {
		Connection conn = getConnection();
		int result = mdao.selectCheckId(conn, userId);
		close(conn);
		return result;
	}

	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = mdao.insertMember(conn, member);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Member selectMember(String userId) {
		Connection conn = getConnection();
		Member member = mdao.selectMember(conn,userId);
		close(conn);
		return member;
	}

	public int memberUpdate(Member member) {
		Connection conn = getConnection();
		int result = mdao.memberUpdate(conn, member);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public ArrayList<Member> selectMemberAll() {
		Connection conn = getConnection();
		ArrayList<Member>  list = mdao.selectMemberAll(conn);
		close(conn);
		return list;
	}
	

}
