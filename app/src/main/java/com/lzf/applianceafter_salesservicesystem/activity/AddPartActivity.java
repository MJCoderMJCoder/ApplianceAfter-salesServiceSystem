package com.lzf.applianceafter_salesservicesystem.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.lzf.applianceafter_salesservicesystem.R;
import com.lzf.applianceafter_salesservicesystem.util.DBUtil;
import com.lzf.applianceafter_salesservicesystem.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPartActivity extends AppCompatActivity {

    private EditText appliance_name;
    private EditText appliance_model;
    private EditText appliance_part;
    private EditText appliance_price;
    private EditText appliance_arg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_part);


        appliance_name = findViewById(R.id.appliance_name);
        appliance_model = findViewById(R.id.appliance_model);
        appliance_part = findViewById(R.id.appliance_part);
        appliance_price = findViewById(R.id.appliance_price);
        appliance_arg = findViewById(R.id.appliance_arg);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                try {
                    final String applianceName = appliance_name.getText().toString().trim();
                    final String applianceModel = appliance_model.getText().toString().trim();
                    final String appliancePart = appliance_part.getText().toString().trim();
                    final Float appliancePrice = Float.parseFloat(appliance_price.getText().toString().trim());
                    final String applianceArg = appliance_arg.getText().toString().trim();
                    if (applianceName == null || "".equals(applianceName)) {
                        appliance_name.setFocusable(true);
                        appliance_name.setFocusableInTouchMode(true);
                        appliance_name.requestFocus();
                        Toast.makeText(AddPartActivity.this, "请输入家电名称", Toast.LENGTH_SHORT).show();
                    } else if (applianceModel == null || "".equals(applianceModel)) {
                        appliance_model.setFocusable(true);
                        appliance_model.setFocusableInTouchMode(true);
                        appliance_model.requestFocus();
                        Toast.makeText(AddPartActivity.this, "请输入家电型号", Toast.LENGTH_SHORT).show();
                    } else if (appliancePart == null || "".equals(appliancePart)) {
                        appliance_part.setFocusable(true);
                        appliance_part.setFocusableInTouchMode(true);
                        appliance_part.requestFocus();
                        Toast.makeText(AddPartActivity.this, "请输入家电零件名称", Toast.LENGTH_SHORT).show();
                    } else if (appliancePrice == null || "".equals(appliancePrice)) {
                        appliance_price.setFocusable(true);
                        appliance_price.setFocusableInTouchMode(true);
                        appliance_price.requestFocus();
                        Toast.makeText(AddPartActivity.this, "请输入零件价格", Toast.LENGTH_SHORT).show();
                    } else if (applianceArg == null || "".equals(applianceArg)) {
                        appliance_arg.setFocusable(true);
                        appliance_arg.setFocusableInTouchMode(true);
                        appliance_arg.requestFocus();
                        Toast.makeText(AddPartActivity.this, "请输入家电的零件参数", Toast.LENGTH_SHORT).show();
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
                                        ps = conn.prepareStatement("INSERT INTO appliance (appliance_name, appliance_model, appliance_part, appliance_price, appliance_arg) VALUES (?, ?, ?, ?, ?)");
                                        ps.setString(1, applianceName);
                                        ps.setString(2, applianceModel);
                                        ps.setString(3, appliancePart);
                                        ps.setFloat(4, appliancePrice);
                                        ps.setString(5, applianceArg);
                                        int exeNums = ps.executeUpdate();
                                        LogUtil.logV("AddPartActivity", "插入条数：" + exeNums);
                                        if (exeNums == 1) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(AddPartActivity.this, "提交成功！", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                    AddPartActivity.this.finish();
                                                }
                                            });
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(AddPartActivity.this, "抱歉，提交失败！", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(AddPartActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "抱歉，价格格式不正确！\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
