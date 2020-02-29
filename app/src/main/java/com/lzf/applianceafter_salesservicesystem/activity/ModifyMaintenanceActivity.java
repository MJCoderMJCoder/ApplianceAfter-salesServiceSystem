package com.lzf.applianceafter_salesservicesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.lzf.applianceafter_salesservicesystem.R;
import com.lzf.applianceafter_salesservicesystem.bean.Maintenance;
import com.lzf.applianceafter_salesservicesystem.util.DBUtil;
import com.lzf.applianceafter_salesservicesystem.util.LogUtil;
import com.lzf.applianceafter_salesservicesystem.util.ReusableAdapter;
import com.lzf.applianceafter_salesservicesystem.util.SPUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModifyMaintenanceActivity extends AppCompatActivity {


    private EditText maintenance_name;
    private EditText maintenance_address;
    private EditText maintenance_appliance;
    private Spinner spinner;

    private List<String> stringList = new ArrayList<String>();
    private Maintenance maintenance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_maintenance);
        Intent intent = getIntent();
        if (intent != null) {
            maintenance = (Maintenance) intent.getSerializableExtra("maintenance");
            if (maintenance != null) {
                maintenance_name = findViewById(R.id.maintenance_name);
                maintenance_address = findViewById(R.id.maintenance_address);
                maintenance_appliance = findViewById(R.id.maintenance_appliance);
                spinner = findViewById(R.id.spinner);
                maintenance_name.setText(maintenance.getMaintenance_name());
                maintenance_address.setText(maintenance.getMaintenance_address());
                maintenance_appliance.setText(maintenance.getMaintenance_appliance());
                stringList.add("未接取");
                stringList.add("已取消");
                spinner.setAdapter(new ReusableAdapter<String>(stringList, R.layout.item_status) {
                    @Override
                    public void bindView(ViewHolder holder, String obj) {
                        holder.setText(R.id.status, obj);
                    }
                });
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String status = ((TextView) view.findViewById(R.id.status)).getText().toString().trim();
                        if ("未接取".equals(status)) {
                            maintenance.setMaintenance_status(0);
                        } else if ("已取消".equals(status)) {
                            maintenance.setMaintenance_status(3);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                if (maintenance.getMaintenance_status() == 0) {
                    spinner.setSelection(0, true);
                } else if (maintenance.getMaintenance_status() == 3) {
                    spinner.setSelection(1, true);
                }
            } else {
                Toast.makeText(ModifyMaintenanceActivity.this, "请先选择一项在进行修改", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } else {
            Toast.makeText(ModifyMaintenanceActivity.this, "请先选择一项在进行修改", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                final String maintenanceName = maintenance_name.getText().toString().trim();
                final String maintenanceAddress = maintenance_address.getText().toString().trim();
                final String maintenanceAppliance = maintenance_appliance.getText().toString().trim();
                if (maintenanceName == null || "".equals(maintenanceName)) {
                    maintenance_name.setFocusable(true);
                    maintenance_name.setFocusableInTouchMode(true);
                    maintenance_name.requestFocus();
                    Toast.makeText(ModifyMaintenanceActivity.this, "请对维修申请进行简单描述", Toast.LENGTH_SHORT).show();
                } else if (maintenanceAddress == null || "".equals(maintenanceAddress)) {
                    maintenance_address.setFocusable(true);
                    maintenance_address.setFocusableInTouchMode(true);
                    maintenance_address.requestFocus();
                    Toast.makeText(ModifyMaintenanceActivity.this, "请输入此次维修申请的详细地址信息", Toast.LENGTH_SHORT).show();
                } else if (maintenanceAppliance == null || "".equals(maintenanceAppliance)) {
                    maintenance_appliance.setFocusable(true);
                    maintenance_appliance.setFocusableInTouchMode(true);
                    maintenance_appliance.requestFocus();
                    Toast.makeText(ModifyMaintenanceActivity.this, "请输入维修申请的其他家电信息和详情", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Connection conn = null;
                            PreparedStatement ps = null;
                            ResultSet rs = null;
                            try {
                                conn = DBUtil.getConnection();
                                if (conn != null) {

                                    ps = conn.prepareStatement("UPDATE maintenance SET maintenance_name = ?, maintenance_address = ?, maintenance_appliance = ?, maintenance_status = ? WHERE maintenance_id = " + maintenance.getMaintenance_id());
                                    ps.setString(1, maintenanceName);
                                    ps.setString(2, maintenanceAddress);
                                    ps.setString(3, maintenanceAppliance);
                                    ps.setInt(4, maintenance.getMaintenance_status());
                                    int exeNums = ps.executeUpdate();
                                    LogUtil.logV("ModifyMaintenanceActivity", "更新条数：" + exeNums);
                                    if (exeNums == 1) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(ModifyMaintenanceActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                                ModifyMaintenanceActivity.this.finish();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ModifyMaintenanceActivity.this, "抱歉，提交失败！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ModifyMaintenanceActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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
                break;
            default:
                break;
        }
    }
}
