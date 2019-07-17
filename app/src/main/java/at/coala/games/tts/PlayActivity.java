package at.coala.games.tts;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import at.coala.games.tts.data.Background;
import at.coala.games.tts.game.GameDataManager;

/**
 * This activity extends the Activity class. For more information about
 * activities read the description of Activity. It also implements the
 * OnClickListener interface to receive notification if a button was clicked.
 * This activity leads through the game and shows the quests.
 *
 * TODO
 * Button to go back to settings.
 *
 * @author Klaus
 * @see Activity
 * @see OnClickListener
 */
public class PlayActivity extends Activity implements OnClickListener {

	/**
	 * Button to complete/approve and go on.
	 */
	Button go_button;

	/**
	 * TODO
	 */
	Runnable go_button_activation;

	/**
	 * Button to abort or skip.
	 */
	Button no_button;

	/**
	 * TODO
	 */
	Runnable no_button_activation;

	/**
	 * Called when a view has been clicked. For more information read the
	 * method description in OnClickListener. This method listens to the
	 * go_button and no_button and calls setText().
	 *
	 * @param v The view that was clicked.
	 * @see OnClickListener
	 * @see View
	 */
	@Override
	public void onClick(View v) {
		GameDataManager.clickedButtonIsGo(v == this.go_button);

		// so that the button is not fading through this process
		no_button.clearAnimation();
		no_button.setVisibility(View.GONE);
		no_button.removeCallbacks(no_button_activation);

		no_button.removeCallbacks(go_button_activation);

		GameDataManager.saveGameData(this);

		setText();
	}

	/**
	 * Called when the activity is starting. For more information read the
	 * method description in Activity. This method initializes the Buttons to
	 * the OnClickListener and calls setText().
	 *
	 * @param savedInstanceState null
	 * @see Activity
	 * @see OnClickListener
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		this.no_button = (Button)findViewById(R.id.no_button);
		this.no_button.setOnClickListener(this);
		
		this.go_button = (Button)findViewById(R.id.go_button);
		this.go_button.setOnClickListener(this);

		findViewById(R.id.title).startAnimation(
				AnimationUtils.loadAnimation(this, R.anim.fade_blink));

		go_button_activation = new Runnable() {
			@Override
			public void run() {
				go_button.setEnabled(true);
			}
		};

		no_button_activation = new Runnable() {
			@Override
			public void run() {
				no_button.setEnabled(true);
			}
		};

		setText();

		GameDataManager.saveGameData(this);
	}

	/**
	 * This method sets the text to all text views and hides the no_button if
	 * there is no text to show.
	 */
	private void setText() {
		((TextView)findViewById(R.id.pre_title)).setText(GameDataManager.getPreTitle(this));

		((TextView)findViewById(R.id.title)).setText(GameDataManager.getTitle(this));

		((TextView)findViewById(R.id.main_text)).setText(GameDataManager.getMainText(this));
		findViewById(R.id.main_text).startAnimation(
				AnimationUtils.loadAnimation(this, R.anim.fade_in_0_5));

		no_button.setEnabled(false);
		String no = GameDataManager.getNoButtonText(this);
		if (no == null) no_button.setVisibility(View.GONE);
		else {
			no_button.setVisibility(View.VISIBLE);
			no_button.setText(no);
			no_button.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_2));
			no_button.postDelayed(no_button_activation, 2250);
		}
		go_button.setEnabled(false);
		go_button.setText(GameDataManager.getGoButtonText(this));
		go_button.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_1));
		go_button.postDelayed(go_button_activation, 1000);

		((TextView) findViewById(R.id.sub_text)).setText(GameDataManager.getSubText(this));
		findViewById(R.id.head_layout).setBackgroundResource(
				Background.getBackground(getResources().getConfiguration().orientation));

		findViewById(R.id.sub_text).startAnimation(
				AnimationUtils.loadAnimation(this, R.anim.fade_in_1));
	}
}
