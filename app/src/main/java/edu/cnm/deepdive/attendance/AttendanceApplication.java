package edu.cnm.deepdive.attendance;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class AttendanceApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
  }
}
