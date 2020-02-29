package com.lzf.applianceafter_salesservicesystem.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.applianceafter_salesservicesystem.R;
import com.lzf.applianceafter_salesservicesystem.activity.AddMaintenanceActivity;
import com.lzf.applianceafter_salesservicesystem.activity.UserDetailActivity;
import com.lzf.applianceafter_salesservicesystem.activity.ModifyMaintenanceActivity;
import com.lzf.applianceafter_salesservicesystem.bean.Maintenance;
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
 * {@link MaintenanceUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MaintenanceUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaintenanceUserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Activity context;
    private ProgressBar progressBar;
    private ReusableAdapter<Maintenance> reusableAdapter;
    private List<Maintenance> maintenanceList = new ArrayList<Maintenance>();

    private OnFragmentInteractionListener mListener;

    public MaintenanceUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MaintenanceUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MaintenanceUserFragment newInstance(String param1, String param2) {
        MaintenanceUserFragment fragment = new MaintenanceUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintenance_user, container, false);
        context = getActivity();
        progressBar = view.findViewById(R.id.progressBar);
        view.findViewById(R.id.add_maintenance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddMaintenanceActivity.class));
            }
        });
        ListView listView = view.findViewById(R.id.maintenance_list);
        reusableAdapter = new ReusableAdapter<Maintenance>(maintenanceList, R.layout.item_maintenance_user) {
            @Override
            public void bindView(ViewHolder holder, final Maintenance obj) {
                holder.setText(R.id.maintenance_name, "维修简述：" + obj.getMaintenance_name());
                holder.setText(R.id.maintenance_address, obj.getMaintenance_address());
                holder.setText(R.id.maintenance_appliance, obj.getMaintenance_appliance());
                holder.setVisibility(R.id.cancel, View.GONE);
                holder.setVisibility(R.id.modify, View.GONE);
                switch (obj.getMaintenance_status()) {
                    case 0:
                        holder.setText(R.id.maintenance_status, "当前状态：未接取");
                        holder.setVisibility(R.id.cancel, View.VISIBLE);
                        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressBar.setVisibility(View.VISIBLE);
                                new Thread() {
                                    @Override
                                    public void run() {
                                        super.run();
                                        Connection conn = null;
                                        PreparedStatement ps = null;
                                        ResultSet rs = null;
                                        try {
                                            maintenanceList.clear();
                                            conn = DBUtil.getConnection();
                                            if (conn != null) {
                                                ps = conn.prepareStatement("UPDATE maintenance SET maintenance_status = ? WHERE maintenance_id = " + obj.getMaintenance_id());
                                                ps.setInt(1, 3);
                                                int exeNums = ps.executeUpdate();
                                                LogUtil.logV("MaintenanceUserFragment", "更新条数：" + exeNums);
                                                if (exeNums == 1) {
                                                    ps = conn.prepareStatement("SELECT * FROM maintenance where maintenance_user = " + SPUtil.get(context, "user_id", 0));
                                                    rs = ps.executeQuery();
                                                    while (rs.next()) {
                                                        maintenanceList.add(new Maintenance(rs.getInt("maintenance_id"), rs.getString("maintenance_name"), rs.getString("maintenance_address"), rs.getString("maintenance_appliance"), rs.getInt("maintenance_user"), rs.getInt("maintenance_status"), rs.getInt("maintenance_maintainer")));
                                                    }
                                                    LogUtil.logV("MaintainerHomeActivity", maintenanceList.toString());
                                                    context.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            progressBar.setVisibility(View.GONE);
                                                            reusableAdapter.updateAll(maintenanceList);
                                                        }
                                                    });
                                                } else {
                                                    context.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(context, "取消失败", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
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
                        });
                        holder.setVisibility(R.id.modify, View.VISIBLE);
                        holder.setOnClickListener(R.id.modify, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onButtonPressed(ModifyMaintenanceActivity.class, obj);
                            }
                        });
                        break;
                    case 1:
                        holder.setText(R.id.maintenance_status, "当前状态：已接取");
                        break;
                    case 3:
                        holder.setText(R.id.maintenance_status, "当前状态：已取消");
                        holder.setVisibility(R.id.modify, View.VISIBLE);
                        holder.setOnClickListener(R.id.modify, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onButtonPressed(ModifyMaintenanceActivity.class, obj);
                            }
                        });
                        break;
                    case 4:
                        holder.setText(R.id.maintenance_status, "当前状态：已完成");
                        break;
                    default:
                        break;
                }
                holder.setOnClickListener(R.id.detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonPressed(UserDetailActivity.class, obj);
                    }
                });
            }
        };
        listView.setAdapter(reusableAdapter);
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
                    maintenanceList.clear();
                    conn = DBUtil.getConnection();
                    if (conn != null) {
                        ps = conn.prepareStatement("SELECT * FROM maintenance where maintenance_user = " + SPUtil.get(context, "user_id", 0));
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            maintenanceList.add(new Maintenance(rs.getInt("maintenance_id"), rs.getString("maintenance_name"), rs.getString("maintenance_address"), rs.getString("maintenance_appliance"), rs.getInt("maintenance_user"), rs.getInt("maintenance_status"), rs.getInt("maintenance_maintainer")));
                        }
                        LogUtil.logV("MaintainerHomeActivity", maintenanceList.toString());
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                reusableAdapter.updateAll(maintenanceList);
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
        if (mListener != null) {
            mListener.onFragmentInteraction(activityClass, serializable);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
