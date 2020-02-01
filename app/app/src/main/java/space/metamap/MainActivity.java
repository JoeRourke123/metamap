package space.metamap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.radar.sdk.Radar;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Radar.initialize("prj_test_pk_827b0d73644e0cb263e476ca7271b845296b6283");
		SharedPreferences preferences = getSharedPreferences("metamapp", MODE_PRIVATE);

		if(preferences.getString("session", "").equals("")) {
			Intent intent = new Intent(this, space.metamap.ui.login.LoginActivity.class);
			startActivity(intent);
		}

		setContentView(R.layout.activity_main);
		BottomNavigationView navView = findViewById(R.id.nav_view);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
			R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
			.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
			@Override
			public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

			}
		});
//		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);

	}

}
