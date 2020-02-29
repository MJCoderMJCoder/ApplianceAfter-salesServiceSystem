package com.lzf.applianceafter_salesservicesystem.activity;

import android.content.Intent;
import android.telecom.Call;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.lzf.applianceafter_salesservicesystem.R;
import com.lzf.applianceafter_salesservicesystem.bean.Maintenance;
import com.lzf.applianceafter_salesservicesystem.bean.Message;
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

public class UserDetailActivity extends AppCompatActivity {
    private Maintenance maintenance = null;
    private ReusableAdapter<Message> reusableAdapter;
    private List<Message> messages = new ArrayList<Message>();
    private ProgressBar progressBar;
    private EditText editText;
    private int reply_message = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        if (intent != null) {
            maintenance = (Maintenance) intent.getSerializableExtra("maintenance");
            if (maintenance != null) {
                ((TextView) findViewById(R.id.maintenance_name)).setText("维修简述：" + maintenance.getMaintenance_name());
                ((TextView) findViewById(R.id.maintenance_address)).setText(maintenance.getMaintenance_address());
                ((TextView) findViewById(R.id.maintenance_appliance)).setText(maintenance.getMaintenance_appliance());
                TextView maintenance_status = (TextView) findViewById(R.id.maintenance_status);
                progressBar = findViewById(R.id.progressBar);
                editText = findViewById(R.id.editText);
                switch (maintenance.getMaintenance_status()) {
                    case 0:
                        maintenance_status.setText("当前状态：可接取");
                        break;
                    case 1:
                        maintenance_status.setText("当前状态：未完成");
                        break;
                    case 3:
                        maintenance_status.setText("当前状态：已取消");
                        break;
                    case 4:
                        maintenance_status.setText("当前状态：已完成");
                        break;
                    default:
                        break;
                }
                ListView listView = findViewById(R.id.msg_list);
                reusableAdapter = new ReusableAdapter<Message>(messages, R.layout.item_msg) {
                    @Override
                    public void bindView(ViewHolder holder, Message obj) {
                        holder.setText(R.id.name, obj.getUser_name() + obj.getMaintainer_name() + "：");
                        holder.setText(R.id.msg, obj.getMessage_content());
                    }
                };
                listView.setAdapter(reusableAdapter);
            } else {
                Toast.makeText(UserDetailActivity.this, "请先选择一项在进行修改", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } else {
            Toast.makeText(UserDetailActivity.this, "请先选择一项在进行修改", Toast.LENGTH_SHORT).show();
            this.finish();
        }

    }

    @Override
    protected void onResume() {
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
                    conn = DBUtil.getConnection();
                    if (conn != null) {
                        messages.clear();
                        ps = conn.prepareStatement("SELECT * FROM message where maintenance = " + maintenance.getMaintenance_id());
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            reply_message = Math.max(reply_message, rs.getInt("message_id"));
                            messages.add(new Message(rs.getInt("message_id"), rs.getInt("maintenance"), rs.getInt("user"), rs.getString("user_name"), rs.getInt("maintainer"), rs.getString("maintainer_name"), rs.getInt("reply_message"), rs.getString("message_content")));
                        }
                        LogUtil.logV("UserDetailActivity", messages.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                reusableAdapter.updateAll(messages);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UserDetailActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.publishBtn:
                progressBar.setVisibility(View.VISIBLE);
                final String msg = editText.getText().toString().trim();
                if (msg == null || "".equals(msg)) {
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    Toast.makeText(UserDetailActivity.this, "留言内容不能为空", Toast.LENGTH_SHORT).show();
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
                                    ps = conn.prepareStatement("INSERT INTO message (maintenance, user, user_name, maintainer, maintainer_name, reply_message, message_content) VALUES (?, ?, ?, ?, ?, ?, ?)");
                                    ps.setInt(1, maintenance.getMaintenance_id());
                                    ps.setInt(2, (Integer) SPUtil.get(UserDetailActivity.this, "user_id", 0));
                                    ps.setString(3, (String) SPUtil.get(UserDetailActivity.this, "user_name", ""));
                                    ps.setInt(4, -1);
                                    ps.setString(5, "");
                                    ps.setInt(6, reply_message);
                                    ps.setString(7, msg);
                                    int exeNums = ps.executeUpdate();
                                    LogUtil.logV("AddMaintenanceActivity", "插入条数：" + exeNums);
                                    if (exeNums == 1) {
                                        messages.clear();
                                        ps = conn.prepareStatement("SELECT * FROM message where maintenance = " + maintenance.getMaintenance_id());
                                        rs = ps.executeQuery();
                                        while (rs.next()) {
                                            reply_message = Math.max(reply_message, rs.getInt("message_id"));
                                            messages.add(new Message(rs.getInt("message_id"), rs.getInt("maintenance"), rs.getInt("user"), rs.getString("user_name"), rs.getInt("maintainer"), rs.getString("maintainer_name"), rs.getInt("reply_message"), rs.getString("message_content")));
                                        }
                                        LogUtil.logV("UserDetailActivity", messages.toString());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressBar.setVisibility(View.GONE);
                                                reusableAdapter.updateAll(messages);
                                                Toast.makeText(UserDetailActivity.this, "发表成功！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(UserDetailActivity.this, "抱歉，发表失败！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UserDetailActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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

