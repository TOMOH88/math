package member.model.dao;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import member.model.vo.Admin;
import member.model.vo.Member;

public class MemberDao {

	public int searchAdmin(Connection conn, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select count(admin_id) from tb_admin where admin_id = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				result = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public Admin loginAdmin(Connection conn, String userId, String password) {
		Admin admin = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query ="select * from tb_admin where admin_id = ? and admin_pwd = ?";
		try {
			pstmt= conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			
			rset= pstmt.executeQuery();
			
			while(rset.next()) {
				String adminId = rset.getString(1);
				String adminPwd = rset.getString(2);
				String adminName = rset.getString(3);
				admin = new Admin(adminId, adminPwd, adminName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return admin;
	}

	public Member loginMember(Connection conn, String userId, String password) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query ="select * from tb_user where user_id = ? and user_pwd = ?";
		try {
			pstmt= conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			
			rset= pstmt.executeQuery();
			
			while(rset.next()) {
				
				String userPwd = rset.getString(2);
				String userName = rset.getString(3);
				String phone = rset.getString(4);
				Date registDate = rset.getDate(5);
				Date lastModified = rset.getDate(6);
				int branchNo = rset.getInt(7);
				member = new Member(userId, userPwd, userName, phone, registDate, lastModified, branchNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return member;
	}

	public int selectCheckId(Connection conn, String userId) {
		int  result = 0;
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String query = "select count(user_id) from tb_user where user_id = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public int insertMember(Connection conn, Member member) {
		int result=0;
		PreparedStatement pstmt =null;
		String query ="insert into tb_user values(?, ?, ?, ?,sysdate,sysdate,0)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			pstmt.setString(3, member.getUserName());
			pstmt.setString(4, member.getPhone());
			result =pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public Member selectMember(Connection conn, String userId) {
		Member member = new Member();
		PreparedStatement pstmt= null;
		ResultSet rset = null;
		String query = "select * from tb_user where user_id = ?";
		try {
			pstmt  = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				String userPwd = rset.getString(2);
				String userName = rset.getString(3);
				String phone = rset.getString(4);
				Date registDate = rset.getDate(5);
				Date lastModified = rset.getDate(6);
				int branchNo = rset.getInt(7);
				member = new Member(userId, userPwd, userName, phone, registDate, lastModified, branchNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return member;
	}

	public int memberUpdate(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt= null;
		String qurey ="update tb_user set user_pwd =?, phone =?, lastmodified = sysdate where user_id = ?";
		try {
			pstmt= conn.prepareStatement(qurey);
			pstmt.setString(1, member.getUserPwd());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getUserId());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public ArrayList<Member> selectMemberAll(Connection conn) {
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt= null;
		ResultSet rset = null;
		String query = "select * from tb_user order by regist_date desc";
		try {
			pstmt  = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				String userId = rset.getString(1);
				String userPwd = rset.getString(2);
				String userName = rset.getString(3);
				String phone = rset.getString(4);
				Date registDate = rset.getDate(5);
				Date lastModified = rset.getDate(6);
				int branchNo = rset.getInt(7);
				list.add(new Member(userId, userPwd, userName, phone, registDate, lastModified, branchNo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

}
