package ifrs.gp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.octicons_typeface_library.Octicons;

import ifrs.gp.R;
import ifrs.gp.util.SharedPreferences;


/**
 * Activity base
 *
 * @author diego.gomes
 * @version 1.0.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Toolbar
     */
    Toolbar toolbar;
    /**
     * Instancia salva
     */
    Bundle savedInstanceState;
    /**
     * Result do menu lateral
     */
    private Drawer result = null;
    /**
     * Header do menu lateral
     */
    private AccountHeader headerResult = null;

    /**
     * Criação da activity
     *
     * @param savedInstanceState Instancia salva
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);

    }

    /**
     * Criação do menu lateral
     *
     * @param context Contexto de criação
     */
    public void criaMenu(Context context){
        criaHeaderMenu();
        criaDrawerMenu(context);
    }

    /**
     * Criação do menu lateral
     *
     * @param context Contexto para criação
     */
    private void criaDrawerMenu(final Context context) {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withCloseOnClick(true)
                .withHasStableIds(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withDelayOnDrawerClose(100)
                .withDisplayBelowStatusBar(false)
                .withSelectedItem(-1)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_inicio).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1).withSetSelected(true),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_usuario).withIcon(GoogleMaterial.Icon.gmd_accounts_add).withIdentifier(2).withSetSelected(true),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_item).withIcon(GoogleMaterial.Icon.gmd_local_cafe).withIdentifier(3).withSetSelected(true),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(4).withSetSelected(true)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(context, MainActivity.class);
                            }
                            else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(context, CadastrarUsuario.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(context, CadastrarItem.class);
                            }
                            else if (drawerItem.getIdentifier() == 4) {
                                new SharedPreferences().removeValor(getApplicationContext(), "usuario");
                                intent = new Intent(context, LoginActivity.class);
                                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            }

                            if (intent != null) {
                                context.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
        String tipo = new SharedPreferences().retornaValor(getApplicationContext(), "tipo_usuario");
        if (tipo!=null && tipo.equals("1")) {
                result.removeItem(2);
                result.removeItem(3);
        }
    }

    /**
     * Criação do header no menu lateral
     */
    private void criaHeaderMenu(){

        String usuario = new SharedPreferences().retornaValor(getApplicationContext(), "usuario");
        String email = new SharedPreferences().retornaValor(getApplicationContext(), "email");
        final IProfile profile = new ProfileDrawerItem().
                withName(usuario).
                withEmail(email).
                withIcon(FontAwesome.Icon.faw_user).
                withIdentifier(100);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.fundo_header_3)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();
    }

    /**
     * Configuração do toolbar
     *
     * @param titulo Titulo do toolbar
     */
    public void setToolbar(String titulo){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(titulo);
        setSupportActionBar(toolbar);
    }

    /**
     * Configuração do toolbar e backbutton
     *
     * @param titulo     Titulo do toolbar
     * @param backButton Setar backbutton
     */
    public void setToolbar(String titulo, boolean backButton){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(titulo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
