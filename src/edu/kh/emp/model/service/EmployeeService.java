package edu.kh.emp.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;
import edu.kh.emp.view.EmployeeView;

public class EmployeeService {

	private EmployeeDAO dao = new EmployeeDAO();
	private EmployeeView view = new EmployeeView();
	
	/** 전체 사원 정보 조회 서비스
	 * 
	 */
	public List<Employee> selectAll() throws Exception{
		
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectAll(conn);
		
		close(conn);
		
		return list;
		
	}

	public int insertEmployee(Employee emp1) throws SQLException{
		
		Connection conn = getConnection();
		
		int result = dao.insert(conn, emp1);
		
		if(result > 0) commit(conn);
		else			rollback(conn);
		
		close(conn);

		return result;
	}

	/** 입력받은 부서와 일치하는 모든 사원 정보 조회 service
	 * @param departmentTitle
	 * @return
	 */
	public List<Employee> selectDeptEmp(String departmentTitle) throws SQLException{
		
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectDeptEmp(conn, departmentTitle);
		
		close(conn);
		
		return list;
	}

	/** 입력받은 급여 이상을 받는 모든 사원 정보 조회 service
	 * @param salary
	 * @return
	 */
	public List<Employee> selectSalatyEmp(int salary) throws Exception{
		
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectSalatyEmp(conn, salary);
		
		close(conn);
		
		return list;
	}

	/** 부서별 급여 합 전체 조회 sevice
	 * @return
	 */
	public Map<String, Integer> selectDeptTotalSalary() throws Exception{
		
		Connection conn = getConnection();
		
		Map<String, Integer> map = dao.selectDeptTotalSalary(conn);
		
		close(conn);
		
		return map;
	}

	/** 주민등록번호가 일치하는 사원 정보 조회 service
	 * @return
	 */
	public Employee selectEmpNo(String empNo) throws Exception{
		
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpNo(conn, empNo);
		
		close(conn);
		
		return emp;
	}

	public Map<String, Double> selectJobAvgSalary() throws Exception{
		
		Connection conn = getConnection();
		
		Map<String, Double> map = dao.selectJobAvgSalary(conn);
		
		close(conn);
		
		return map;
	}
	






















	

}
