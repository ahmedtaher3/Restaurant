package taher.restaurant.data.room.categories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface CategoriesDao {
    @Query("SELECT * FROM CategoriesEntity")
    List<CategoriesEntity> getAll();


    @Query("DELETE FROM CategoriesEntity")
    public void deleteTable();

    @Insert
    void insertAll(List<CategoriesEntity> entities);

    @Insert
    void insert(CategoriesEntity entity);

    @Delete
    void delete(CategoriesEntity entity);

    @Update
    void update(CategoriesEntity entity);


}
