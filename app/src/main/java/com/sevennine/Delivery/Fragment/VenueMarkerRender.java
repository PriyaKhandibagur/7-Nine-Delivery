package com.sevennine.Delivery.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.sevennine.Delivery.Bean.ClusterBean;
import com.sevennine.Delivery.R;

import java.util.concurrent.ExecutionException;

class VenueMarkerRender extends DefaultClusterRenderer<ClusterBean> {

    private final Context mContext;

    public VenueMarkerRender(Context context, GoogleMap map, ClusterManager<ClusterBean> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(ClusterBean item, MarkerOptions markerOptions) {
       // Bitmap venueCircle = VenueCircleFactory.createFromVenue(item);


      /*  try {
            bitmap  = Glide.with(mContext).load(R.drawable.avatarmale).into(200, 200).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

      //  markerOptions.icon(R.drawable.addrs_top_curve);
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<ClusterBean> cluster){
        return cluster.getSize() > 1;
    }
}