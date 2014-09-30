package id.ryandzhunter.testaryandi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			// callback when session changes state
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {

				if (session.isOpened()) {

					// make request to the /me API
					Request.newMeRequest(session,
							new Request.GraphUserCallback() {

								// callback after Graph API response with user
								// object
								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									if (user != null) {
										TextView welcome = (TextView) findViewById(R.id.welcome);
										welcome.setText("Hello "
												+ user.getName() + "!");
										TextView username = (TextView) findViewById(R.id.labelUsername);
										username.setText(user.getUsername());
										TextView firstname = (TextView) findViewById(R.id.labelFirstName);
										firstname.setText(user.getFirstName());
										TextView lastname = (TextView) findViewById(R.id.labelLastName);
										lastname.setText(user.getLastName());
										TextView birthday = (TextView) findViewById(R.id.labelBirthday);
										birthday.setText(user.getBirthday());
										TextView location = (TextView) findViewById(R.id.labelLocation);
										location.setText(user.getLocation().getProperty("name").toString());
										//location.setText(user.asMap().get("location").toString());
										TextView gender = (TextView) findViewById(R.id.labelGender);
										gender.setText(user.asMap().get("gender").toString());
																			
									}
								}
							}).executeAsync();
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}
	
	public void shareDialogClick(View view){
		Intent shareDialogIntent = new Intent(getApplicationContext(), ShareDialogActivity.class);
		startActivity(shareDialogIntent);	
	}
	
	public void shareViewClick(View view){
		Intent shareViewIntent = new Intent(getApplicationContext(), ShareViewActivity.class);
		startActivity(shareViewIntent);	
	}
	
	public void timelineClick(View view){
		Intent shareViewIntent = new Intent(getApplicationContext(), TimelineActivity.class);
		startActivity(shareViewIntent);	
	}
}
