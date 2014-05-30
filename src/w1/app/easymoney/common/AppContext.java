package w1.app.easymoney.common;

import android.app.Application;

public class AppContext extends Application {

	private static AppContext INSTANCE;
	public static AppContext getInstance(){
		return INSTANCE;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		INSTANCE = this;
	}
}
