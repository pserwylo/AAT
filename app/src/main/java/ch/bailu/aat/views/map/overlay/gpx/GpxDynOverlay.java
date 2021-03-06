package ch.bailu.aat.views.map.overlay.gpx;

import ch.bailu.aat.gpx.GpxInformation;
import ch.bailu.aat.gpx.GpxList;
import ch.bailu.aat.gpx.interfaces.GpxType;
import ch.bailu.aat.preferences.SolidLegend;
import ch.bailu.aat.services.ServiceContext;
import ch.bailu.aat.views.map.AbsOsmView;
import ch.bailu.aat.views.map.overlay.MapPainter;
import ch.bailu.aat.views.map.overlay.NullOverlay;
import ch.bailu.aat.views.map.overlay.OsmOverlay;

public class GpxDynOverlay extends OsmOverlay {

    private OsmOverlay gpx;
    private OsmOverlay legend;
    
    private final int ID;
    private final int color;

    private final ServiceContext scontext;
    
    private final SolidLegend slegend;
    
    private GpxInformation info;
    
    
    public GpxDynOverlay(AbsOsmView map, ServiceContext sc, int id) {
        this(map,sc, id,-1);
    }

    public GpxDynOverlay(AbsOsmView map, ServiceContext sc,  int id, int c) {
        super(map);
        color=c;
        scontext =sc;
        ID = id;
        gpx = new NullOverlay(map);
        legend = new NullOverlay(map);
        
        slegend = new SolidLegend(map.getContext(), map.solidKey);
    }




    @Override
    public void draw(MapPainter p) {
        gpx.draw(p);
        legend.draw(p);
    }


    @Override
    public void onContentUpdated(GpxInformation i) {
        if (i.getID()== ID) {
            info=i;
            setTrack(info.getGpxList());
            gpx.onContentUpdated(info);
            legend.onContentUpdated(info);
        }

    }



    public void setTrack(GpxList gpxList) {

        if (gpxList.getDelta().getType()== GpxType.WAY) {
            if (color == -1) gpx = new WayOverlay(getOsmView(), scontext, ID);
            else gpx = new WayOverlay(getOsmView(), scontext, ID, color);
            legend = slegend.createWayLegendOverlay(getOsmView(), ID);

        } else if (gpxList.getDelta().getType()==GpxType.RTE) {
            if (color == -1)  gpx = new RouteOverlay(getOsmView(), ID);
            else gpx = new RouteOverlay(getOsmView(), ID, color);
            legend = slegend.createRouteLegendOverlay(getOsmView(), ID);
                
        } else {
            gpx = new TrackOverlay(getOsmView(), ID);
            legend = slegend.createTrackLegendOverlay(getOsmView(), ID);
        }
    }

    
    @Override
    public void onSharedPreferenceChanged(String key) {
        if (slegend.hasKey(key) && info != null) onContentUpdated(info);
    }
}
