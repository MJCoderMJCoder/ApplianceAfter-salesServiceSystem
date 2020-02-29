package com.lzf.applianceafter_salesservicesystem.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.lzf.applianceafter_salesservicesystem.R;
import com.lzf.applianceafter_salesservicesystem.util.DBUtil;
import com.lzf.applianceafter_salesservicesystem.util.LogUtil;
import com.lzf.applianceafter_salesservicesystem.util.SPUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddMaintenanceActivity extends AppCompatActivity {

    private final int MAINTENANCE_STATUS = 0; //0-未接取【用户】/可接取【维修工】   1-已接取【用户】/未完成【维修工】  3-已取消 4-已完成
    private int maintenance_user = 1;

    private EditText maintenance_name;
    private EditText maintenance_address;
    private EditText maintenance_appliance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintenance);
        maintenance_user = (int) SPUtil.get(getApplicationContext(), "user_id", 1);
        maintenance_name = findViewById(R.id.maintenance_name);
        maintenance_address = findViewById(R.id.maintenance_address);
        maintenance_appliance = findViewById(R.id.maintenance_appliance);
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
                    Toast.makeText(AddMaintenanceActivity.this, "请对维修申请进行简单描述", Toast.LENGTH_SHORT).show();
                } else if (maintenanceAddress == null || "".equals(maintenanceAddress)) {
                    maintenance_address.setFocusable(true);
                    maintenance_address.setFocusableInTouchMode(true);
                    maintenance_address.requestFocus();
                    Toast.makeText(AddMaintenanceActivity.this, "请输入此次维修申请的详细地址信息", Toast.LENGTH_SHORT).show();
                } else if (maintenanceAppliance == null || "".equals(maintenanceAppliance)) {
                    maintenance_appliance.setFocusable(true);
                    maintenance_appliance.setFocusableInTouchMode(true);
                    maintenance_appliance.requestFocus();
                    Toast.makeText(AddMaintenanceActivity.this, "请输入维修申请的其他家电信息和详情", Toast.LENGTH_SHORT).show();
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
                                    ps = conn.prepareStatement("INSERT INTO maintenance (maintenance_name, maintenance_address, maintenance_appliance, maintenance_user, maintenance_status) VALUES (?, ?, ?, ?, ?)");
                                    ps.setString(1, maintenanceName);
                                    ps.setString(2, maintenanceAddress);
                                    ps.setString(3, maintenanceAppliance);
                                    ps.setInt(4, maintenance_user);
                                    ps.setInt(5, MAINTENANCE_STATUS);
                                    int exeNums = ps.executeUpdate();
                                    LogUtil.logV("AddMaintenanceActivity", "插入条数：" + exeNums);
                                    if (exeNums == 1) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(AddMaintenanceActivity.this, "提交成功！", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                                AddMaintenanceActivity.this.finish();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(AddMaintenanceActivity.this, "抱歉，提交失败！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(AddMaintenanceActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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
