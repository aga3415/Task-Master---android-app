package al.taskmasterprojinz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Agnieszka on 2015-05-20.
 */
public class HelpView extends Menu {

    Button send;
    EditText emailText;
    Resources res;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);
        send = (Button) findViewById(R.id.send);
        emailText = (EditText) findViewById(R.id.email);
        res = getResources();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailText.getText().length()>0){
                    sendEmail();
                    emailText.setText("");
                }
                finishActivity(0);

            }
        });



    }

    protected void sendEmail() {
        String[] recipients = {"agalim@o2.pl"};
        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));

        // prompts email clients only

        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, recipients);
        email.putExtra(Intent.EXTRA_SUBJECT, res.getString(R.string.app_update));
        email.putExtra(Intent.EXTRA_TEXT, emailText.getText().toString());

        try {

            // the user can choose the email client

            startActivity(Intent.createChooser(email, "Choose an email client from..."));

        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(HelpView.this, "No email client installed.",
                    Toast.LENGTH_LONG).show();

        }

    }

}
