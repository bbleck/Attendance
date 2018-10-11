package edu.cnm.deepdive.attendance.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

@Entity(
    tableName = "absences",
    foreignKeys = {
      @ForeignKey(
          entity = Student.class,
          parentColumns = "student_id",
          childColumns = "student_id",
          onDelete = ForeignKey.CASCADE
      )
    },
    indices = {
        @Index(value = {"start", "end"}),
        @Index(value = {"student_id", "start"}, unique = true),
        @Index(value = {"student_id", "start", "end"})
    }
)
public class Absence {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "absence_id")
  private long absenceId;

  @NonNull
  @ColumnInfo(name = "student_id", index = true)//indexing makes it so each entry must be unique
  private long studentId;

  @NonNull
  private Date start;

  private Date end;

  @ColumnInfo(name = "is_excused")
  private boolean isExcused;

  public long getAbsenceId() {
    return absenceId;
  }

  public void setAbsenceId(long absenceId) {
    this.absenceId = absenceId;
  }

  public long getStudentId() {
    return studentId;
  }

  public void setStudentId(long studentId) {
    this.studentId = studentId;
  }

  @NonNull
  public Date getStart() {
    return start;
  }

  public void setStart(@NonNull Date start) {
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public boolean isExcused() {
    return isExcused;
  }

  public void setExcused(boolean excused) {
    isExcused = excused;
  }
}
