package com.biz.grade.exec;

import java.util.Scanner;

import com.biz.grade.service.ScoreService;
import com.biz.grade.service.ScoreServiceImplV1;
import com.biz.grade.service.StudentService;
import com.biz.grade.service.StudentServiceImplV1;

public class ScoreEx_01 {
	public static void main(String[] args) {
		
		StudentService stdService = new StudentServiceImplV1();
		ScoreService scService = new ScoreServiceImplV1();
		Scanner scan = new Scanner(System.in);
		
		stdService.loadStudent();
		scService.loadScore();
		
		while(true) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("성적처리시스템 V1");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("1. 학생정보 입력");
			System.out.println("2. 학생성적 입력");
			System.out.println("3. 학생정보 출력");
			System.out.println("4. 학생성적 출력");
			System.out.println("-1. 업무종료");
			System.out.println("--------------------------------------------------------------");
			System.out.print("업무선택 >> ");
			
			String strMenu = scan.nextLine();
			System.out.println();

			int intMenu = 0;

			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("메뉴선택은 숫자로만 입력하세요");
				continue;
			}

			if (intMenu == -1) {
				break;
			} else if (intMenu < 1 || intMenu > 4) {
				System.out.println("선택된 업무가 없습니다.");
				continue;
			}
			
			if(intMenu == 1) {
				stdService.inputStudent();
				stdService.saveStudent();
				
			} else if (intMenu ==2 ) {
				if(!scService.inputScore()) {
					continue;
				}
				scService.calcSum();
				scService.calcAvg();
				scService.saveScore();
				
			} else if (intMenu == 3) {
				if(!stdService.studentList()) {
					continue;
				}
				
			} else {
				if(!scService.scoreList()) {
					continue;
				}
			}
		}
	
		
	}
}
