package com.library.userModel;

import com.library.models.Category;
import com.library.projections.BookView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBook {
    Long id;
    String title;
    String isbn;

    String category;


}
