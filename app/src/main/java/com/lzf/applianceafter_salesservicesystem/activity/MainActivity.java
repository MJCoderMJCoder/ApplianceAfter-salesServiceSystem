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


public class MainActivity extends AppCompatActivity {

    private EditText phone;
    private EditText pwd;
    private long exitTime = 0; //保存点击的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogUtil.logV("MainActivity", SPUtil.getAll(this).toString());
        if (null != SPUtil.get(this, "maintainer_phone", "") && !"".equals(SPUtil.get(this, "maintainer_phone", ""))) {
            startActivity(new Intent(this, MaintainerHomeActivity.class));
            this.finish();
        } else if (null != SPUtil.get(this, "user_phone", "") && !"".equals(SPUtil.get(this, "user_phone", ""))) {
            startActivity(new Intent(this, UserHomeActivity.class));
            this.finish();
        } else {
            phone = findViewById(R.id.phone);
            pwd = findViewById(R.id.pwd);
        }
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
                    Toast.makeText(MainActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (pwdStr == null || "".equals(pwdStr)) {
                    pwd.setFocusable(true);
                    pwd.setFocusableInTouchMode(true);
                    pwd.requestFocus();
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
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
                                    ps = conn.prepareStatement("SELECT * FROM user WHERE user_phone = ?");
                                    ps.setString(1, phoneStr);
                                    rs = ps.executeQuery();
                                    LogUtil.logV("MainActivity", rs.getRow() + "");
                                    rs.last();
                                    LogUtil.logV("MainActivity", rs.getRow() + "");
                                    if (rs.getRow() != 1) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                phone.setFocusable(true);
                                                phone.setFocusableInTouchMode(true);
                                                phone.requestFocus();
                                                Toast.makeText(MainActivity.this, "该账号不存在！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        rs.first();
                                        LogUtil.logV("MainActivity", rs.getRow() + "");
                                        do {
                                            if (pwdStr.equals(rs.getString("user_pwd"))) {
                                                SPUtil.put(MainActivity.this, "user_id", rs.getInt("user_id"));
                                                SPUtil.put(MainActivity.this, "user_name", rs.getString("user_name"));
                                                SPUtil.put(MainActivity.this, "user_phone", rs.getString("user_phone"));
                                                SPUtil.put(MainActivity.this, "user_gender", rs.getString("user_gender"));
                                                SPUtil.put(MainActivity.this, "user_address", rs.getString("user_address"));
                                                SPUtil.put(MainActivity.this, "user_pwd", rs.getString("user_pwd"));
                                                LogUtil.logV("MainActivity", SPUtil.getAll(MainActivity.this).toString());
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        startActivity(new Intent(MainActivity.this, UserHomeActivity.class));
                                                        MainActivity.this.finish();
                                                    }
                                                });
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        pwd.setFocusable(true);
                                                        pwd.setFocusableInTouchMode(true);
                                                        pwd.requestFocus();
                                                        Toast.makeText(MainActivity.this, "密码不正确！", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        } while (rs.next());
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.toMaintarner:
                startActivity(new Intent(this, MaintainerActivity.class));
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
            System.exit(0); //正常退出
        }
    }
}
