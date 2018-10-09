package edu.cnm.deepdive.attendance.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "students",
  indices = {
    @Index(value = {"last_name", "first_name"}, unique = true ),
      @Index(value = {"display_name"}, unique = true)
  })
public class Student {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "student_id")
  private long id;

  @NonNull
  @ColumnInfo(name = "last_name", collate = ColumnInfo.NOCASE)
  private String lastName;

  @NonNull
  @ColumnInfo(name = "first_name", collate = ColumnInfo.NOCASE)
  private String firstName;

  @NonNull
  @ColumnInfo(name = "display_name", collate = ColumnInfo.NOCASE)
  private String displayName;

  @ColumnInfo(name = "phone_number", index = true)
  private String phoneNumber;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public String getLastName() {
    return lastName;
  }

  public void setLastName(@NonNull String lastName) {
    this.lastName = lastName;
  }

  @NonNull
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(@NonNull String firstName) {
    this.firstName = firstName;
  }

  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(@Nullable String displayName) {
    this.displayName = displayName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }


  //Layers of databases:  bottom -> physical db -> relational database management system RDBMS such as MySQL or SQLite -> Data Access such as JDBC ->  ORM such as Room and Hibernate  ->

  //ORMs have: Entities,  DAO,  Database
}
