package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.sbs.exam.app.dto.Article;

public class ArticleRepository {

	public static Article getArticleById(int id, List<Article> articles) {
		for(Article article : articles) {
			if(article.id == id) {
				return article;
			}
		}
		return null;
	}
	
	
	
}
