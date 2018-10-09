package edu.cnm.deepdive.attendance.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import java.util.List;

@Dao
public interface StudentDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Student student);

  @Insert(onConflict = OnConflictStrategy.FAIL)
  List<Long> insert(Student...students);

  @Insert(onConflict = OnConflictStrategy.FAIL)
  List<Long> insert(List<Student> students);

  //TODO: add update/delete/select

}
