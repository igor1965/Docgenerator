package com.igor.docgenerator;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE)
    void insert(Employees employees);
    @Delete
    void delete(Employees employees);
    @Delete
    void deleteAll(List<Employees> employees);
    @Query("UPDATE employees SET name = :sName WHERE ID = :sID")
    void update(int sID,String sName);
    @Query("SELECT * FROM employees")
    List<Employees> getAll();
}
