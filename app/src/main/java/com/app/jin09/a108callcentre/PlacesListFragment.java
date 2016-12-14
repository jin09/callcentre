package com.app.jin09.a108callcentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by gautam on 18-11-2016.
 */

public class PlacesListFragment extends Fragment{
    private RecyclerView mPlacesRecyclerView;
    private PlacesAdapter mAdapter;
    private PlacesBean placesBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placesBean = ((DetailActivity)getActivity()).placesbean;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.places_list_fragment, container, false);
        mPlacesRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_places_list);
        mPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {
        List<PlacesBean.Results> reports = placesBean.getallresults();
        Collections.sort(reports, new Comparator<PlacesBean.Results>() {
            @Override
            public int compare(PlacesBean.Results lhs, PlacesBean.Results rhs) {
                return lhs.seconds - rhs.seconds;
            }
        });
        if(mAdapter == null){
            mAdapter = new PlacesAdapter(reports);
            mPlacesRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setmReports(reports);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private class ReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Serializable{
        private PlacesBean.Results mReport;
        public ImageView image;
        public TextView name;
        public TextView shortestTime;

        public ReportHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.place_name);
            image = (ImageView) itemView.findViewById(R.id.place_icon);
            shortestTime = (TextView) itemView.findViewById(R.id.shortest_time_places_list);
        }

        private void bindReport(PlacesBean.Results result){
            mReport = result;
            itemView.setOnClickListener(this);
            name.setText(mReport.name);
            Picasso.with(getActivity()).load(mReport.iconlink).into(image);
            shortestTime.setText(mReport.shortestDistance);
        }

        @Override
        public void onClick(View v) {
            PlacesBean.Results result = mReport;
            Intent intent = new Intent(getActivity(),AllDetailActivity.class);
            intent.putExtra("user_type",((DetailActivity)getActivity()).user_type);
            intent.putExtra("user_name", ((DetailActivity)getActivity()).user_name);
            intent.putExtra("user_phone", ((DetailActivity)getActivity()).user_phone);
            intent.putExtra("user_sms_number", ((DetailActivity)getActivity()).user_sms_number);
            intent.putExtra("user_injured", ((DetailActivity)getActivity()).user_injured);
            intent.putExtra("user_lat",((DetailActivity)getActivity()).user_lat);
            intent.putExtra("user_lng", ((DetailActivity)getActivity()).user_lng);
            intent.putExtra("address", ((DetailActivity)getActivity()).address);
            intent.putExtra("place_name", result.name);
            intent.putExtra("place_address", result.address);
            intent.putExtra("place_id", result.placeID);
            intent.putExtra("place_lat", result.lat);
            intent.putExtra("place_lng", result.lon);
            startActivity(intent);
        }
    }


    private class PlacesAdapter extends RecyclerView.Adapter<PlacesListFragment.ReportHolder>{
        private List<PlacesBean.Results> mReports;

        public PlacesAdapter (List<PlacesBean.Results> reports){
            mReports = reports;
        }

        @Override
        public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.places_list_item, parent, false);
            return new ReportHolder(v);
        }

        @Override
        public void onBindViewHolder(ReportHolder holder, int position) {
            PlacesBean.Results result = mReports.get(position);
            holder.bindReport(result);
        }

        @Override
        public int getItemCount() {
            return mReports.size();
        }

        public void setmReports(List<PlacesBean.Results> reports){
            mReports = reports;
        }
    }
}
