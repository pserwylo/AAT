package ch.bailu.aat.views.map.overlay.gpx;

import android.graphics.drawable.Drawable;

import ch.bailu.aat.helpers.AppTheme;
import ch.bailu.aat.services.ServiceContext;
import ch.bailu.aat.views.map.AbsOsmView;
import ch.bailu.aat.views.map.overlay.MapPainter;
import ch.bailu.aat.views.map.overlay.MapTwoNodes;
import ch.bailu.aat.views.map.overlay.MapTwoNodes.PixelNode;

public class WayOverlay extends GpxOverlay {
    private static final int MAX_VISIBLE_NODES=500;

    private final ServiceContext scontext;

    public WayOverlay(AbsOsmView osm, ServiceContext scontext, int id) {
        this(osm, scontext, id, AppTheme.getHighlightColor2());

    }



    public WayOverlay(AbsOsmView osm, ServiceContext sc, int id, int color) {
        super(osm, id, color);
        scontext = sc;
    }



    @Override
    public void draw(MapPainter p) {
        new WayPainter(p).walkTrack(getGpxList());
    }


    private class WayPainter extends GpxListPainter {

        private final MapPainter painter;
        private int count=0;

        public WayPainter(MapPainter p) {
            super(p);
            painter = p;
        }

        
        @Override
        public void drawEdge(MapTwoNodes nodes) {

        }

   


        @Override
        public void drawNode(PixelNode node) {
            if (node.isVisible() && count < MAX_VISIBLE_NODES) {

                final Drawable nodeBitmap = getOsmView().mapIconCache.getSmallIcon(scontext, node.point);

                if (nodeBitmap != null) {
                    painter.canvas.draw(nodeBitmap, node.pixel);
                } else {
                    painter.canvas.draw(painter.nodeBitmap, node.pixel, getColor());
                }
                count++;

            }
        }
    }
}
