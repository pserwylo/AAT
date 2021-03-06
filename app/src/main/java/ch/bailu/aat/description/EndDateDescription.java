package ch.bailu.aat.description;


import android.content.Context;

import ch.bailu.aat.R;
import ch.bailu.aat.gpx.GpxInformation;

public class EndDateDescription extends DateDescription {

    public EndDateDescription(Context context) {
        super(context);
    }

    
    @Override
    public String getLabel() {
        return getString(R.string.d_enddate);
    }

    @Override
    public void onContentUpdated(GpxInformation info) {
        setCache(info.getEndTime());
    }
}
