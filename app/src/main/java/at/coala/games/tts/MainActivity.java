package at.coala.games.tts;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import at.coala.games.tts.data.Background;
import at.coala.games.tts.data.UpdateUserData;
import at.coala.games.tts.data.User;
import at.coala.games.tts.dba.DataAccess;
import at.coala.games.tts.game.GameDataManager;

/**
 * This activity extends the Activity class. For more information about
 * activities read the description of Activity. It also implements the
 * OnClickListener interface to receive notification if a button was clicked.
 * This activity is the main activity and called first if the application is
 * started. It provides a user list to create and remove users and add them to
 * the game.
 *
 * TODO
 * slide to left --> next activity/question
 * slide to right --> last activity/abort question
 *
 * @author Klaus
 * @see Activity
 * @see OnClickListener
 */
public class MainActivity extends Activity implements OnClickListener {

	private boolean doubleBackToExitPressedOnce = false;
	private Handler handler = new Handler();
	private final Runnable resetDoubleBack = new Runnable() {
		@Override
		public void run() {
			doubleBackToExitPressedOnce = false;
		}
	};

	private View.OnTouchListener onTouchListener;

	/**
	 * A RadioButton for the male sex.
	 *
	 * @see RadioButton
	 */
	private RadioButton button_m;

	/**
	 * A RadioButton for the female sex.
	 *
	 * @see RadioButton
	 */
	private RadioButton button_w;

	/**
	 * A listView to show a user list.
	 *
	 * @see ListView
	 */
	private ListView user_listView;

	/**
	 * A button to create a new user.
	 *
	 * @see Button
	 */
	private Button new_user_button;

	/**
	 * An EditText field to type a username.
	 *
	 * @see EditText
	 */
	private EditText new_user_name;

	/**
	 * A button to go to the game.
	 *
	 * @see Button
	 */
	private Button play_button;

	/**
	 * A private list containing all existing users.
	 *
	 * @see List
	 * @see User
	 */
	private List<User> userList;

	/**
	 * Create a new user if the username is longer than zero and does not exist
	 * yet, and adds it to the game.
	 */
	private void newUser() {
		if (this.new_user_name.getText().toString().length() > 0
				&& (this.button_m.isChecked()
				|| this.button_w.isChecked())) {
			GameDataManager.addPlayer(UpdateUserData.newUser(
					this,
					this.userList, this.new_user_name.getText().toString(),
					(this.button_m.isChecked() ? User.SEX_MALE : User.SEX_FEMALE)));
			((UserAdapter)this.user_listView.getAdapter()).notifyDataSetChanged();
			this.new_user_name.setText(getText(R.string.empty));
		}

		//noinspection ConstantConditions
		((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
		this.new_user_name.clearFocus();
	}

	/**
	 * Called when a view has been clicked. For more information read the
	 * method description in OnClickListener. If the play_button was clicked it
	 * starts the SettingsActivity.
	 *
	 * @param v The view that was clicked.
	 * @see OnClickListener
	 * @see View
	 */
	@Override
	public void onClick(View v) {
		if (v == this.play_button) {
			if (GameDataManager.enoughPlayer()) {
				findViewById(R.id.choose_some_player).setVisibility(View.GONE);
				startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			} else {
				findViewById(R.id.choose_some_player).setVisibility(View.VISIBLE);
			}
		} else if (v == this.new_user_button) {
			newUser();
		}
	}

	/**
	 * Called when the activity is starting. For more information read the
	 * method description in Activity. This method initializes some Buttons to
	 * the OnClickListener, creates a OnEditorActionListener for the EditText
	 * element, and extracts data from data files and databases.
	 *
	 * @param savedInstanceState null
	 * @see Activity
	 * @see OnClickListener
	 * @see OnEditorActionListener
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		this.new_user_button = (Button)findViewById(R.id.new_user_button);
		this.new_user_button.setOnClickListener(this);
		
		this.play_button = (Button)findViewById(R.id.main_play_button);
		this.play_button.setOnClickListener(this);
		this.play_button.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_blink));

		this.button_m = (RadioButton)findViewById(R.id.radio_m);
		this.button_w = (RadioButton)findViewById(R.id.radio_w);
		
		this.new_user_name = (EditText)findViewById(R.id.new_user_name);
		this.new_user_name.setOnEditorActionListener(new OnEditorActionListener() {

			/**
			 * Called when an action is being performed. For more information
			 * read the method description in OnEditorActionListener.
			 *
			 * @param view The view that was clicked.
			 * @param keyCode Identifier of the action. This will be either the
			 *                         identifier you supplied, or
			 *                         EditorInfo.IME_NULL if being called due
			 *                         to the enter key being pressed.
			 * @param event If triggered by an enter key, this is the event;
			 *                       otherwise, this is null.
			 * @return Return true if you have consumed the action, else false.
			 * @see EditorInfo
			 * @see KeyEvent
			 * @see OnEditorActionListener
			 * @see TextView
			 */
			@Override
			public boolean onEditorAction(TextView view, int keyCode, KeyEvent event) {
				if (event != null && event.getAction() != KeyEvent.ACTION_DOWN) return false;
				if (keyCode == EditorInfo.IME_ACTION_DONE) {
					newUser();
					return true;
				}
				return false;
			}
		});

