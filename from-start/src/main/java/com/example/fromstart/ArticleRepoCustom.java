package com.example.fromstart;

import java.util.List;

public interface ArticleRepoCustom {
   List<Article> search(ArticleSearchCriteria searchCriteria);
}
