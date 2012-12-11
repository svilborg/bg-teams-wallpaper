package comix.bgteams.utils;

import java.util.ArrayList;
import android.content.Context;

public class Logo {
	private String[] logosNames = { "CSKA", "Levski" };

	public String getRandom() {

		final int randomId = Randomizer.generate(0, logosNames.length - 1);

		return logosNames[randomId];
	}

	public String[] getNames() {
		return logosNames;
	}

	public String getName(int i) {
		return logosNames[i];
	}

	public ArrayList<String> getResourcesLabels(Context context) {
		ArrayList<String> list = new ArrayList<String>();

		int length = logosNames.length;
		for (int i = 1; i < length + 1; i++) {

			int resourceId = context.getResources().getIdentifier("lang_" + i, "string", "comix.apchih.app");

			list.add(context.getResources().getString(resourceId));
		}

		return list;
	}
}