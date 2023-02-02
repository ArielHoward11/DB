package temp;

import java.sql.*;

public class DBtemp {
	public static void main (String[] args) {
		Test o= new Test();
		Connection conn;
		Statement stmt=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/JAVA","root","festa0204");
			System.out.println("성공");
			stmt=conn.createStatement();
			
			/*
			//학번 중복검사
			System.out.printf("%d",o.studentIdCheck(stmt, "0000008"));
			*/
			/*
			//학생 추가 쿼리문
			stmt.executeUpdate("insert into STUDENT values('0000005-2-2','0000005','db테스트용','0','testpswd','2','2');");
			*/
			/*
			//로그인 기능(학번 비밀번호가 일치하는 컬럼이 있는지 검사)
			System.out.printf("%d",o.studentIdPassCheck(stmt, "0000001","festa0204"));
			*/
			/*
			해당 학생이 듣는 모든 과목의 이름,장소 출력
			o.printAllsubject(stmt);
			*/
			//테스트ㅇㅇ
			o.printSubjectData(stmt);
		} catch(ClassNotFoundException e) {
			System.out.println("DB에러");
		} catch(SQLException e) {
			System.out.println("에러");
		}

	}
}
class Test{
	void printAllStudent(Statement stmt, String col1) throws SQLException{
		ResultSet srs = stmt.executeQuery("select * from STUDENT");
		while(srs.next()) {
			if(col1!="") {
				System.out.print(new String(srs.getString("student_name")));
				System.out.println();
			}
			else
				System.out.println();
		}
	}
	void printAllsubject(Statement stmt)throws SQLException{
		ResultSet srs = stmt.executeQuery("select distinct subject_name, place "
				+ "from (select class_code "
					+ "from STUDENT,TAKES "
					+ "where TAKES.student_code='0000001-2-2' and TAKES.student_code=STUDENT.student_code) as sbcode,SUBJECT,SECTION "
				+ "where sbcode.class_code=SECTION.class_code and SUBJECT.subject_code=SECTION.subject_code;"); //임시

		while(srs.next()) {
			System.out.print(new String(srs.getString("subject_name"))+" ");
			System.out.print(new String(srs.getString("place"))+" \n");
		}
		
	}
	void printSubjectData(Statement stmt)throws SQLException{
		
		ResultSet srs = stmt.executeQuery("select distinct subject_name, place, subject_type, pro_name, subject_time "
				+ "from (select class_code from STUDENT,TAKES where TAKES.student_code='0000001-2-2' and TAKES.student_code=STUDENT.student_code) as sbcode,SUBJECT,SECTION "
				+ "where sbcode.class_code=SECTION.class_code and SUBJECT.subject_code=SECTION.subject_code;");

		ResultSet srs2 = stmt.executeQuery("select memo from TAKES,STUDENT where TAKES.student_code = '0000001-2-2' and TAKES.student_code=STUDENT.student_code;");

		while(srs.next()) {
			//srs2.next();
			System.out.print(new String(srs.getString("subject_name"))+" ");
			System.out.print(new String(srs.getString("place"))+" ");
			System.out.print(new String(srs.getString("subject_type"))+" ");
			System.out.print(new String(srs.getString("pro_name"))+" ");
			System.out.print(new String(srs.getString("subject_time"))+" ");
			//System.out.print(new String(srs2.getString("memo"))+"\n");
		}
		
	}
	int studentIdCheck(Statement stmt,String number)throws SQLException{
		String str = "select student_number from STUDENT where student_number = '";
		ResultSet srs = stmt.executeQuery(str + number + "';");
		while(srs.next()) {
			if(number.equals(srs.getString("student_number")))
				return 1;
		}
		return 0;
	}
	int studentIdPassCheck(Statement stmt,String id, String pass)throws SQLException{
		String str = "select student_number, student_password from STUDENT where student_number = '";
		ResultSet srs = stmt.executeQuery(str + id + "' and student_password = '" + pass + "';");
		while(srs.next()) {
			if(id.equals(srs.getString("student_number"))&&pass.equals(srs.getString("student_password")));
				return 1;
		}
		return 0;
	}
}
