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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by gautam on 20-11-2016.
 */

public class RegisteredPlacesListFragment extends Fragment {
    private RecyclerView mRegisteredPlacesRecyclerView;
    private RegisteredPlacesBean registeredPlacesBean;
    private RegisteredPlacesAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registeredPlacesBean = ((RegisteredPlacesListActivity)getActivity()).registeredPlacesBean;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registered_places_list_fragment, container, false);
        mRegisteredPlacesRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_registered_places_list);
        mRegisteredPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {
        List<RegisteredPlacesBean.Results> reports = registeredPlacesBean.getallresults();
        Collections.sort(reports, new Comparator<RegisteredPlacesBean.Results>() {

            @Override
            public int compare(RegisteredPlacesBean.Results lhs, RegisteredPlacesBean.Results rhs) {
                return lhs.value - rhs.value;
            }
        });
        if(mAdapter == null){
            mAdapter = new RegisteredPlacesAdapter(reports);
            mRegisteredPlacesRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.setmResults(reports);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private class ReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RegisteredPlacesBean.Results result;
        public TextView name;
        public TextView shortestTime;

        public ReportHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.registered_place_name);
            shortestTime = (TextView) itemView.findViewById(R.id.shortest_time_registered_places_list);
        }

        public void bindReport(RegisteredPlacesBean.Results result) {
            itemView.setOnClickListener(this);
            this.result = result;
            name.setText(result.name);
            shortestTime.setText(result.text);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),AllDetailActivity.class);
            intent.putExtra("user_type",((RegisteredPlacesListActivity)getActivity()).user_type);
            intent.putExtra("user_name", ((RegisteredPlacesListActivity)getActivity()).user_name);
            intent.putExtra("user_phone", ((RegisteredPlacesListActivity)getActivity()).user_phone);
            intent.putExtra("user_sms_number", ((RegisteredPlacesListActivity)getActivity()).user_sms);
            intent.putExtra("user_injured", ((RegisteredPlacesListActivity)getActivity()).user_injured);
            intent.putExtra("user_lat",((RegisteredPlacesListActivity)getActivity()).user_lat);
            intent.putExtra("user_lng", ((RegisteredPlacesListActivity)getActivity()).user_lng);
            intent.putExtra("address", ((RegisteredPlacesListActivity)getActivity()).user_addr);
            intent.putExtra("place_name", result.name);
            intent.putExtra("place_address", result.addr);
            intent.putExtra("place_id", "");
            intent.putExtra("place_lat", result.lat);
            intent.putExtra("place_lng", result.lng);
            startActivity(intent);
        }
    }

    private class RegisteredPlacesAdapter extends RecyclerView.Adapter<ReportHolder>{

        private List<RegisteredPlacesBean.Results> mResults;

        public RegisteredPlacesAdapter(List<RegisteredPlacesBean.Results> results){
            mResults = results;
        }

        @Override
        public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.registered_places_list_item, parent, false);
            return new ReportHolder(v);
        }

        @Override
        public void onBindViewHolder(ReportHolder holder, int position) {
            RegisteredPlacesBean.Results result = mResults.get(position);
            holder.bindReport(result);
        }

        @Override
        public int getItemCount() {
            return mResults.size();
        }

        public void setmResults(List<RegisteredPlacesBean.Results> results){
            mResults = results;
        }
    }
}
