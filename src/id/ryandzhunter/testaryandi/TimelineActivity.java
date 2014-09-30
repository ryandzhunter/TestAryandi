package id.ryandzhunter.testaryandi;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;

public class TimelineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		Session.openActiveSession(this, true, callback);

	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
			/* make the API call */
			new Request(
			    session,
			    "/me/feed",
			    null,
			    HttpMethod.GET,
			    new Request.Callback() {
			        public void onCompleted(Response response) {
			            /* handle the result */
			        }
			    }
			).executeAsync();
	    }
	};
}
