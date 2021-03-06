package ch.bailu.aat.activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import ch.bailu.aat.R;
import ch.bailu.aat.gpx.GpxInformation;
import ch.bailu.aat.preferences.SolidPreset;
import ch.bailu.aat.services.tileremover.TileRemoverService;
import ch.bailu.aat.views.ContentView;
import ch.bailu.aat.views.MainControlBar;
import ch.bailu.aat.views.description.MultiView;
import ch.bailu.aat.views.preferences.GeneralPreferencesView;
import ch.bailu.aat.views.preferences.MapTilePreferencesView;
import ch.bailu.aat.views.preferences.PresetPreferencesView;

public class PreferencesActivity extends AbsDispatcher {

    private final static String SOLID_KEY=PreferencesActivity.class.getSimpleName();

    private MapTilePreferencesView mapTilePreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createViews();
    }


    private void createViews() {
        LinearLayout contentView = new ContentView(this);

        MultiView multiView = createMultiView();
        contentView.addView(new MainControlBar(getServiceContext(), multiView));
        contentView.addView(multiView);

        setContentView(contentView);
    }



    private MultiView createMultiView() {
        MultiView mv = new MultiView(this, SOLID_KEY, GpxInformation.ID.INFO_ID_ALL);

        final int l = new SolidPreset(this).length();

        mapTilePreferences = new MapTilePreferencesView(getServiceContext());
        mv.add(new GeneralPreferencesView(this),
                getString(R.string.p_general)+ "/"+ getString(R.string.p_system));
        mv.add(mapTilePreferences,
                getString(R.string.p_tiles));

        for (int i = 0; i < l; i++) {
            mv.add(new PresetPreferencesView(this, i),
                    getString(R.string.p_preset) + " " + (i + 1));
        }

        return mv;
    }


    @Override
    public void onResumeWithService() {
        super.onResumeWithService();
        mapTilePreferences.updateText();
    }

    @Override
    public void onDestroy() {
        TileRemoverService ts = getServiceContext().getTileRemoverService();

        if (ts != null) ts.getState().reset();

        super.onDestroy();

    }
}
