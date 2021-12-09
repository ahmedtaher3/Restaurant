package taher.restaurant.data.room.extras;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;



@Dao
public interface ExtrasDao {
    @Query("SELECT * FROM ExtrasEntity")
    List<ExtrasEntity> getAll();


    @Query("DELETE FROM ExtrasEntity")
    public void deleteTable();

    @Insert
    void insertAll(List<ExtrasEntity> entities);

    @Insert
    void insert(ExtrasEntity entity);

    @Delete
    void delete(ExtrasEntity entity);

    @Update
    void update(ExtrasEntity entity);


}
