package com.biz.grade.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.grade.vo.StudentVO;

public class StudentServiceImplV1 implements StudentService {
	protected List<StudentVO> studentList;
	protected Scanner scan;
	protected String stdFileName = "";

	public List<StudentVO> getStudentList() {
		return studentList;
	}

	public StudentServiceImplV1() {
		studentList = new ArrayList<StudentVO>();
		scan = new Scanner(System.in);
		stdFileName = "src/com/biz/grade/data/student.txt";
	}

	@Override
	public void loadStudent() {
		FileReader fileReader = null;
		BufferedReader buffer = null;

		try {
			fileReader = new FileReader(stdFileName);
			buffer = new BufferedReader(fileReader);

			String reader = "";

			while (true) {
				reader = buffer.readLine();

				if (reader == null) {
					break;
				}

				String[] student = reader.split(":");

				StudentVO stdVO = new StudentVO();

				stdVO.setStdNum(student[0]);
				stdVO.setName(student[1]);
				stdVO.setDept(student[2]);
				stdVO.setGrade(Integer.valueOf(student[3]));
				stdVO.setTel(student[4]);

				studentList.add(stdVO);
			}
			buffer.close();
			fileReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("파일을 찾을 수 없습니다 :(");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void inputStudent() {

		StudentVO stdVO = new StudentVO();

		System.out.print("학번 (정수 5자리 이내) >> ");
		String stdNum = scan.nextLine();

		System.out.print("이름 >> ");
		String name = scan.nextLine();

		System.out.print("학과 >> ");
		String dept = scan.nextLine();

		System.out.print("학년 >> ");
		String strGrade = scan.nextLine();

		int intGrade = 0;
		while (true) {
			try {
				intGrade = Integer.valueOf(strGrade);
				break;
			} catch (Exception e) {
				System.out.println("학년은 숫자만 입력 가능합니다. 다시 입력해주세요 :(");
			}
		}

		System.out.print("전화번호 >> ");
		String strTel = scan.nextLine();

		stdVO.setStdNum(stdNum);
		stdVO.setName(name);
		stdVO.setDept(dept);
		stdVO.setGrade(intGrade);
		stdVO.setTel(strTel);

		studentList.add(stdVO);

	}

	@Override
	public void saveStudent() {

		PrintStream outPut = null;

		try {
			outPut = new PrintStream(stdFileName);

			for (StudentVO stdVO : studentList) {
				outPut.printf("%s:%s:%s:%d:%s\n", stdVO.getStdNum(), stdVO.getName(), stdVO.getDept(), stdVO.getGrade(),
						stdVO.getTel());
			}
			outPut.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean studentList() {
		System.out.println("===============================================================");
		System.out.println("학생정보");
		System.out.println("===============================================================");
		System.out.println("학번\t이름\t학과\t\t학년\t전화번호");
		System.out.println("---------------------------------------------------------------");

		int stdSize = studentList.size();

		if (stdSize == 0) {
			System.out.println("입력된 학생정보가 없습니다.");
			return false;
		}
		
		for(StudentVO stdVO : studentList) {
			System.out.print(stdVO.getStdNum() +"\t");
			System.out.print(stdVO.getName() + "\t");
			System.out.print(stdVO.getDept() + "\t");
			System.out.print(stdVO.getGrade() + "\t");
			System.out.print(stdVO.getTel() + "\n");
		}

		System.out.println("===============================================================");
		
		return true;

	}

}
