package com.lzf.applianceafter_salesservicesystem.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.applianceafter_salesservicesystem.R;
import com.lzf.applianceafter_salesservicesystem.activity.AddPartActivity;
import com.lzf.applianceafter_salesservicesystem.activity.MaintainerHomeActivity;
import com.lzf.applianceafter_salesservicesystem.bean.Appliance;
import com.lzf.applianceafter_salesservicesystem.util.DBUtil;
import com.lzf.applianceafter_salesservicesystem.util.LogUtil;
import com.lzf.applianceafter_salesservicesystem.util.ReusableAdapter;
import com.lzf.applianceafter_salesservicesystem.util.SPUtil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String APPLIANCE_LIST = "APPLIANCE_LIST";

    private Activity context;
    private ReusableAdapter<Appliance> reusableAdapter;
    private ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private List<Appliance> applianceList = new ArrayList<Appliance>();

    private OnFragmentInteractionListener onFragmentInteractionListener;

    public PartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param applianceList applianceList.
     * @return A new instance of fragment PartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartFragment newInstance(List<Appliance> applianceList) {
        PartFragment fragment = new PartFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(APPLIANCE_LIST, (Serializable) applianceList);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            applianceList = (List<Appliance>) getArguments().getSerializable(APPLIANCE_LIST);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_part, container, false);
        context = getActivity();
        progressBar = view.findViewById(R.id.progressBar);
        Button add_part = view.findViewById(R.id.add_part);
        if ("18334706003".equals(SPUtil.get(context, "maintainer_phone", "")) || "超级维修员".equals(SPUtil.get(context, "maintainer_name", ""))) {
            add_part.setVisibility(View.VISIBLE);
            add_part.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonPressed(AddPartActivity.class, null);
                }
            });
        } else {
            add_part.setVisibility(View.GONE);
        }
        ListView partList = view.findViewById(R.id.part_list);
        reusableAdapter = new ReusableAdapter<Appliance>(applianceList, R.layout.item_part) {
            @Override
            public void bindView(ViewHolder holder, Appliance obj) {
                holder.setText(R.id.appliance_name, "家电名称：" + obj.getAppliance_name());
                holder.setText(R.id.appliance_model, "家电型号：" + obj.getAppliance_model());
                holder.setText(R.id.appliance_part, "零件名称：" + obj.getAppliance_part());
                holder.setText(R.id.appliance_price, "零件价格：" + obj.getAppliance_price() + "");
                holder.setText(R.id.appliance_arg, obj.getAppliance_arg());
            }
        };
        partList.setAdapter(reusableAdapter);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                super.run();
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    applianceList.clear();
                    conn = DBUtil.getConnection();
                    if (conn != null) {
                        ps = conn.prepareStatement("SELECT * FROM appliance");
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            applianceList.add(new Appliance(rs.getInt("appliance_id"), rs.getString("appliance_name"), rs.getString("appliance_model"), rs.getString("appliance_part"), rs.getFloat("appliance_price"), rs.getString("appliance_arg")));
                        }
                        LogUtil.logV("MaintainerHomeActivity", applianceList.toString());
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                reusableAdapter.updateAll(applianceList);
                            }
                        });
                    } else {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DBUtil.closeDB(conn, ps, rs);
                }
            }
        }.start();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Class activityClass, Serializable serializable) {
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.onFragmentInteraction(activityClass, serializable);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Class activityClass, Serializable serializable);
    }
}
