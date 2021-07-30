package clothes;

import java.sql.*;
import java.util.Scanner;

public class ShopSQL {
	Connection con = null;
	ResultSet rs = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	Scanner sc = new Scanner(System.in);

	public void connect() {
		con = DBC.DBConnect();
	}

	public void conClose() {
		try {
			con.close();
			System.out.println("접속 해제!");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void newMember() {
		System.out.println("회원정보를 입력해주세요.");

		System.out.print("회원번호(3**)>> ");
		int memberno = sc.nextInt();

		System.out.print("이름>> ");
		String name = sc.next();

		System.out.print("태어난 년도(네자리!)>>");
		String year = sc.next();

		System.out.print("태어난 달(1~12)>> ");
		int month = sc.nextInt();

		System.out.print("태어난 일(1~31)>> ");
		int day = sc.nextInt();

		System.out.print("성별(남/여)>> ");
		String gender = sc.next();

		System.out.print("비밀번호(네자리숫자)>> ");
		String password = sc.next();

		System.out.print("전화번호>> ");
		String phone = sc.next();

		System.out.print("주소>> ");
		String addr = sc.next();

		System.out.print("계좌번호>> ");
		String account = sc.next();

		String month1;
		String day1;

		if (month >= 10) {
			month1 = Integer.toString(month);
		} else {
			month1 = "0" + Integer.toString(month);
		}

		if (day >= 10) {
			day1 = Integer.toString(day);
		} else {
			day1 = "0" + Integer.toString(day);
		}

		String birth = year + month1 + day1;

		new Client(memberno, name, birth, gender, password, phone, addr, account);

		String sql = "INSERT INTO MEMBER VALUES(?,?,TO_DATE(?),?,?,?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, memberno);
			pstmt.setString(2, name);
			pstmt.setString(3, birth);
			pstmt.setString(4, gender);
			pstmt.setString(5, password);
			pstmt.setString(6, phone);
			pstmt.setString(7, addr);
			pstmt.setString(8, account);

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("회원가입 성공!!");
			} else {
				System.out.println("회원가입 실패..");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void list(String table) {
		String sql = null;

		if (table.equals("상의")) {
			sql = "SELECT * FROM TOP";
		} else {
			sql = "SELECT * FROM PANTS";
		}

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				System.out.print("상품명 : " + rs.getString(1));
				System.out.print("\t사이즈 : " + rs.getString(2));
				System.out.print("\t색상 : " + rs.getString(3));
				System.out.print("\t가격 : " + rs.getString(4));
				System.out.println();
			}

		} catch (SQLException e) {
			System.out.println("SQLException 발생!");
			e.printStackTrace();
		}
	}

	public boolean idcheck(int nick, String pw) {
		boolean check = false;

		String sql = "SELECT * FROM MEMBER WHERE MEMBERNO=? AND PASSWORD=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, nick);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				check = true;
			} else {
				System.out.println("회원번호와 비밀번호가 일치하지 않습니다!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return check;
	}

	public void orderlist(int id) {

		String sql = "SELECT * FROM ORDERS WHERE MEMBERNO=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println();
				System.out.print("회원번호 : " + rs.getInt(1));
				System.out.print("\t상품명 : " + rs.getString(2));
				System.out.print("\t사이즈 : " + rs.getString(3));
				System.out.print("\t색상 : " + rs.getString(4));
				System.out.print("\t가격 : " + rs.getString(5));
				System.out.print("\t주소 : " + rs.getString(6));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public String cost(String name1, int table) {
		String price = null;
		String sql = null;
		if (table == 1) {
			sql = "SELECT PPRICE FROM TOP WHERE PNAME = ?";
		} else if (table == 2) {
			sql = "SELECT PPRICE FROM PANTS WHERE PNAME = ?";
		}
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name1);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				price = rs.getString(1);
			} else {
				System.out.println("결제 실패!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return price;
	}

	public void order(int id, String name, String size, String color, String price) {
		String sql = "SELECT ADDR FROM MEMBER WHERE MEMBERNO=?";

		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();
			rs.next();
			String addr = rs.getString(1);

			sql = "INSERT INTO ORDERS VALUES(?,?,?,?,?,?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, size);
			pstmt.setString(4, color);
			pstmt.setString(5, price);
			pstmt.setString(6, addr);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("주문 성공!");
			} else {
				System.out.println("주문 실패...");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public boolean pay2(String name1) {
		boolean phonec = false;

		System.out.println("결제 수단을 선택해주세요!");
		System.out.println("==============================");
		System.out.println(" 1. 카드 결제    2. 계좌이체  3.휴대폰 결제 ");
		System.out.print(">> ");
		int pay = sc.nextInt();
		switch (pay) {
		case 1:
			System.out.println("결제할카드번호를 입력해주세요.");
			System.out.print(">>");
			String card = sc.next();
			System.out.println("고객님의 카드번호는" + card + "입니다");
			phonec = true;
			System.out.println("이용해주셔서 감사합니다.");
			break;

		case 2:
			System.out.println("3333-33-3333333 카카오뱅크");
			System.out.println("위의 계좌로 입금해주시면 감사하겠습니다.");
			phonec = true;
			break;

		case 3:

			System.out.println("휴대폰 결제를 선택 하셨습니다. ");
			System.out.print("휴대폰 번호를 입력해주세요. ");
			System.out.println(">> ");
			String phone = sc.next();

			phonec = phonec(phone);
			break;

		default:
			System.out.println("결제방식을 다시 선택해주세요.");

			break;
		}
		return phonec;
	}

	public boolean phonec(String phone) {
		boolean check = false;

		String sql = "SELECT * FROM MEMBER WHERE PHONE=? ";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, phone);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				check = true;
				System.out.println("휴대폰 결제가 완료되었습니다.");
			} else {
				System.out.println("전화번호를 잘못 입력 하셨습니다.");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return check;
	}
}