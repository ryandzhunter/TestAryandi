package id.ryandzhunter.testaryandi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;

public class TimelineActivity extends Activity {
	private String TAG = "TimelineActivity";
	String[] arrayStatus;
	ListView lvStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		lvStatus = (ListView) findViewById(R.id.lvStatus);

		Session session = Session.getActiveSession();
		Session.NewPermissionsRequest newPermissionsRequest = new Session
			      .NewPermissionsRequest(this, Arrays.asList("public_profile", "user_status",
							"read_stream"));
			    List<String> permision = new ArrayList<String>();
			    permision.add("user_status");
			    permision.add("read_stream");
				Session.openActiveSession(this, true,permision, callback);
		/*if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this).setPermissions(
					Arrays.asList("public_profile", "user_status",
							"read_stream")).setCallback(callback));
		} else {
			Session.openActiveSession(this, true, callback);
		}*/
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {

			if (session.isOpened()) {
				Bundle params = new Bundle();
				params.putString("access_token", session.getAccessToken());
				/* make the API call */
				new Request(session, "/v2.1/me/feed?fields=message", params,
						HttpMethod.GET, new Request.Callback() {

							public void onCompleted(Response response) {
								/* handle the result */
								Log.e(TAG, response.getError().toString());
								GraphObject graphObject = response
										.getGraphObject();
								if (graphObject != null) {
									Log.d(TAG, "sukses mengambil data");
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
													"id = "
															+ object.get("message"));
										}
									} catch (JSONException e) {

										e.printStackTrace();
										Log.e(TAG, "JSON error");
									}
								} else {
									Log.e(TAG, "gagal mengambil data");
								}
							}
						}).executeAsync();
			}
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
