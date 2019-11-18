package dbpkg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO extends DAOBase{
	private static DAO instance = new DAO();
	public static DAO getInstance() {
		return instance;
	}
	
	private DAO() {}

	//readOne 값하나 받기 update.jsp
	public MemberVO getMember(int custno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		MemberVO vo = null;
		try {
			conn = getConnection();
			sql = "select custno, custname, phone, address, to_char(joindate, 'YYYY-MM-dd'), grade, city"
					+ " from member_tbl_04"
					+ " where custno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, custno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new MemberVO();
				vo.setCustno(rs.getInt(1));
				vo.setCustname(rs.getString(2));
				vo.setPhone(rs.getString(3));
				vo.setAddress(rs.getString(4));
				vo.setJoindate(rs.getString(5));
				vo.setGrade(rs.getString(6));
				vo.setCity(rs.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBResource(rs, pstmt, conn);
		}
		return vo;
	}
//	pstmt = conn.prepareStatement(sql);
//	pstmt.setInt(1, num);
//	rs = pstmt.executeQuery();
//	if(rs.next()) {
//		vo.setCustno(Integer.parseInt(rs.getString(1)));
//		vo.setCustname(rs.getString(2));
//		vo.setPhone(rs.getString(3));
//		vo.setAddress(rs.getString(4));
//		vo.setJoindate(rs.getString(5));
//		vo.setGrade(rs.getString(6));
//		vo.setCity(rs.getString(7));
//	}
	
	//custno 숫자 출력 insert.jsp
	public int getCustno() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int custno = 0;
		try {
			conn = getConnection();
			sql = "select max(custno)+1 from member_tbl_04";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				custno = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBResource(rs, pstmt, conn);
		}
		return custno;
	}
	
	
	
	//memberList.jsp memberList 조회
	public ArrayList<MemberVO> memList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<MemberVO> list = null;
		try {
			conn = getConnection();
			sql = "select custno, custname, phone, address, to_char(joindate, 'YYYY-MM-dd'), grade, city " + 
					" from member_tbl_04";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<MemberVO>();
				do {
					MemberVO vo = new MemberVO();
					vo.setCustno(rs.getInt(1));
					vo.setCustname(rs.getString(2));
					vo.setPhone(rs.getString(3));
					vo.setAddress(rs.getString(4));
					vo.setJoindate(rs.getString(5));
					if (rs.getString(6).equals("A")) {
						vo.setGrade("VIP");
					} else if (rs.getString(6).equals("B")) {
						vo.setGrade("일반");
					} else if (rs.getString(6).equals("C")) {
						vo.setGrade("직원");
					}
					vo.setCity(rs.getString(7));
					list.add(vo);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBResource(rs, pstmt, conn);
		}
		return list;
	}
	
	
	
	
	//회원매출조회 priceList.jsp
	public ArrayList<PriceVO> priceList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<PriceVO> list = null;
		try {
			conn = getConnection();
			sql = " select me.custno, me.custname, me.grade, sum(mo.price) as total_price"
					+ " from member_tbl_04 me"
					+ " join money_tbl_04 mo"
					+ " on (me.custno = mo.custno)"
					+ " group by me.custno, me.custname, me.grade"
					+ " order by total_price desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<PriceVO>();
				do {
					PriceVO vo = new PriceVO();
					vo.setCustno(rs.getInt(1));
					vo.setCustname(rs.getString(2));
//					vo.setGrade(rs.getString(3));
					if (rs.getString(3).equals("A")) {
						vo.setGrade("VIP");
					} else if (rs.getString(3).equals("B")) {
						vo.setGrade("일반");
					} else if (rs.getString(3).equals("C")) {
						vo.setGrade("직원");
					}
					vo.setTotalprice(rs.getInt(4));
					list.add(vo);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBResource(rs, pstmt, conn);
		}
		return list;
	}
	
	public int memUpdate(MemberVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int rs = 0;
		try {
			conn = getConnection();
			sql = "update MEMBER_TBL_04 set "
					+ " custname=?, phone=?, address=?, grade=?, city=? "
					+ " where custno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getCustname());
			pstmt.setString(2, vo.getPhone());
			pstmt.setString(3, vo.getAddress());
			pstmt.setString(4, vo.getGrade());
			pstmt.setString(5, vo.getCity());
			pstmt.setInt(6, vo.getCustno());
			rs = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBResource(pstmt, conn);
		}
		return rs;
	}
	//member insert등록
	public int memInsert(MemberVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int rs = 0;
		try {
			conn = getConnection();
			sql = "insert into member_tbl_04 values (?, ?, ?, ?,to_date(?, 'YYYYMMdd'),?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getCustno());
			pstmt.setString(2, vo.getCustname());
			pstmt.setString(3, vo.getPhone());
			pstmt.setString(4, vo.getAddress());
			pstmt.setString(5, vo.getJoindate());
			pstmt.setString(6, vo.getGrade());
			pstmt.setString(7, vo.getCity());
			rs = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBResource(pstmt, conn);
		}
		return rs;
	}
	

	
}
