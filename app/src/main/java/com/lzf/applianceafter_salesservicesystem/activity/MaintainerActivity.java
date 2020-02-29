package com.lzf.applianceafter_salesservicesystem.activity;

import android.content.Intent;
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

public class MaintainerActivity extends AppCompatActivity {

    private EditText phone;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainer);
        phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.pwd);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                final String phoneStr = phone.getText().toString().trim();
                final String pwdStr = pwd.getText().toString().trim();
                if (phoneStr == null || "".equals(phoneStr)) {
                    phone.setFocusable(true);
                    phone.setFocusableInTouchMode(true);
                    phone.requestFocus();
                    Toast.makeText(MaintainerActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (pwdStr == null || "".equals(pwdStr)) {
                    pwd.setFocusable(true);
                    pwd.setFocusableInTouchMode(true);
                    pwd.requestFocus();
                    Toast.makeText(MaintainerActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
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
                                    ps = conn.prepareStatement("SELECT * FROM maintainer WHERE maintainer_phone = ?");
                                    ps.setString(1, phoneStr);
                                    rs = ps.executeQuery();
                                    LogUtil.logV("MaintainerActivity", rs.getRow() + "");
                                    rs.last();
                                    LogUtil.logV("MaintainerActivity", rs.getRow() + "");
                                    if (rs.getRow() != 1) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                phone.setFocusable(true);
                                                phone.setFocusableInTouchMode(true);
                                                phone.requestFocus();
                                                Toast.makeText(MaintainerActivity.this, "该账号不存在！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        rs.first();
                                        LogUtil.logV("MaintainerActivity", rs.getRow() + "");
                                        do {
                                            if (pwdStr.equals(rs.getString("maintainer_pwd"))) {
                                                SPUtil.put(MaintainerActivity.this, "maintainer_id", rs.getInt("maintainer_id"));
                                                SPUtil.put(MaintainerActivity.this, "maintainer_name", rs.getString("maintainer_name"));
                                                SPUtil.put(MaintainerActivity.this, "maintainer_phone", rs.getString("maintainer_phone"));
                                                SPUtil.put(MaintainerActivity.this, "maintainer_gender", rs.getString("maintainer_gender"));
                                                SPUtil.put(MaintainerActivity.this, "maintainer_address", rs.getString("maintainer_address"));
                                                SPUtil.put(MaintainerActivity.this, "maintainer_pwd", rs.getString("maintainer_pwd"));
                                                LogUtil.logV("MaintainerActivity", SPUtil.getAll(MaintainerActivity.this).toString());
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        startActivity(new Intent(MaintainerActivity.this, MaintainerHomeActivity.class));
                                                        MaintainerActivity.this.finish();
                                                    }
                                                });
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        pwd.setFocusable(true);
                                                        pwd.setFocusableInTouchMode(true);
                                                        pwd.requestFocus();
                                                        Toast.makeText(MaintainerActivity.this, "密码不正确！", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        } while (rs.next());
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MaintainerActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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
            case R.id.toUser:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
