package edu.kh.emp.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.service.EmployeeService;
import edu.kh.emp.model.vo.Employee;


// 화면용 클래스 
/**
 * 
 */
public class EmployeeView {

	private Scanner sc = new Scanner(System.in);
	
	// Service 객체 생성
	private EmployeeService service = new EmployeeService();
	
	// 메인메뉴
	public void displayMenu() {
		
		int input = 0;
		
		do {
			try {
				System.out.println("---------------------------------------------------------");
				System.out.println("----- 사원 관리 프로그램 -----");
				System.out.println("1. 전체 사원 정보 조회");
				System.out.println("2. 새로운 사원 추가");
				System.out.println("3. 사번이 일치하는 사원 정보 조회");
				System.out.println("4. 사번이 일치하는 사원 정보 수정");
				System.out.println("5. 사번이 일치하는 사원 정보 삭제");
				System.out.println("6. 입력 받은 부서와 일치하는 모든 사원 정보 조회");
				System.out.println("7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회");
				System.out.println("8. 부서별 급여 합 전체 조회");
				// selectDeptTotalSalary()
				// DB 조회 결과를 HashMap<String, Integer>에 옮겨 담아서 반환
				// 부서코드, 급여 합 조회
				System.out.println("9. 주민등록번호가 일치하는 사원 정보 조회");
				System.out.println("10. 직급별 급여 평균 조회");
				// selectJobAvgSalary()
				// DB 조회 결과를 HashMap<String, Double>에 옮겨 담아서 반환 
				// 직급명, 급여 평균(소수점 첫째자리) 조회
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 >> ");
				input = sc.nextInt();
				sc.nextLine();
				
				System.out.println();
				
				
				switch(input) {
				case 1:  selectAll();   break;
				case 2:  insertEmployee();  break;
				case 3:  selectEmpId();   break;
				case 4:  updateEmployee();   break;
				case 5:  deleteEmployee();   break;
				case 6:  selectDeptEmp();   break;
				case 7:  selectSalaryEmp();   break;
				case 8:  selectDeptTotalSalary();   break;
				case 9:  selectEmpNo();   break;
				case 10:  selectJobAvgSalary();   break;
				case 0:  System.out.println("프로그램을 종료합니다...");   break;
				default: System.out.println("메뉴에 존재하는 번호만 입력하세요.");
				}
				
				
			}catch(InputMismatchException e) {
				System.out.println("정수만 입력해주세요.");
				input = -1; // 반복문 첫 번째 바퀴에서 잘못 입력하면 종료되는 상황을 방지
				sc.nextLine(); // 입력 버퍼에 남아있는 잘못 입력된 문자열 제거해서
							   // 무한 반복 방지
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}while(input != 0);
	}
	
	/** 주민등록번호가 일치하는 사원 정보 조회
	 * 
	 */
	private void selectEmpNo() throws Exception{
		
		System.out.println("<주민등록번호가 일치하는 사원 정보 조회>");
		
		System.out.print("주민등록번호 : ");
		String empNo = sc.next();
		
		Employee emp = service.selectEmpNo();
		
		printOne(emp);
		
	}

	/** 직급별 급여 평균 조회
	 * 
	 */
	private void selectJobAvgSalary() throws Exception{
		
		System.out.println("< 직급별 급여 평균 조회>");
		
		Map<String, Double> map = service.selectJobAvgSalary();
		
		for(String key : map.keySet()) {
			System.out.println(key + " : " + map.get(key) + "원");
		}
		
	}

	
	/** 부서별 급여 합 전체 조회
	 * 
	 */
	private void selectDeptTotalSalary() throws Exception{
		
		System.out.println("<부서별 급여 합 전체 조회>");
		
		// D1 : 8000000원
		Map<String, Integer> map = service.selectDeptTotalSalary();
		
		for(String key : map.keySet()) {
			
			System.out.println( key + " : " + map.get(key) );
			
		}
		
	}

	/** 입력받은 급여 이상을 받는 모든 사원 정보 조회
	 * 
	 */
	private void selectSalaryEmp() throws Exception{
		System.out.println("<입력받은 급여 이상을 받는 모든 사원 정보 조회>");

		System.out.print("급여 : ");
		int salary = sc.nextInt();
	
		printAll(service.selectSalatyEmp(salary));
		
	}

	/** 입력받은 부서와 일치하는 모든 사원 정보 조회
	 * 
	 */
	private void selectDeptEmp() throws SQLException{
		
		System.out.println("<입력받은 부서와 일치하는 모든 사원 정보 조회>");
		
		System.out.print("부서명 : ");
		String departmentTitle = sc.nextLine();
		
		printAll( service.selectDeptEmp(departmentTitle) );
		
		
	}

	// 주 기능 메서드


	private void insertEmployee(Employee emp1) throws SQLException {
		
		System.out.println("<새로운 사원 추가>");
		
		service.insertEmployee();
	
		
		
		
	}

	private void selectEmpId() {
		// TODO Auto-generated method stub
		
	}
	
	private void updateEmployee() {
		// TODO Auto-generated method stub
		
	}
	
	private void deleteEmployee() {
		// TODO Auto-generated method stub
		
	}



	/** 전체 사원 정보 조회
	 * 
	 */
	private void selectAll() throws Exception {
		System.out.println("<전체 사원 정보 조회>");
		
		List<Employee> empList = service.selectAll();
		
		printAll(empList);
	
	}
	
	
	// 보조 메서드
	
	/** 전달받은 사원 List 모두 출력
	 * 
	 */
	public void printAll(List<Employee> empList) {
		
		if(empList.isEmpty()) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			for(Employee emp : empList) { 
				System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
						emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
						emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
			}
		
		}
		
		return;

	}
	
	
	/** 사원 1명 정보 출력
	 * @param emp
	 */
	public void printOne(Employee emp) {
		if(emp == null) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("사번 |   이름  | 주민 등록 번호 |        이메일        |   전화 번호   | 부서 | 직책 | 급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			
			System.out.printf(" %2d  | %4s | %s | %20s | %s | %s | %s | %d\n",
					emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
					emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
		}


	}
	
	
	/** 사번을 입력받아 반환하는 메서드
	 * @return empId
	 */
	public int inputEmpId() {
		System.out.print("사번 입력 : ");
		int empId = sc.nextInt();
		sc.nextLine();
		return empId;
	}
	
	
}
