package edu.cnm.deepdive.attendance.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Student.class}, version = 1, exportSchema = true)
public abstract class AbsenceDatabase extends RoomDatabase {

  private static final String DATABASE_NAME = "attendance_db";

  private static AbsenceDatabase instance = null;

  public static synchronized AbsenceDatabase getInstance(Context context){
    if(instance==null){
      instance = Room.databaseBuilder(context.getApplicationContext(),
          AbsenceDatabase.class,
          DATABASE_NAME)
          .addCallback(new Callback(context.getApplicationContext()))
          .build();
    }
    return instance;
  }

  public static synchronized void forgetInstance(Context context){
    instance = null;
  }

  public abstract StudentDao getStudentDao();



  private static class Callback extends RoomDatabase.Callback{

    private Context context;

    private Callback(Context context){
      this.context = context;
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      new PrepopulateTask(context).execute();
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }

    private static class PrepopulateTask extends AsyncTask<Void, Void, Void>{

      private Context context;

      public PrepopulateTask(Context context){
        this.context = context;
      }

      @Override
      protected Void doInBackground(Void... voids) {
        AbsenceDatabase db = getInstance(context);
        StudentDao dao = db.getStudentDao();
        Student student = new Student();
        student.setLastName("Mouse");
        student.setFirstName("Michael");
        student.setDisplayName("Mickey Mouse");
        dao.insert(student);
        forgetInstance(context);
        return null;
      }
    }

  }

}
