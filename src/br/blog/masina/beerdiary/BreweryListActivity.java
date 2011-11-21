package br.blog.masina.beerdiary;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewSwitcher;

public class BreweryListActivity extends Activity implements OnClickListener,
		OnGestureListener {

	int SWIPE_MIN_VELOCITY = 100;
	int SWIPE_MIN_DISTANCE = 100;

	private ViewSwitcher switcher;
	private Button btnNext, btnPrevious;
	private GestureDetector gesturedetector = null;

	// private EditText name, website, description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brewery_list);

		switcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
		gesturedetector = new GestureDetector(this, this);

		getList();

	}
	
	private void getList() {
		DAO dao = new DAO( getBaseContext() );
		BreweryClientAdapter bca = new BreweryClientAdapter(getBaseContext(), dao.breweryGetAll());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getList();
	}
	
	protected void onListItemClick( ListView l, View v, int position, long id ) {
		BreweryVO client = ( BreweryVO )l.getAdapter().getItem( position );
		//Intent it = new Intent( getBaseContext(), BreweryEditActivity.class );
		//it.putExtra( "id", String.valueOf( client.getId() ) );
		//startActivity( it );		
	}	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gesturedetector.onTouchEvent(event);
	}

	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	
		// Get Position
		float ev1X = e1.getX();
		float ev2X = e2.getX();

		// Get distance of X (e1) to X (e2)
		final float xdistance = Math.abs(ev1X - ev2X);
		// Get veclocity of cusor
		final float xvelocity = Math.abs(velocityX);

		if ((xvelocity > SWIPE_MIN_VELOCITY)
				&& (xdistance > SWIPE_MIN_DISTANCE)) {
			if (ev1X > ev2X)// Switch Left
			{
				previousView();
			} else// Switch Right
			{
				nextView();
			}
		}
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btnNext:
			// Button Next Style
			Animation animationNext = AnimationUtils.loadAnimation(this,
					R.anim.btn_style_next);
			btnNext.startAnimation(animationNext);

			nextView();
			break;

		case R.id.btnPrevious:
			// Button Previous Style
			Animation animationPrevious = AnimationUtils.loadAnimation(this,
					R.anim.btn_style_previous);
			btnPrevious.startAnimation(animationPrevious);

			previousView();
			break;
		}
	}

	// NEXT AND PREVIOWS VIEWS

	private void nextView() {
		// Next View
		switcher.setInAnimation(this, R.anim.in_animation);
		switcher.setOutAnimation(this, R.anim.out_animation);
		switcher.showNext();
	}

	private void previousView() {
		// Previous View
		switcher.setInAnimation(this, R.anim.in_animation1);
		switcher.setOutAnimation(this, R.anim.out_animation1);
		switcher.showPrevious();
	}

}
