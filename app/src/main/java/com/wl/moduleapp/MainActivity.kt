package com.wl.moduleapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wl.modulea.ModuleAFragment
import com.wl.moduleb.ModuleBFragment
import com.wl.modulec.ModuleCFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    //定义碎片集合
    private val fragments: Array<Fragment?> = arrayOfNulls<Fragment>(3)

    //当前显示的fragment的索引位置
    private var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
        navigation.setOnNavigationItemSelectedListener(this);


    }


    /**
     * 初始化Fragment碎片
     */
    fun  initFragment() {
        if (fragments[0] == null) {
            fragments[0] =  ModuleAFragment()
            getSupportFragmentManager().beginTransaction().add(
                R.id.content,
                fragments[0]!!,
                "moduleA"
            ).commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(fragments[0]!!);
        }
    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.navigation_a -> {
                if (currentIndex == 0) return true;//如果已经是当前的fragment，不用切换
                var transition0 = getSupportFragmentManager().beginTransaction();
                hideAndShow(0, transition0);
                return true;
            }


            R.id.navigation_b -> {
                if (currentIndex == 1) return true;//如果已经是当前的fragment，不用切换
                var transition1 = getSupportFragmentManager().beginTransaction();
                if (fragments[1] == null) {
                    fragments[1] = ModuleBFragment()
                    transition1.add(R.id.content, fragments[1]!!, "moduleB");
                }
                hideAndShow(1, transition1);
                return true;
            }

            R.id.navigation_c -> {
                if (currentIndex == 2) return true;//如果已经是当前的fragment，不用切换
                var transition2 = getSupportFragmentManager().beginTransaction();
                if (fragments[2] == null) {
                    fragments[2] = ModuleCFragment()
                    transition2.add(R.id.content, fragments[2]!!, "modulec");
                }
                hideAndShow(2, transition2);
                return true;
            }
        }
        return false
    }

    /**
     * 除了指定的fragment不hide，其他fragment全hide
     * @param expectIndex 指定的fragment在fragments中的位置
     * @param transition
     */
    private fun hideAndShow(expectIndex: Int, transition: FragmentTransaction) {
        for (i in 0 until fragments.size) {
            if (i != expectIndex && fragments[i] != null) {
                transition.hide(fragments[i]!!)
            }
        }
        transition.show(fragments[expectIndex]!!)
        transition.commit()
        currentIndex = expectIndex
    }


}