package at.coala.games.tts.data;

import java.util.Random;

import android.content.res.Configuration;
import at.coala.games.tts.R;

/**
 * TODO
 *
 * @author Klaus
 */
public class Background {

	/**
	 * A private static field containing a Game.FRIENDS_ flag.
	 *
	 * @see Game
	 */
	private static int friendship_level = Settings.DEFAULT_FRIENDS;

	/**
	 * A private static random number generator.
	 *
	 * @see Random
	 */
	private static Random r = new Random();

	/**
	 * Returns a random resource id for a background image considering the
	 * orientation and set friendship level.
	 *
	 * @param orientation takes a final Configuration.ORIENTATION_ flag.
	 * @return a resource id, or 0 if no suitable resource was found.
	 * @see Configuration
	 * @see R
	 */
	public static int getBackground(int orientation) {
		switch (friendship_level) {
			case Game.FRIENDS_BENEFITS: return getBenefits(orientation);
			case Game.FRIENDS_GOOD: return getGood(orientation);
			case Game.FRIENDS_LOOSE: return getLoose(orientation);
			default: return 0;
		}
	}


	/**
	 * Returns a random resource id for a background image considering the
	 * orientation and all background resources suitable for friendship level
	 * Game.FRIENDS_BENEFITS
	 *
	 * @param orientation takes a final Configuration.ORIENTATION_ flag.
	 * @return a resource id
	 * @see Configuration
	 * @see Game
	 * @see R
	 */
	private static int getBenefits(int orientation) {
		if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
			switch(r.nextInt(13)) {
				case 0: return R.drawable.velli_l;
				case 1: return R.drawable.velli2_l;
				case 2: return R.drawable.velli3_l;
				case 3: return R.drawable.velli4_l;
				case 4: return R.drawable.velli5_l;
				case 5: return R.drawable.velli6_l;
				case 6: return R.drawable.velli7_l;
				case 7: return R.drawable.velli8_l;
				case 8: return R.drawable.velli9_l;
				case 9: return R.drawable.velli10_l;
				case 10: return R.drawable.velli11_l;
				case 11: return R.drawable.velli12_l;
				case 12: return R.drawable.vk_l;
				default:
			}
		} else {
			switch(r.nextInt(13)) {
				case 0: return R.drawable.velli_p;
				case 1: return R.drawable.velli2_p;
				case 2: return R.drawable.velli3_p;
				case 3: return R.drawable.velli4_p;
				case 4: return R.drawable.velli5_p;
				case 5: return R.drawable.velli6_p;
				case 6: return R.drawable.velli7_p;
				case 7: return R.drawable.velli8_p;
				case 8: return R.drawable.velli9_p;
				case 9: return R.drawable.velli10_p;
				case 10: return R.drawable.velli11_p;
				case 11: return R.drawable.velli12_p;
				case 12: return R.drawable.vk_p;
				default:
			}
		}
		return 0;
	}

	/**
	 * Returns a random resource id for a background image considering the
	 * orientation and all background resources suitable for friendship level
	 * Game.FRIENDS_GOOD
	 *
	 * @param orientation takes a final Configuration.ORIENTATION_ flag.
	 * @return a resource id
	 * @see Configuration
	 * @see Game
	 * @see R
	 */
	private static int getGood(int orientation) {
		if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
			switch(r.nextInt(9)) {
				case 0: return R.drawable.velli5_l;
				case 1: return R.drawable.velli6_l;
				case 2: return R.drawable.velli7_l;
				case 3: return R.drawable.velli8_l;
				case 4: return R.drawable.velli9_l;
				case 5: return R.drawable.velli10_l;
				case 6: return R.drawable.velli11_l;
				case 7: return R.drawable.velli12_l;
				case 8: return R.drawable.vk_l;
				default:
			}
		} else {
			switch(r.nextInt(9)) {
				case 0: return R.drawable.velli5_p;
				case 1: return R.drawable.velli6_p;
				case 2: return R.drawable.velli7_p;
				case 3: return R.drawable.velli8_p;
				case 4: return R.drawable.velli9_p;
				case 5: return R.drawable.velli10_p;
				case 6: return R.drawable.velli11_p;
				case 7: return R.drawable.velli12_p;
				case 8: return R.drawable.vk_p;
				default:
			}
		}
		return 0;
	}

	/**
	 * Returns a random resource id for a background image considering the
	 * orientation and all background resources suitable for friendship level
	 * Game.FRIENDS_LOOSE
	 *
	 * @param orientation takes a final Configuration.ORIENTATION_ flag.
	 * @return a resource id
	 * @see Configuration
	 * @see Game
	 * @see R
	 */
	private static int getLoose(int orientation) {
		if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
			switch(r.nextInt(2)) {
				case 0: return R.drawable.velli5_l;
				case 1: return R.drawable.velli6_l;
				default:
			}
		} else {
			switch(r.nextInt(2)) {
				case 0: return R.drawable.velli5_p;
				case 1: return R.drawable.velli6_p;
				default:
			}
		}
		return 0;
	}

	/**
	 * Updates the friendship level.
	 *
	 * @param friendship_level takes a final Game.FRIENDS_ flag.
	 * @see Game
	 */
	public static void setFriendshipLevel(int friendship_level) { 
		Background.friendship_level = friendship_level;
	}
}
