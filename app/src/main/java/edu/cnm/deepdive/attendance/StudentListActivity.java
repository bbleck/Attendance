package edu.cnm.deepdive.attendance;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.cnm.deepdive.attendance.database.AbsenceDatabase;
import edu.cnm.deepdive.attendance.database.Student;
import edu.cnm.deepdive.attendance.database.StudentDao;
import edu.cnm.deepdive.attendance.dummy.DummyContent;
import java.util.List;

/**
 * An activity representing a list of Students. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link StudentDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two vertical panes.
 */
public class StudentListActivity extends AppCompatActivity {

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
   */
  private boolean mTwoPane;
  private List<Student> students;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_student_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    if (findViewById(R.id.student_detail_container) != null) {
      // The detail container view will be present only in the
      // large-screen layouts (res/values-w900dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      mTwoPane = true;
    }

    View recyclerView = findViewById(R.id.student_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);
  }

  @Override
  protected void onStart() {
    super.onStart();
    //db access or service access need to be done off UI thread
    new StudentQuery().execute();//do a query here to show the latest data
  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
  }

  public static class SimpleItemRecyclerViewAdapter
      extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final StudentListActivity mParentActivity;
    private final List<DummyContent.DummyItem> mValues;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
        if (mTwoPane) {
          Bundle arguments = new Bundle();
          arguments.putString(StudentDetailFragment.ARG_ITEM_ID, item.id);
          StudentDetailFragment fragment = new StudentDetailFragment();
          fragment.setArguments(arguments);
          mParentActivity.getSupportFragmentManager().beginTransaction()
              .replace(R.id.student_detail_container, fragment)
              .commit();
        } else {
          Context context = view.getContext();
          Intent intent = new Intent(context, StudentDetailActivity.class);
          intent.putExtra(StudentDetailFragment.ARG_ITEM_ID, item.id);

          context.startActivity(intent);
        }
      }
    };

    SimpleItemRecyclerViewAdapter(StudentListActivity parent,
        List<DummyContent.DummyItem> items,
        boolean twoPane) {
      mValues = items;
      mParentActivity = parent;
      mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.student_list_content, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.mIdView.setText(mValues.get(position).id);
      holder.mContentView.setText(mValues.get(position).content);

      holder.itemView.setTag(mValues.get(position));
      holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

      final TextView mIdView;
      final TextView mContentView;

      ViewHolder(View view) {
        super(view);
        mIdView = (TextView) view.findViewById(R.id.id_text);
        mContentView = (TextView) view.findViewById(R.id.content);
      }
    }
  }

  private class StudentQuery extends AsyncTask<Void, Void, List<Student>>{

    public StudentQuery() {
      super();
    }

    @Override
    protected void onPostExecute(List<Student> students) {//this one happens in UI thread
      StudentListActivity.this.students = students;
    }

    @Override
    protected List<Student> doInBackground(Void... voids) {//this one happens in background thread
      AbsenceDatabase db = AbsenceDatabase.getInstance(StudentListActivity.this);
      StudentDao dao = db.getStudentDao();
      List<Student> students = dao.select();
      return dao.select();
    }
  }

}
