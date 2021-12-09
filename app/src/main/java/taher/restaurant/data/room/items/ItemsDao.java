package taher.restaurant.data.room.items;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ItemsDao {
    @Query("SELECT * FROM ItemsEntity")
     List<ItemsEntity>  getAll();


    @Query("DELETE FROM ItemsEntity")
    public void deleteTable();

   /* @Query("SELECT * FROM ProductEntity WHERE id IN (:employeesIds)")
    List<Product> loadAllByIds(int[] employeesIds);*/

    @Insert
    void insertAll(List<ItemsEntity> entities);

    @Insert
    void insert(ItemsEntity entity);

    @Delete
    void delete(ItemsEntity entity);

    @Update
    void update(ItemsEntity entity);



}
