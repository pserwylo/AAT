package ch.bailu.aat.description;


import android.content.Context;

import java.util.Locale;

import ch.bailu.aat.R;
import ch.bailu.aat.gpx.GpxInformation;

public class DateDescription extends LongDescription {
    public DateDescription(Context context) {
        super(context);
    }

    @Override
    public String getLabel() {
        return getString(R.string.d_startdate);
    }
    @Override
    public String getValue()   { 
        return String.format((Locale) null, "%tF - %tT",getCache(), getCache());
    }

    @Override
    public void onContentUpdated(GpxInformation info) {
        setCache(info.getStartTime());
    }

}
