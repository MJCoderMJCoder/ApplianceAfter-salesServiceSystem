package com.lzf.applianceafter_salesservicesystem.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.lzf.applianceafter_salesservicesystem.R;
import com.lzf.applianceafter_salesservicesystem.bean.Maintainer;
import com.lzf.applianceafter_salesservicesystem.bean.Maintenance;
import com.lzf.applianceafter_salesservicesystem.bean.Message;
import com.lzf.applianceafter_salesservicesystem.bean.User;
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

public class MaintainerDetailActivity extends AppCompatActivity {
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
                    public void bindView(ViewHolder holder, final Message obj) {
                        holder.setText(R.id.name, obj.getUser_name() + obj.getMaintainer_name() + "：");
                        holder.setText(R.id.msg, obj.getMessage_content());
                        holder.setOnClickListener(R.id.name, new View.OnClickListener() {
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
                                            conn = DBUtil.getConnection();
                                            if (conn != null) {
                                                if (obj.getMaintainer() < 0 || "".equals(obj.getMaintainer_name())) {
                                                    ps = conn.prepareStatement("SELECT * FROM user where user_id = " + obj.getUser());
                                                    rs = ps.executeQuery();
                                                    while (rs.next()) {
                                                        final User user = new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_phone"), rs.getString("user_gender"), rs.getString("user_address"), rs.getString("user_pwd"));
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                progressBar.setVisibility(View.GONE);
                                                                //初始化Builder
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(MaintainerDetailActivity.this);
                                                                //加载自定义的那个View,同时设置下
                                                                final LayoutInflater inflater = MaintainerDetailActivity.this.getLayoutInflater();
                                                                View view_custom = inflater.inflate(R.layout.alert_info, null, false);
                                                                ((TextView) view_custom.findViewById(R.id.name)).setText("姓名：" + user.getUser_name());
                                                                ((TextView) view_custom.findViewById(R.id.phone)).setText("手机：" + user.getUser_phone());
                                                                ((TextView) view_custom.findViewById(R.id.gender)).setText("性别：" + user.getUser_gender());
                                                                ((TextView) view_custom.findViewById(R.id.address)).setText(user.getUser_address());
                                                                builder.setView(view_custom);
                                                                builder.setCancelable(true);
                                                                AlertDialog alert = builder.create();
                                                                alert.show();
                                                            }
                                                        });
                                                    }
                                                } else if (obj.getUser() < 0 || "".equals(obj.getUser_name())) {
                                                    ps = conn.prepareStatement("SELECT * FROM maintainer where maintainer_id = " + obj.getMaintainer());
                                                    rs = ps.executeQuery();
                                                    while (rs.next()) {
                                                        final Maintainer maintainer = new Maintainer(rs.getInt("maintainer_id"), rs.getString("maintainer_name"), rs.getString("maintainer_phone"), rs.getString("maintainer_gender"), rs.getString("maintainer_address"), rs.getString("maintainer_pwd"));
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                progressBar.setVisibility(View.GONE);
                                                                //初始化Builder
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(MaintainerDetailActivity.this);
                                                                //加载自定义的那个View,同时设置下
                                                                final LayoutInflater inflater = MaintainerDetailActivity.this.getLayoutInflater();
                                                                View view_custom = inflater.inflate(R.layout.alert_info, null, false);
                                                                ((TextView) view_custom.findViewById(R.id.name)).setText("姓名：" + maintainer.getMaintainer_name());
                                                                ((TextView) view_custom.findViewById(R.id.phone)).setText("手机：" + maintainer.getMaintainer_phone());
                                                                ((TextView) view_custom.findViewById(R.id.gender)).setText("性别：" + maintainer.getMaintainer_gender());
                                                                ((TextView) view_custom.findViewById(R.id.address)).setText(maintainer.getMaintainer_address());
                                                                builder.setView(view_custom);
                                                                builder.setCancelable(true);
                                                                AlertDialog alert = builder.create();
                                                                alert.show();
                                                            }
                                                        });
                                                    }
                                                }
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(MaintainerDetailActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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
                    }
                };
                listView.setAdapter(reusableAdapter);
            } else {
                Toast.makeText(MaintainerDetailActivity.this, "请先选择一项在进行修改", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        } else {
            Toast.makeText(MaintainerDetailActivity.this, "请先选择一项在进行修改", Toast.LENGTH_SHORT).show();
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
                        LogUtil.logV("MaintainerDetailActivity", messages.toString());
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
                                Toast.makeText(MaintainerDetailActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MaintainerDetailActivity.this, "留言内容不能为空", Toast.LENGTH_SHORT).show();
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
                                    ps.setInt(2, -1);
                                    ps.setString(3, "");
                                    ps.setInt(4, (Integer) SPUtil.get(MaintainerDetailActivity.this, "maintainer_id", 0));
                                    ps.setString(5, (String) SPUtil.get(MaintainerDetailActivity.this, "maintainer_name", ""));
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
                                        LogUtil.logV("MaintainerDetailActivity", messages.toString());
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressBar.setVisibility(View.GONE);
                                                reusableAdapter.updateAll(messages);
                                                Toast.makeText(MaintainerDetailActivity.this, "发表成功！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MaintainerDetailActivity.this, "抱歉，发表失败！", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MaintainerDetailActivity.this, "抱歉，数据库连接失败；请确认网络是否畅通", Toast.LENGTH_LONG).show();
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

