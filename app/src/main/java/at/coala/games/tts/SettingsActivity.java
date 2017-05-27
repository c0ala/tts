package at.coala.games.tts;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.coala.games.tts.data.Background;
import at.coala.games.tts.data.Game;
import at.coala.games.tts.data.Settings;
import at.coala.games.tts.data.quest.Quest;
import at.coala.games.tts.dba.DataAccess;
import at.coala.games.tts.game.GameDataManager;

/**
 * This activity extends the Activity class. For more information about
 * activities read the description of Activity. It also implements the
 * OnCheckedChangeListener interface to receive notification if a button was
 * checked or unchecked. This activity provides changing some game settings.
 *
 * @author Klaus
 * @see Activity
 * @see OnCheckedChangeListener
 */
public class SettingsActivity extends Activity {

	/**
	 * A CheckBox checked if new players start wth a higher level.
	 *
	 * @see CheckBox
	 */
	CheckBox already_drunk;
	/**
	 * Items and Views that can be made invisible of custom options are not
	 * available.
	 *
	 * @see ArrayList
	 * @see List
	 * @see View
	 */
	List<View> collapsible_options = new ArrayList<>();

	/**
	 * A CheckBox to set custom options.
	 *
	 * @see CheckBox
	 */
	CheckBox custom_options;

	/**
	 * A RadioButton representing a very deep friendship between players.
	 *
	 * @see RadioButton
	 */
	RadioButton friends_benefits;

	/**
	 * A RadioButton representing a loose friendship between players.
	 *
	 * @see RadioButton
	 */
	RadioButton friends_good;

	/**
	 * A RadioButton representing a good friendship between players.
	 *
	 * @see RadioButton
	 */
	RadioButton friends_loose;

	/**
	 * A button to go to the game.
	 *
	 * @see Button
	 */
	Button play_button;

	/**
	 * A RadioButton for a game in a private location.
	 *
	 * @see RadioButton
	 */
	RadioButton radio_private_game;

	/**
	 * A RadioButton for a game in a public location.
	 *
	 * @see RadioButton
	 */
	RadioButton radio_public_game;

	CheckBox requirement_ice_cube;

	CheckBox requirement_cream;

	CheckBox requirement_pool;

	CheckBox requirement_dance;

	OnCheckedChangeListener locationChangeListener;
	OnCheckedChangeListener friendshipLevelChangeListener;
	OnCheckedChangeListener requirementsChangeListener;