		ArrayList<User> userListActive = new ArrayList<>();
		userList = new ArrayList<>();
		DataAccess.getUsers(this, userListActive, userList);

		GameDataManager.onCreate(this);
		for (User u: userListActive) {
			GameDataManager.addPlayer(u);
			userList.add(0, u);
		}

		UserAdapter userAdapter = new UserAdapter(this, this.userList);
		this.user_listView = (ListView)findViewById(R.id.list_view);
		this.user_listView.setAdapter(userAdapter);
		/**this.user_listView.setOnTouchListener(new OnSwipeTouchListener(this) {
			@Override
			public void onSwipeBottom(MotionEvent e1, MotionEvent e2) {

			}

			@Override
			public void onSwipeLeft(MotionEvent e1, MotionEvent e2) {
				int position = user_listView.pointToPosition((int)e1.getX(), (int)e1.getY());
				if (GameDataManager.containsUser(userList.get(position))) {
					UpdateUserData.removePlayer(MainActivity.this, userList.get(position));
				} else {
					UpdateUserData.removeUser(MainActivity.this, userList, position);
				}
				((UserAdapter)user_listView.getAdapter()).notifyDataSetChanged();
			}

			@Override
			public void onSwipeRight(MotionEvent e1, MotionEvent e2) {
				UpdateUserData.addPlayer(MainActivity.this, userList.get(
						user_listView.pointToPosition((int)e1.getX(), (int)e1.getY())));
				((UserAdapter)user_listView.getAdapter()).notifyDataSetChanged();
			}

			@Override
			public void onSwipeTop(MotionEvent e1, MotionEvent e2) {

			}
		});


		onTouchListener = new OnSwipeTouchListener(this) {

			@Override
			public void onSwipeBottom(MotionEvent e1, MotionEvent e2) {

			}

			@Override
			public void onSwipeLeft(MotionEvent e1, MotionEvent e2) {
				play_button.performClick();
			}

			@Override
			public void onSwipeRight(MotionEvent e1, MotionEvent e2) {

			}

			@Override
			public void onSwipeTop(MotionEvent e1, MotionEvent e2) {

			}
		};
		for (View v : findViewById(R.id.main_region).getTouchables()) {
			//v.setOnTouchListener(onTouchListener);
		}**/

		findViewById(R.id.head_layout).setBackgroundResource(Background.getBackground(getResources().getConfiguration().orientation));
	}

	/**
	 * Called after onStop() when the current activity is being re-displayed to
	 * the user (the user has navigated back to it). For more information read
	 * the method description in Activity.
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		((UserAdapter)this.user_listView.getAdapter()).notifyDataSetChanged();
	}

	/**
	 * Called if the back-button is pressed. This class delays the standard
	 * back-action till the back-button gets pressed a second time within two
	 * seconds.
	 */
	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
		} else {
			this.doubleBackToExitPressedOnce = true;
			Toast.makeText(this, String.format(getString(R.string.press_double_to_exit), getString(R.string.app_name)), Toast.LENGTH_SHORT).show();
			handler.postDelayed(resetDoubleBack, 2000);
		}
	}

	/**
	 * TODO
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (handler != null) { handler.removeCallbacks(resetDoubleBack); }
	}
}
