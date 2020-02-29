package com.lzf.applianceafter_salesservicesystem.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.lzf.applianceafter_salesservicesystem.R;
import com.lzf.applianceafter_salesservicesystem.fragment.MaintenanceMaintainerFragment;
import com.lzf.applianceafter_salesservicesystem.fragment.PartFragment;
import com.lzf.applianceafter_salesservicesystem.fragment.WorkFieldFragment;
import com.lzf.applianceafter_salesservicesystem.util.LogUtil;
import com.lzf.applianceafter_salesservicesystem.util.SPUtil;

import java.io.Serializable;

public class MaintainerHomeActivity extends AppCompatActivity implements PartFragment.OnFragmentInteractionListener, MaintenanceMaintainerFragment.OnFragmentInteractionListener, WorkFieldFragment.OnFragmentInteractionListener {


    private long exitTime = 0; //保存点击的时间
    //UI Object
    private Button add_maintainer;
    private TextView maintenance_request;
    private TextView work_field;
    private TextView part;
    private FrameLayout ly_content;

    //Fragment Object
    private FragmentManager fManager;
    private PartFragment partFragment;
    private MaintenanceMaintainerFragment maintenanceMaintainerFragment;
    private WorkFieldFragment workFieldFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainer_home);

        add_maintainer = (Button) findViewById(R.id.add_maintainer);
        maintenance_request = (TextView) findViewById(R.id.maintenance_request);
        work_field = (TextView) findViewById(R.id.work_field);
        part = (TextView) findViewById(R.id.part);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);
        if ("18334706003".equals(SPUtil.get(this, "maintainer_phone", "")) || "超级维修员".equals(SPUtil.get(this, "maintainer_name", ""))) {
            add_maintainer.setVisibility(View.VISIBLE);
        } else {
            add_maintainer.setVisibility(View.GONE);
        }
        fManager = getSupportFragmentManager();

        maintenance_request.performClick();   //模拟一次点击，既进去后选择第一项

    }

    /**
     * 重置所有文本的选中状态
     */
    private void setSelected() {
        maintenance_request.setSelected(false);
        work_field.setSelected(false);
        part.setSelected(false);
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (partFragment != null) fragmentTransaction.hide(partFragment);
        if (maintenanceMaintainerFragment != null) fragmentTransaction.hide(maintenanceMaintainerFragment);
        if (workFieldFragment != null) fragmentTransaction.hide(workFieldFragment);
    }

    public void doClick(View view) {
        if (view.getId() == R.id.add_maintainer) {
            startActivity(new Intent(this, AddMaintainerActivity.class));
        } else {
            FragmentTransaction fTransaction = fManager.beginTransaction();
            hideAllFragment(fTransaction);
            switch (view.getId()) {
                case R.id.part:
                    setSelected();
                    part.setSelected(true);
                    if (partFragment == null) {
                        partFragment = PartFragment.newInstance(null);
                        fTransaction.add(R.id.ly_content, partFragment);
                    } else {
                        partFragment.onResume();
                        fTransaction.show(partFragment);
                    }
                    break;
                case R.id.work_field:
                    setSelected();
                    work_field.setSelected(true);
                    if (workFieldFragment == null) {
                        workFieldFragment = WorkFieldFragment.newInstance("工作栏", "工作栏");
                        fTransaction.add(R.id.ly_content, workFieldFragment);
                    } else {
                        workFieldFragment.onResume();
                        fTransaction.show(workFieldFragment);
                    }
                    break;
                case R.id.maintenance_request:
                    setSelected();
                    maintenance_request.setSelected(true);
                    if (maintenanceMaintainerFragment == null) {
                        maintenanceMaintainerFragment = MaintenanceMaintainerFragment.newInstance("维修申请", "维修申请");
                        fTransaction.add(R.id.ly_content, maintenanceMaintainerFragment);
                    } else {
                        maintenanceMaintainerFragment.onResume();
                        fTransaction.show(maintenanceMaintainerFragment);
                    }
                    break;
            }
            fTransaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Class activityClass, Serializable serializable) {
        LogUtil.logV("MaintainerHomeActivity", activityClass + "");
        if (activityClass != null) {
            if ("MainActivity".equals(activityClass.getSimpleName())) {
                SPUtil.clear(this);
                startActivity(new Intent(this, activityClass));
                this.finish();
            } else if ("AddPartActivity".equals(activityClass.getSimpleName())) {
                startActivity(new Intent(this, activityClass));
            } else if ("MaintainerDetailActivity".equals(activityClass.getSimpleName())) {
                Intent intent = new Intent(this, activityClass);
                intent.putExtra("maintenance", serializable);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        LogUtil.logV("MaintainerHomeActivity", hasCapture + "");
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
