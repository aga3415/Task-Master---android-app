package al.taskmasterprojinz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import MySQLConnection.RefreshingGroups;
import MySQLConnection.SignUp;

/**
 * Created by Agnieszka on 2015-06-22.
 */
public class RefreshingActivity extends Activity {

    ProgressBar mProgressView;
    boolean connectionEstablish = false;
    TextView update;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshing);
        update = (TextView) findViewById(R.id.update);
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);

        showProgress(true);
        new Refresh().execute();


    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }

    private class Refresh extends AsyncTask implements al.taskmasterprojinz.Refresh {

        @Override
        protected Object doInBackground(Object[] params) {

             RefreshingGroups refreshingGroups = new RefreshingGroups(getApplicationContext());
             refreshingGroups.doInBackground();

            connectionEstablish = refreshingGroups.connectionSuccess;

            if (!connectionEstablish){
                update.setText(getResources().getString(R.string.error_connection_establish));
            }else{
                onPostExecute(connectionEstablish);
            }

            return null;

        }


        protected void onPostExecute(boolean success){
            if (connectionEstablish){
                Intent backToTaskList = new Intent(getApplicationContext(), TabHostActivity.class);
                startActivity(backToTaskList);
            }
        }
    }
}
