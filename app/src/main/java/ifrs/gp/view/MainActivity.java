package ifrs.gp.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import ifrs.gp.R;
import ifrs.gp.controler.*;
import ifrs.gp.fragment.FragmentBebida;
import ifrs.gp.fragment.FragmentLanches;
import ifrs.gp.util.SharedPreferences;

public class MainActivity extends BaseActivity {
    private PedidoController pedidoCont;
    private ItemController itemCont;
    private ViewPager _viewPager;
    private TabLayout _tabLayout;
    public FragmentBebida f;
    public FragmentLanches f2 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String usuario = new SharedPreferences().retornaValor(getApplicationContext(), "usuario");
        if(usuario==null){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        //new PedidoController(this).testeLogin();
        _tabLayout = (TabLayout) findViewById(R.id.tabs);



        setToolbar("Fastcheck");
        criaMenu(this);
        pedidoCont = new PedidoController(this);
        itemCont = new ItemController(this);

        itemCont.carrega();

        _viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(_viewPager);

        _tabLayout = (TabLayout) findViewById(R.id.tabs);
        _viewPager.setCurrentItem(1);
        _tabLayout.setupWithViewPager(_viewPager);

        FloatingActionButton btnLimpar = (FloatingActionButton) findViewById(R.id.btnLimpar);
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCont.carrega();
            }
        });

        FloatingActionButton btnConfirmar = (FloatingActionButton) findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidoCont.fecha_pedido();
            }
        });


        /*TabHost abas = (TabHost) findViewById(R.id.tabhost);
        abas.setup();
        abas.addTab(abas.newTabSpec("aba1").setIndicator(getString(R.string.txt_bebidas)).setContent(R.id.bebidas));
        abas.addTab(abas.newTabSpec("aba2").setIndicator(getString(R.string.txt_lanches)).setContent(R.id.lanches));


       */
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        f =  new FragmentBebida();
        f2 = new FragmentLanches();
        adapter.addFrag(f2, getString(R.string.fragment_lanches));
        adapter.addFrag(f, getString(R.string.fragment_bebida));
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        /**
         * Instantiates a new View pager adapter.
         *
         * @param manager the manager
         */
        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /**
         * Add frag.
         *
         * @param fragment the fragment
         * @param title    the title
         */
        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