	/**
	 * Called when the activity is starting. For more information read the
	 * method description in Activity. This method initializes some Buttons to
	 * different listeners.
	 *
	 * Make static Settings class in data package.
	 *
	 * @param savedInstanceState null
	 * @see Activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		/**
		 * Location Radio Group
		 */
		this.radio_private_game = (RadioButton)findViewById(R.id.radio_private_game);
		this.radio_public_game = (RadioButton)findViewById(R.id.radio_public_game);
		int location = DataAccess.getSetting(this, Settings.ATTRIBUTE_GAME_LOCATION, Settings.DEFAULT_LOCATION);
		if (location == Game.LOCATION_PRIVATE) this.radio_private_game.setChecked(true);
		else this.radio_public_game.setChecked(true);
		GameDataManager.setGameLocation(location);
		locationChangeListener = new OnCheckedChangeListener() {

			/**
			 * Called when the checked state of a compound button has changed.
			 * For more information read the method description in
			 * OnCheckedChangeListener. This method listens to the location
			 * radio group and sets the new location.
			 *
			 * @param buttonView The compound button view whose state has changed.
			 * @param isChecked The new checked state of buttonView.
			 * @see CompoundButton
			 * @see OnCheckedChangeListener
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (buttonView == radio_public_game && isChecked) {
					onLocationChange(Game.LOCATION_PUBLIC);
				} else if (buttonView == radio_private_game && isChecked) {
					onLocationChange(Game.LOCATION_PRIVATE);
				}
			}
		};
		this.radio_private_game.setOnCheckedChangeListener(locationChangeListener);
		this.radio_public_game.setOnCheckedChangeListener(locationChangeListener);
		this.collapsible_options.add(this.radio_public_game);

		/**
		 * Checkbox already_drunk
		 */
		this.already_drunk = (CheckBox)findViewById(R.id.already_drunk);
		if (DataAccess.getSetting(
				this, Settings.ATTRIBUTE_ALREADY_DRUNK, false)) {
			this.requirement_ice_cube.setChecked(true);
		}
		this.collapsible_options.add(this.already_drunk);

		/**
		 * Friendship Level Radio Group
		 */
		this.friends_loose = (RadioButton)findViewById(R.id.friends_loose);
		this.friends_good = (RadioButton)findViewById(R.id.friends_good);
		this.friends_benefits = (RadioButton)findViewById(R.id.friends_benefits);
		int friendship_level = DataAccess.getSetting(this, Settings.ATTRIBUTE_FRIENDSHIP_LEVEL, Settings.DEFAULT_FRIENDS);
		if (friendship_level == Game.FRIENDS_LOOSE) {
			this.friends_loose.setChecked(true);
			this.friends_benefits.setVisibility(View.GONE);
		}
		else if (friendship_level == Game.FRIENDS_GOOD) this.friends_good.setChecked(true);
		else this.friends_benefits.setChecked(true);
		GameDataManager.setFriendshipLevel(friendship_level);
		friendshipLevelChangeListener = new OnCheckedChangeListener() {

			/**
			 * Called when the checked state of a compound button has changed.
			 * For more information read the method description in
			 * OnCheckedChangeListener. This method listens to the friendship
			 * level radio group and sets the new friendship level.
			 *
			 * @param buttonView The compound button view whose state has changed.
			 * @param isChecked The new checked state of buttonView.
			 * @see CompoundButton
			 * @see OnCheckedChangeListener
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (buttonView == friends_good && isChecked) {
					findViewById(R.id.friends_benefits).setVisibility(View.VISIBLE);
					onFriendshipLevelChange(Game.FRIENDS_GOOD);
				} else if (buttonView == friends_loose && isChecked) {
					findViewById(R.id.friends_benefits).setVisibility(View.GONE);
					onFriendshipLevelChange(Game.FRIENDS_LOOSE);
				} else if (buttonView == friends_benefits && isChecked) {
					onFriendshipLevelChange(Game.FRIENDS_BENEFITS);
				}
			}
		};
		this.friends_loose.setOnCheckedChangeListener(friendshipLevelChangeListener);
		this.friends_good.setOnCheckedChangeListener(friendshipLevelChangeListener);
		this.friends_benefits.setOnCheckedChangeListener(friendshipLevelChangeListener);
		this.collapsible_options.add(this.friends_good);

		/**
		 * Requirements Checkboxes
		 */
		List<Integer> defaultRequirements = Settings.getRequirementsDefault();
		this.requirement_ice_cube = (CheckBox)findViewById(R.id.requirement_ice_cube);
		this.requirement_cream = (CheckBox)findViewById(R.id.requirement_cream);
		this.requirement_pool = (CheckBox)findViewById(R.id.requirement_pool);
		this.requirement_dance = (CheckBox)findViewById(R.id.requirement_dance);
		if (DataAccess.getSetting(
				this, Settings.ATTRIBUTE_REQUIREMENT_ICE_CUBE, defaultRequirements.contains(Quest.REQUIREMENT_ICE_CUBE))) {
			this.requirement_ice_cube.setChecked(true);
			GameDataManager.addRequirement(Quest.REQUIREMENT_ICE_CUBE);
		}
		if (DataAccess.getSetting(
				this, Settings.ATTRIBUTE_REQUIREMENT_CREAM, defaultRequirements.contains(Quest.REQUIREMENT_CREAM))) {
			this.requirement_cream.setChecked(true);
			GameDataManager.addRequirement(Quest.REQUIREMENT_CREAM);
		}
		if (DataAccess.getSetting(
				this, Settings.ATTRIBUTE_REQUIREMENT_POOL, defaultRequirements.contains(Quest.REQUIREMENT_POOL))) {
			this.requirement_pool.setChecked(true);
			GameDataManager.addRequirement(Quest.REQUIREMENT_POOL);
		}
		if (DataAccess.getSetting(
				this, Settings.ATTRIBUTE_REQUIREMENT_DANCE, defaultRequirements.contains(Quest.REQUIREMENT_DANCE))) {
			this.requirement_dance.setChecked(true);
			GameDataManager.addRequirement(Quest.REQUIREMENT_DANCE);
		}
		requirementsChangeListener = new OnCheckedChangeListener() {

			/**
			 * Called when the checked state of a compound button has changed.
			 * For more information read the method description in
			 * OnCheckedChangeListener. This method listens to requirements
			 * checkboxes and sets the new requirements.
			 *
			 * @param buttonView The compound button view whose state has changed.
			 * @param isChecked The new checked state of buttonView.
			 * @see CompoundButton
			 * @see OnCheckedChangeListener
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (buttonView == requirement_ice_cube) {
					onRequirementChange(Quest.REQUIREMENT_ICE_CUBE, isChecked);
				} else if (buttonView == requirement_cream) {
					onRequirementChange(Quest.REQUIREMENT_CREAM, isChecked);
				} else if (buttonView == requirement_pool) {
					onRequirementChange(Quest.REQUIREMENT_POOL, isChecked);
				} else if (buttonView == requirement_dance) {
					onRequirementChange(Quest.REQUIREMENT_DANCE, isChecked);
				}
			}
		};
		this.requirement_ice_cube.setOnCheckedChangeListener(requirementsChangeListener);
		this.requirement_cream.setOnCheckedChangeListener(requirementsChangeListener);
		this.requirement_pool.setOnCheckedChangeListener(requirementsChangeListener);
		this.requirement_dance.setOnCheckedChangeListener(requirementsChangeListener);
		this.collapsible_options.add(this.requirement_ice_cube);
		this.collapsible_options.add(this.requirement_cream);
		this.collapsible_options.add(this.requirement_pool);
		this.collapsible_options.add(this.requirement_dance);

		/**
		 * Custom Settings Checkbox
		 */
		this.custom_options = (CheckBox)findViewById(R.id.custom_options);
		this.custom_options.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			/**
			 * Called when the checked state of a compound button has changed.
			 * For more information read the method description in
			 * OnCheckedChangeListener. This method listens to the check box
			 * en- and disabling the custom settings area.
			 *
			 * @param buttonView The compound button view whose state has changed.
			 * @param isChecked The new checked state of buttonView.
			 * @see CompoundButton
			 * @see OnCheckedChangeListener
			 */
			@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					for (View v : collapsible_options) v.setVisibility(View.VISIBLE);
				} else {
					radio_private_game.setChecked(true);
					friends_loose.setChecked(true);
					already_drunk.setChecked(false);
					requirement_ice_cube.setChecked(false);
					requirement_cream.setChecked(false);
					requirement_pool.setChecked(false);
					requirement_dance.setChecked(false);

					for (View v : collapsible_options) v.setVisibility(View.GONE);
				}
				DataAccess.updateSetting(SettingsActivity.this, Settings.ATTRIBUTE_CUSTOM_SETTINGS, isChecked);
			}
		});
		this.custom_options.setChecked(!DataAccess.getSetting(this, Settings.ATTRIBUTE_CUSTOM_SETTINGS, false));
		this.custom_options.performClick();

		/**
		 * Play Button
		 */
		this.play_button = (Button)findViewById(R.id.play_button);
		this.play_button.setOnClickListener(new OnClickListener() {

			/**
			 * Called when a view has been clicked. For more information read
			 * the method description in OnClickListener. If the play_button
			 * was clicked it starts the SettingsActivity.
			 *
			 * @param v The view that was clicked.
			 * @see OnClickListener
			 * @see View
			 */
			@Override
			public void onClick(View v) {
				if (already_drunk.isChecked()) GameDataManager.startDrunk();
				startActivity(new Intent(SettingsActivity.this, PlayActivity.class));
			}
		});
		this.play_button.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_blink));

		/**
		 * other initialisation
		 */
		setQuestCount();
		findViewById(R.id.head_layout).setBackgroundResource(Background.getBackground(getResources().getConfiguration().orientation));

		if (DataAccess.getGameData(this, GameDataManager.ATTRIBUTE_GAME_DATA_LAST_GAME_ACTION_TIMESTAMP, 0L)
				> System.currentTimeMillis() - Settings.MAX_MILLIS_TO_RESUME_GAME)
			GameDataManager.toResume();

		System.out.println(DataAccess.getGameData(this, GameDataManager.ATTRIBUTE_GAME_DATA_LAST_GAME_ACTION_TIMESTAMP, 0L));
		System.out.println(System.currentTimeMillis() - Settings.MAX_MILLIS_TO_RESUME_GAME);
	}

	/**
	 * Called after onStop() when the current activity is being re-displayed to
	 * the user (the user has navigated back to it). For more information read
	 * the method description in Activity.
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		GameDataManager.toResume();
	}

	private void setQuestCount() {
		((TextView)findViewById(R.id.quest_count)).setText(String.format(
				this.getString(R.string.space_questcount),
				GameDataManager.getValidQuestTextCount()));
	}

	/**
	 * Updates the friendship level in  game and database.
	 *
	 * @param friendship_level the new friendship level. Takes a public final
	 *                            Game.FRIENDS_ flag.
	 */
	private void onFriendshipLevelChange(int friendship_level) {
		Background.setFriendshipLevel(friendship_level);
		GameDataManager.setFriendshipLevel(friendship_level);
		DataAccess.updateSetting(this, Settings.ATTRIBUTE_FRIENDSHIP_LEVEL, friendship_level);
		setQuestCount();
	}

	/**
	 * Updates the friendship level in  game and database.
	 *
	 * @param location the new location. Takes a public final Game.LOCATION_
	 *                    flag.
	 */
	private void onLocationChange(int location) {
		GameDataManager.setGameLocation(location);
		DataAccess.updateSetting(this, Settings.ATTRIBUTE_GAME_LOCATION, location);
		setQuestCount();
	}

	/**
	 * Updates the friendship level in  game and database.
	 *
	 * @param requirement the requirement. Takes a public final
	 *                       Quest.REQUIREMENT_ flag.
	 * @param isChecked true if requirement is added; false if removed
	 */
	private void onRequirementChange(int requirement, boolean isChecked) {
		if (isChecked) GameDataManager.addRequirement(requirement);
		else GameDataManager.removeRequirement(requirement);
		DataAccess.updateSetting(this, Settings.getSettingsAttributeRequirementInt(requirement), isChecked);
		setQuestCount();
	}
}
