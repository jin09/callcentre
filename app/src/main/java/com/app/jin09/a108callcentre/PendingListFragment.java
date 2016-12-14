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
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class PendingListFragment extends Fragment {
    private RecyclerView mPendingRecyclerView;
    private PendingAdapter mAdapter;
    private BackendDataBean getBackendDataBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBackendDataBean = ((MainActivity)getActivity()).backendDataBean;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pending_list_fragment, container, false);
        mPendingRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_pending_list);
        mPendingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {
        List<BackendDataBean.Requests> reports = getBackendDataBean.getallrequests();
        if(mAdapter == null){
            mAdapter = new PendingAdapter(reports);
            mPendingRecyclerView.setAdapter(mAdapter);
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

    private class ReportHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private BackendDataBean.Requests mReport;
        public TextView type;
        public TextView name;
        public TextView phone;
        public TextView sms;
        public Button injured;
        public TextView lat;
        public TextView lng;
        public TextView address;
        public Button registered_list_activity;

        public ReportHolder(View itemView){
            super(itemView);
            type = (TextView)
                    itemView.findViewById(R.id.type);
            name = (TextView)
                    itemView.findViewById(R.id.name);
            phone = (TextView)
                    itemView.findViewById(R.id.phone);
            sms = (TextView)
                    itemView.findViewById(R.id.sms);
            injured = (Button)
                    itemView.findViewById(R.id.injured);
            lat = (TextView)
                    itemView.findViewById(R.id.lat);
            lng = (TextView)
                    itemView.findViewById(R.id.lng);
            address = (TextView)
                    itemView.findViewById(R.id.address);
            registered_list_activity = (Button) itemView.findViewById(R.id.registered_list_button);
        }

        private void bindReport(BackendDataBean.Requests report){
            mReport = report;
            itemView.setOnClickListener(this);
            type.setText(mReport.type);
            name.setText(mReport.name);
            phone.setText(mReport.phone);
            sms.setText(mReport.smsnumber);
            injured.setText(mReport.injured);
            lat.setText(mReport.latitude);
            lng.setText(mReport.longitude);
            address.setText(mReport.address);
            if(mReport.injured.equals("0")){
                injured.setEnabled(false);
            }
            registered_list_activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RegisteredPlacesListActivity.class);
                    intent.putExtra("user_lat", mReport.latitude);
                    intent.putExtra("user_lng", mReport.longitude);
                    intent.putExtra("user_type", mReport.type);
                    intent.putExtra("user_name", mReport.name);
                    intent.putExtra("user_phone", mReport.phone);
                    intent.putExtra("user_sms", mReport.smsnumber);
                    intent.putExtra("user_injured", mReport.injured);
                    intent.putExtra("user_addr", mReport.address);
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),DetailActivity.class);
            intent.putExtra("user_type",mReport.type);
            intent.putExtra("user_name", mReport.name);
            intent.putExtra("user_phone", mReport.phone);
            intent.putExtra("user_sms_number", mReport.smsnumber);
            intent.putExtra("user_injured", mReport.injured);
            intent.putExtra("user_lat",mReport.latitude);
            intent.putExtra("user_lng", mReport.longitude);
            intent.putExtra("address", mReport.address);
            intent.putExtra("type",type.getText());
            intent.putExtra("lat",lat.getText());
            intent.putExtra("longi",lng.getText());
            startActivity(intent);
        }
    }


    private class PendingAdapter extends RecyclerView.Adapter<ReportHolder>{
        private List<BackendDataBean.Requests> mReports;

        public PendingAdapter (List<BackendDataBean.Requests> reports){
            mReports = reports;
        }

        @Override
        public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_report, parent, false);
            return new ReportHolder(v);
        }

        @Override
        public void onBindViewHolder(ReportHolder holder, int position) {
            BackendDataBean.Requests report = mReports.get(position);
            holder.bindReport(report);
        }

        @Override
        public int getItemCount() {
            return mReports.size();
        }

        public void setmReports(List<BackendDataBean.Requests> reports){
            mReports = reports;
        }
    }
}
