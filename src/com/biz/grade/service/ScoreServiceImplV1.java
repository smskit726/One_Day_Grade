package com.biz.grade.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.grade.vo.ScoreVO;
import com.biz.grade.vo.StudentVO;

public class ScoreServiceImplV1 implements ScoreService {
	protected StudentServiceImplV1 stdService;
	protected List<ScoreVO> scoreList;
	protected List<StudentVO> studentList;
	protected Scanner scan;
	protected String scoreFileName;

	public ScoreServiceImplV1() {
		scoreList = new ArrayList<ScoreVO>();
		scan = new Scanner(System.in);
		scoreFileName = "src/com/biz/grade/data/score.txt";

		stdService = new StudentServiceImplV1();
		stdService.loadStudent();
		studentList = stdService.getStudentList();

	}

	@Override
	public void loadScore() {
		FileReader fileReader = null;
		BufferedReader buffer = null;

		try {
			fileReader = new FileReader(scoreFileName);
			buffer = new BufferedReader(fileReader);

			String reader = "";

			while (true) {
				reader = buffer.readLine();

				if (reader == null) {
					break;
				}

				String[] score = reader.split(":");

				ScoreVO scoreVO = new ScoreVO();

				scoreVO.setStdNum(score[0]);
				scoreVO.setIntKor(Integer.valueOf(score[1]));
				scoreVO.setIntEng(Integer.valueOf(score[2]));
				scoreVO.setIntMath(Integer.valueOf(score[3]));
				scoreVO.setIntMusic(Integer.valueOf(score[4]));

				scoreList.add(scoreVO);

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
	public boolean inputScore() {
		ScoreVO scoreVO = new ScoreVO();

		System.out.print("학번 >> ");
		String stdNum = scan.nextLine();

		/*
		for (StudentVO stdVO : studentList) {
			if (stdVO.getStdNum().equals(stdNum)) {
				break;
			} else {
				System.out.println("학생정보가 없습니다. 학생정보를 먼저 입력해주세요 :(");
				return false;
			}
		}
		*/

		int size = studentList.size();
		int index = 0;
		for (index = 0; index < size; index++) {
			if (studentList.get(index).getStdNum().equals(stdNum)) {
				break;
			}
		}
		if (index > size) {
			System.out.println("학생정보가 없습니다. 학생정보를 먼저 입력해주세요 :(");
			return false;
		}
		
		System.out.print("국어 >> ");
		String strKor = scan.nextLine();

		int intKor = 0;
		try {
			intKor = Integer.valueOf(strKor);
		} catch (Exception e) {
			System.out.println("성적은 숫자만 입력가능합니다 :(");
		}

		System.out.print("영어 >> ");
		String strEng = scan.nextLine();

		int intEng = 0;
		try {
			intEng = Integer.valueOf(strEng);
		} catch (Exception e) {
			System.out.println("성적은 숫자만 입력가능합니다 :(");
		}

		System.out.print("수학 >> ");
		String strMath = scan.nextLine();

		int intMath = 0;
		try {
			intMath = Integer.valueOf(strMath);
		} catch (Exception e) {
			System.out.println("성적은 숫자만 입력가능합니다 :(");
		}

		System.out.print("음악 >> ");
		String strMusic = scan.nextLine();

		int intMusic = 0;
		try {
			intMusic = Integer.valueOf(strMusic);
		} catch (Exception e) {
			System.out.println("성적은 숫자만 입력가능합니다 :(");
		}

		scoreVO.setStdNum(stdNum);
		scoreVO.setIntKor(intKor);
		scoreVO.setIntEng(intEng);
		scoreVO.setIntMath(intMath);
		scoreVO.setIntMusic(intMusic);

		scoreList.add(scoreVO);

		return true;

	}

	@Override
	public void calcSum() {
		int intSum = 0;
		for (ScoreVO scoreVO : scoreList) {
			intSum = scoreVO.getIntKor() + scoreVO.getIntEng() + scoreVO.getIntMath() + scoreVO.getIntMusic();
			scoreVO.setIntSum(intSum);
		}

	}

	@Override
	public void calcAvg() {
		int subNum = 4;
		int intAvg = 0;
		for (ScoreVO scoreVO : scoreList) {
			intAvg = scoreVO.getIntSum() / subNum;
			scoreVO.setIntAvg(intAvg);
		}

	}

	@Override
	public void saveScore() {
		PrintStream outPut = null;

		try {
			outPut = new PrintStream(scoreFileName);

			for (ScoreVO scoreVO : scoreList) {
				outPut.printf("%s:%d:%d:%d:%d\n", scoreVO.getStdNum(), scoreVO.getIntKor(), scoreVO.getIntEng(),
						scoreVO.getIntMath(), scoreVO.getIntMusic());
			}
			outPut.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean scoreList() {
		System.out.println("===============================================================");
		System.out.println("성적일람표");
		System.out.println("===============================================================");
		System.out.println("학번\t이름\t국어\t영어\t수학\t음악\t총점\t평균");
		System.out.println("---------------------------------------------------------------");

		for (ScoreVO scoreVO : scoreList) {
			System.out.print(scoreVO.getStdNum() + "\t");

			for (StudentVO stdVO : studentList) {
				if (scoreVO.getStdNum().equals(stdVO.getStdNum())) {
					System.out.print(stdVO.getName() + "\t");
					break;
				}
			}

			System.out.print(scoreVO.getIntKor() + "\t");
			System.out.print(scoreVO.getIntEng() + "\t");
			System.out.print(scoreVO.getIntMath() + "\t");
			System.out.print(scoreVO.getIntMusic() + "\t");
			System.out.print(scoreVO.getIntSum() + "\t");
			System.out.print(scoreVO.getIntAvg() + "\n");

		}

		int korSum = 0;
		int engSum = 0;
		int mathSum = 0;
		int musicSum = 0;
		int sumSum = 0;
		int avgSum = 0;
		int scSize = scoreList.size();

		if (scSize == 0) {
			System.out.println("입력된 성적이 없습니다.");
			return false;
		}

		for (ScoreVO scoreVO : scoreList) {
			korSum += scoreVO.getIntKor();
			engSum += scoreVO.getIntEng();
			mathSum += scoreVO.getIntMath();
			musicSum += scoreVO.getIntMusic();
			sumSum += scoreVO.getIntSum();
			avgSum += scoreVO.getIntAvg();
		}

		int korAvg = korSum / scSize;
		int engAvg = engSum / scSize;
		int mathAvg = mathSum / scSize;
		int musicAvg = musicSum / scSize;
		int sumAvg = sumSum/scSize;
		int avgAvg = avgSum/scSize;

		System.out.println("---------------------------------------------------------------");
		System.out.printf("과목총점\t%d\t%d\t%d\t%d\t%d\t%d\n", korSum, engSum, mathSum, musicSum,sumSum,avgSum);
		System.out.printf("과목평균\t%d\t%d\t%d\t%d\t%d\t%d\n", korAvg, engAvg, mathAvg, musicAvg,sumAvg,avgAvg);
		System.out.println("===============================================================");

		return true;

	}

}
