package com.sbs.exam.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sbs.exam.app.Rq;
import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Article;
import com.sbs.exam.app.repository.ArticleRepository;
import com.sbs.exam.util.Util;

public class UsrArticleController {
	private List<Article> articles;
	private int articlesLastId;
	private Scanner sc;
	
	public UsrArticleController(){
		articles = new ArrayList<>();
		articlesLastId = 0;
		sc = Container.getSc();
		
		makeTestArticles();
	}

	private void makeTestArticles() {
		for (int i = 0; i < 10; i++) {
			Article article = new Article();
			article.id = articlesLastId + 1;
			article.regDate = Util.getNowDateStr();
			article.updateDate = Util.getNowDateStr();
			article.title = "제목 " + article.id;
			article.body = "내용 " + article.id;
			articles.add(article);
			articlesLastId++;
		}
	}

	public void performAction(Rq rq) {
		if (rq.isValid == false) {
			System.out.printf("명령어가 올바르지 않습니다.\n");
			return;
		}
		if (rq.getActionPath().equals("/usr/article/write")) {
			actionWrite(rq);
		} else if (rq.getActionPath().equals("/usr/article/list")) {
			actionList(rq);
		} else if (rq.getActionPath().equals("/usr/article/detail")) {
			actionDetail(rq);
		} else if (rq.getActionPath().equals("/usr/article/delete")) {
			actionDelete(rq);
		}else if (rq.getActionPath().equals("/usr/article/modify")) {
			actionModify(rq);
		} 
	}

	private void actionModify(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			System.out.println("id를 입력해주세요.");
			return;
		}

		Article foundArticle = ArticleRepository.getArticleById(id, articles);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("새 제목 입력 : ");
		String title = sc.nextLine().trim();
		System.out.printf("새 내용 입력 : ");
		String body = sc.nextLine().trim();
		
		foundArticle.title = title;
		foundArticle.body = body;
		foundArticle.updateDate = Util.getNowDateStr();
		
		System.out.println(foundArticle.id + "번 게시물 수정 완료");
	}

	private void actionDelete(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			System.out.println("id를 입력해주세요.");
			return;
		}

		Article foundArticle = ArticleRepository.getArticleById(id, articles);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		articles.remove(foundArticle);

		System.out.printf("%d번 게시물을 삭제하였습니다.\n", id);
	}

	private void actionDetail(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			System.out.println("id를 입력해주세요.");
			return;
		}

		Article foundArticle = ArticleRepository.getArticleById(id, articles);
		
		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("번호 : %d\n", foundArticle.id);
		System.out.printf("작성날짜 : %s\n", foundArticle.regDate);
		System.out.printf("수정날짜 : %s\n", foundArticle.updateDate);
		System.out.printf("제목 : %s\n", foundArticle.title);
		System.out.printf("내용 : %s\n", foundArticle.body);
	}

	private void actionList(Rq rq) {
		System.out.printf("번호 / 작성날자 / 제목\n");

		for (int i = articles.size() - 1; i >= 0; i--) {
			Article article = articles.get(i);
			System.out.printf("%d / %s / %s\n", article.id, article.regDate, article.title);
		}

	}

	private void actionWrite(Rq rq) {
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();

		Article article = new Article();
		article.id = articlesLastId + 1;
		article.regDate = Util.getNowDateStr();
		article.updateDate = Util.getNowDateStr();
		article.title = title;
		article.body = body;
		articles.add(article);

		articlesLastId++;

		System.out.printf("%d번 게시물이 생성되었습니다.\n", article.id);
	}
	
}
