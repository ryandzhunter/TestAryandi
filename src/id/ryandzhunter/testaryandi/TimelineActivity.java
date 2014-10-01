package id.ryandzhunter.testaryandi;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;

public class TimelineActivity extends Activity {
	private String TAG = "TimelineActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		Session.NewPermissionsRequest np = new Session.NewPermissionsRequest(
				this, "read_stream");
		Session.getActiveSession().requestNewReadPermissions(np);
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this).setPermissions(
					Arrays.asList("read_stream")).setCallback(callback));
		} else {
			Session.openActiveSession(this, true, callback);
		}
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			/* make the API call */
			new Request(session, "/v2.1/me/feed?fields=message", null,
					HttpMethod.GET, new Request.Callback() {

						public void onCompleted(Response response) {
							/* handle the result */
							GraphObject graphObject = response.getGraphObject();
							if (graphObject != null) {
								JSONObject jsonObject = graphObject
										.getInnerJSONObject();
								Log.d(TAG, jsonObject.toString());
								try {
									JSONArray array = jsonObject
											.getJSONArray("data");
									Log.d(TAG, array.toString());
									for (int i = 0; i < array.length(); i++) {
										JSONObject object = (JSONObject) array
												.get(i);
										Log.i(TAG,
												"id = " + object.get("message"));
									}
								} catch (JSONException e) {

									e.printStackTrace();
									Log.e(TAG, "JSON error");
								}
							}
						}
					}).executeAsync();
		}
	};

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
}
