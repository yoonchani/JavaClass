package clothes;

import java.util.Scanner;

import clothes.ShopSQL;
import clothes.Client;

public class ShopMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Client ct = new Client();
		ShopSQL ssql = new ShopSQL();
		boolean run = true;
		// 시작하면 DB접속
		ssql.connect();

		do {
			System.out.println();
			System.out.println("   ☆★  겸신사에 오신것을 환영합니다  ☆★");
			System.out.println("=============================");
			System.out.println("① 회원가입       ② 로그인        ③ 종료");
			System.out.println("       (로그인후 주문가능)");
			System.out.println("=============================");
			System.out.print("메뉴선택 >> ");
			int menu = sc.nextInt();		// 회원가입과 로그인, 종료 중 원하는 번호 입력

			switch (menu) {
			case 1:
				ssql.newMember();				// 회원가입 메소드로 이동
				break;
			case 2:
				System.out.print("회원번호>> ");	// 회원번호화
				int id = sc.nextInt();
				System.out.print("비밀번호>> ");	// 비밀번호 입력받으면
				String pw = sc.next();

				// 회원번호와 비밀번호가 맞는지 확인하는 메소드의 bool타입 리턴값을 idc에 저장
				boolean idc = ssql.idcheck(id, pw);	
				
				if (idc) {						// idc = true 면 실행
					System.out.println("=====================");
					System.out.println("① 상의     ② 하의    ③ 주문목록");
					System.out.println("=====================");
					System.out.print("어떤 종류를 찾으세요>> ");
					int menu1 = sc.nextInt();	// 상의 구매, 하의 구매, 주문목록 조회 중에 선택
					switch (menu1) {
					case 1:
						ssql.list("상의"); // TOP 테이블의 내용을 출력하는 메소드
						System.out.print("상품명 >> ");	// list에서 원하는 상품의 정보를 입력
						String name = sc.next();		// 입력받은 정보를 각각의 변수에 저장
						
						System.out.print("사이즈 >> ");
						String size = sc.next();
						
						System.out.print("색상 >> ");
						String color = sc.next();
						
						System.out.print("가격 >> ");
						String price = sc.next();
						
						boolean payc = ssql.pay2(name);	// 결제수단 선택 메소드의 리턴값을 payc에 저장
						if(payc) {		// payc = true 면
							System.out.println("결제하실 금액은 " +  ssql.cost(name, menu1) + "입니다.");
							ssql.order(id, name, size, color, price); 
						}				// ORDER테이블에 입력정보 저장
						break;
					case 2:
						ssql.list("하의");	// PANTS 테이블의 내용을 출력하는 메소드
						System.out.print("상품명 >> "); // list 중 원하는 상품 정보 입력
						String name1 = sc.next();	 // 입력받은 정보를 변수에 각각 저장
						
						System.out.print("사이즈 >> ");
						String size1 = sc.next();
						
						System.out.print("색상 >> ");
						String color1 = sc.next();
						
						System.out.print("가격 >> ");
						String price1 = sc.next();
						payc = ssql.pay2(name1);	// pay2 메소드에 상품이름을 가져감
						if(payc) {
							System.out.println("결제하실 금액은 " +  ssql.cost(name1, menu1) + "입니다.");
							ssql.order(id, name1, size1, color1, price1);
						}
						break;
					case 3:
						ssql.orderlist(id);		// 로그인한 회원번호를 가져가서 그 회원이 주문했던 
						break;					// 목록을 출력하는 메소드로 넘어감
					default:
						System.out.println("잘못 입력하셨습니다!");
						break;
					}
				}
				break;
			case 3:
				// 해제
				ssql.conClose();
				System.out.println("시스템을 종료합니다!");
				run = false;
				break;
			default:
				System.out.println("1~5를 입력해주세요!");
				break;
			}
		} while (run);
	}
}