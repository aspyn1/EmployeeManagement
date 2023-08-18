package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static edu.kh.emp.common.JDBCTemplate.*;

import edu.kh.emp.model.vo.Employee;

public class EmployeeDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs = null;
	
	private Properties prop;

	public EmployeeDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML( new FileInputStream("query.xml") );
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** 전체 사원 정보 조회 DAO
	 * @param conn
	 */
	public List<Employee> selectAll(Connection conn) throws Exception{
		
		// 결과 저장용 변수 선언
		List<Employee> empList = new ArrayList<Employee>();
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			// Statement 객체 생성
			
			stmt = conn.createStatement();
			
			// SQL을 수행 후 결과(ResultSet) 반환 받음
			rs = stmt.executeQuery(sql);
			
			// 조회 결과를 얻어와 한 행씩 접근하여
			// Employee 객체 생성 후 컬럼값 담기
			// -> List 추가
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				// EMP_ID 컬럼은 문자열 컬럼이지만
				// 저장된 값들이 모두 숫자 형태
				// -> DB에서 자동으로 형변환 진행해서 얻어옴
				
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, 
						email, phone, departmentTitle, jobName, salary );
				
				empList.add(emp); // List 담기
			
			} // while문 종료
			
			
		} finally {
			
			close(stmt);
			
		}
		
		// 결과 반환
		return empList;
		
		
	}

	/** 회원 정보 추가 DAO
	 * @param conn
	 * @param emp1
	 * @return
	 * @throws SQLException
	 */
	public int insert(Connection conn, Employee emp1) throws SQLException{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("insert");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, emp1.getEmpId());
			pstmt.setString(2, emp1.getEmpName());
			pstmt.setString(3, emp1.getEmpNo());
			pstmt.setString(4, emp1.getEmail());
			pstmt.setString(5, emp1.getPhone());
			pstmt.setString(6, emp1.getDepartmentTitle());
			pstmt.setString(7, emp1.getJobName());
			pstmt.setInt(8, emp1.getSalary());
			
			result = pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		return result;
	}

	/** 입력받은 부서와 일치하는 모든 사원 정보 조회 DAO
	 * @param conn
	 * @param departmentTitle
	 * @return
	 */
	public List<Employee> selectDeptEmp(Connection conn, String departmentTitle) throws SQLException{
		
		List<Employee> empList = new ArrayList<Employee>();
		
		try {
			String sql = prop.getProperty("selectDeptEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, departmentTitle);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
//				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, salary);
				
				empList.add(emp);
				
			}
			
		}finally {
			close(stmt);
			
		}
		
		return empList;
	}

	/** 입력받은 급여 이상을 받는 모든 사원 정보 조회 DAO
	 * @param conn
	 * @param salary
	 * @return
	 */
	public List<Employee> selectSalatyEmp(Connection conn, int salary) throws Exception{
		
		List<Employee> empList = new ArrayList<Employee>();
		
		try {
			String sql = prop.getProperty("selectSalatyEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, salary);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int selectSalary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, selectSalary);
				
				empList.add(emp);
				
			}
			
		}finally {
			close(stmt);
			
		}
		
		return empList;
	}

	/** 부서별 급여 합 전체 조회 DAO
	 * @return
	 */
	public Map<String, Integer> selectDeptTotalSalary() throws Exception{
		
		Map<String, Integer> map = new LinkedHashMap<String, Integer> ();
		// LinkedHashMap : key순서가 유지되는 HashMap (ORDER BY절 정렬결과 그대로 저장함)
		
		try {
			
			String sql = prop.getProperty("selectDeptTotalSalary");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String deptCode = rs.getString("DEPT_CODE");
				int total = rs.getInt("TOTAL");
				
				map.put(deptCode, total);
				
			}
			
		}finally {
			
			close(stmt);
		}
		
		return map;
	}



	/** 주민등록번호가 일치하는 사원 정보 조회 DAO
	 * @param conn
	 * @param empNo
	 * @return
	 * @throws Exception
	 */
	public Employee selectEmpNo(Connection conn, String empNo) throws Exception{
		
		Employee emp = null;
		
		try {
			String sql = prop.getProperty("selectEmpNo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, empNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
//				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(empId, empName, empNo, email, phone, departmentTitle, jobName, salary);
				
			}
			
		}finally{
			close(conn);
		}
		
		result emp;
	}

	public Map<String, Double> selectJobAvgSalary(Connection conn) throws Exception{
		
		
		Map<String, Double> map = new LinkedHashMap<>();
		
		try {
			
			String sql = prop.getProperty("selectJobAvgSalary");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String jobName = rs.getString("JOB_NAME");
				double average = rs.getDouble("AVERAGE");
				
				map.put(jobName, average);
			}
			
			
		}finally {
			
			close(stmt);
		}
		
		return map;
	}
























	
	
}
