package com.brand.sniffy.android.service;


import java.sql.SQLException;
import java.util.List;
import android.accounts.Account;
import android.content.Context;
import com.brand.sniffy.android.model.Category;
import com.brand.sniffy.android.model.Database;


public class CategoryService {

	private Database database;
	
	
	public CategoryService(Context context, Account account){
		database = new Database(context, account);
	}

	public Category getCategory(int id) {
		try{
			List<Category> result  = database.getRuntimeExceptionDao(Category.class)
					.queryBuilder().where().eq(Category.ID_FIELD, id).query();
			if(result.size() > 0){
				return result.get(0);
			}
			else{
				return null;
			}
		}catch(SQLException e){
			return null;
		}
	}

	public Category save(Category category) {
		Category existingCategory = getCategory(category.getId());
		if(existingCategory == null){
			create(category);
		}
		else{
			update(category);
		}
		
		return category;
	}

	private void update(Category category) {
		database.getRuntimeExceptionDao(Category.class).update(category);
	}

	private Category create(Category category) {
		database.getRuntimeExceptionDao(Category.class).create(category);
		return category;
	}
}
