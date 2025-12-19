package com.library.repositories.projections;

public interface BookView {
    Long getId();
    String getTitle();
    String getIsbn();

    CategoryInfo getCategory();

    interface CategoryInfo {
        Long getId();
        String getName();
    }
}
