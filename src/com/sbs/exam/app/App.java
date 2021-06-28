package com.sbs.exam.app;

import java.util.Scanner;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.controller.UsrArticleController;

public class App {
	
	Scanner sc;
	
	App(){
		sc = Container.getSc();
	}
	
	public void run() {
		System.out.println("== 텍스트 게시판 시작 ==");
		
		UsrArticleController usrArticleController = new UsrArticleController();

		while (true) {
			System.out.printf("명령어) ");

			String command = sc.nextLine().trim();

			Rq rq = new Rq(command);

			if(("/" + rq.getControllerTypeName() + "/" + rq.getControllerName()).equals("/usr/article")) {
				usrArticleController.performAction(rq);
			} else if (rq.getActionPath().equals("/system/exit")) {
				System.out.println("프로그램을 종료 합니다.");
				break;
			} else {
				System.out.println("올바른 명령어가 아닙니다.");;
				return;
			}
		}

		System.out.println("== 텍스트 게시판 끝 ==");
	}

}
