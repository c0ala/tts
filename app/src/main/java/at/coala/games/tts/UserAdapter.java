package at.coala.games.tts;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import at.coala.games.tts.data.UpdateUserData;
import at.coala.games.tts.data.User;
import at.coala.games.tts.game.GameDataManager;

/**
 * UserAdapter extends the ArrayAdapter, a concrete BaseAdapter that is backed
 * by an array of arbitrary objects. For more information read the description
 * of ArrayAdapter.
 *
 * @author Klaus
 */
public class UserAdapter extends ArrayAdapter<User> {

	/**
	 * A private list containing all existing users.
	 *
	 * @see List
	 * @see User
	 */
	private List<User> userList;

	/**
	 * Create a new UserAdapter.
	 *
	 * @param context The current context.
	 * @param userList The objects to represent in the ListView.
	 */
	public UserAdapter(Context context, List<User> userList) {
		super(context, R.layout.user_item, userList);
		this.userList = userList;
	}

	/**
	 * Get a View that displays the data at the specified position in the data
	 * set. For more information read the description of ArrayAdapter. This
	 * method sets listeners to the delete button and checkbox. If the delete
	 * button ic clicked, the player will be deleted from the game and
	 * database. If checkbox is set, the player will be added to the game,
	 * else he will be removed from the game.
	 *
	 * @param position The position of the item within the adapter's data set
	 *                    of the item whose view we want.
	 * @param convertView The old view if possible to reuse, else null.
	 * @param parent The parent that this view will eventually be attached to.
	 * @return A View corresponding to the data at the specified position.
	 * @see ArrayAdapter
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		User user = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
		}
		((TextView)convertView.findViewById(R.id.text_view)).setText(user.name);
		((ImageView)convertView.findViewById(R.id.user_sex_image)).setImageResource(user.sex == User.SEX_FEMALE ? R.drawable.f : R.drawable.m);
		
		CheckBox play_checkBox = (CheckBox)convertView.findViewById(R.id.play_checkBox);
		play_checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			/**
			 * Called when the checked state of a compound button has changed.
			 * For more information read the method description in
			 * OnCheckedChangeListener. This method listens to the check box
			 * to add users to, or remove them from, the game.
			 *
			 * @param buttonView The compound button view whose state has changed.
			 * @param isChecked The new checked state of buttonView.
			 * @see CompoundButton
			 * @see OnCheckedChangeListener
			 */
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					UpdateUserData.addPlayer(getContext(), userList.get(position));
				} else {
					UpdateUserData.removePlayer(getContext(), userList.get(position));
				}
			}
		});
		play_checkBox.setChecked(GameDataManager.containsUser(user));

		Button delete_button = (Button)convertView.findViewById(R.id.delete_button);
		delete_button.setOnClickListener(new OnClickListener() {

			/**
			 * Called when a view has been clicked. For more information read
			 * the method description in OnClickListener. If clicked, the
			 * player gets deleted from the game and database.
			 *
			 * @param v The view that was clicked.
			 * @see OnClickListener
			 * @see View
			 */
			@Override
			public void onClick(View v) {
				UpdateUserData.removeUser(getContext(), userList, position);
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}
}