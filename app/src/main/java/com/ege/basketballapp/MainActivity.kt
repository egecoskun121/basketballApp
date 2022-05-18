package com.ege.basketballapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ege.basketballapp.databinding.ActivityMainBinding
import com.ege.basketballapp.ui.bottomsheet.ChooseLanguageBS
import com.ege.basketballapp.ui.home.HomeFragment
import com.ege.basketballapp.ui.profile.ProfileFragment
import com.ege.basketballapp.ui.team.TeamFragment
import com.ege.basketballapp.utils.GlobalPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    lateinit var chooseLanguageBS: ChooseLanguageBS
    private var mProgressDialog: Dialog? = null

    lateinit var imageView: ImageView
    lateinit var anim: RotateAnimation

    @Inject
    lateinit var globalPreferences: GlobalPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.myTheme_BasketBallApp)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navSide.setItemIconTintList(null)
        setLocale(this, globalPreferences.locale)
        binding.navSide.setNavigationItemSelectedListener(this)
        checkIfUserexists()
        updateFont()

        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)
    }

    private fun checkIfUserexists() {
        if (globalPreferences.user == null) {
            navController.navigate(R.id.navigation_profile)
            binding.navView.visibility = View.GONE
        } else {
            updateSideMenu()
            navController.navigate(R.id.navigation_home)
        }

    }

    fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_league -> navController.navigate(R.id.allTeamsFragment)
            R.id.change_lang -> chooseLanguage()
        }
        binding.navdrawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun chooseLanguage() {
        chooseLanguageBS = ChooseLanguageBS()
        chooseLanguageBS.show(supportFragmentManager, "TAGG")
    }


    override fun onBackPressed() {
        super.onBackPressed()

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        val fragment2 = navHost!!.childFragmentManager.primaryNavigationFragment

        if (binding.navdrawer.isDrawerOpen(GravityCompat.START)) {
            binding.navdrawer.closeDrawer(GravityCompat.START)
        } else if (fragment2 is HomeFragment || fragment2 is ProfileFragment
            || fragment2 is TeamFragment
        ) {
            finishAffinity()
            finish()
        }
    }


    fun openNavigationDrawer() {
        if (!binding.navdrawer.isDrawerOpen(GravityCompat.START)) {
            binding.navdrawer.openDrawer(GravityCompat.START)
        }


    }


    fun updateFont() {
        if (globalPreferences.locale.length > 1) {
            val locale2 = Locale(globalPreferences.locale)
            Locale.setDefault(locale2)
            val config2 = Configuration()
            config2.locale = locale2
            baseContext.resources.updateConfiguration(
                config2, baseContext.resources.displayMetrics
            )
        } else {
            val locale2 = Locale("en")
            Locale.setDefault(locale2)
            val config2 = Configuration()
            config2.locale = locale2
            baseContext.resources.updateConfiguration(
                config2, baseContext.resources.displayMetrics
            )
        }

    }

    fun updateSideMenu() {

        val headerView: View = binding.navSide.getHeaderView(0)
        var navUser: TextView = headerView.findViewById(R.id.theprofileName)
        navUser.setText(globalPreferences.user.name)
    }




    fun recreateActivity() {
        if (Build.VERSION.SDK_INT >= 11) {
            recreate()
        } else {
            val intent = intent
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    fun setLocale(context: Context, lang: String?) {
        val res = context.resources

        val dm = res.displayMetrics
        val conf = res.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(Locale(lang)) // API 17+ only.
        }

        res.updateConfiguration(conf, dm)
    }

}