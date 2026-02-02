package jwtc.android.chess;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.card.MaterialCardView;

import jwtc.android.chess.R;
import jwtc.android.chess.activities.BoardPreferencesActivity;
import jwtc.android.chess.activities.StartBaseActivity;
import jwtc.android.chess.helpers.ActivityHelper;
import jwtc.android.chess.hotspotboard.HotspotBoardActivity;
import jwtc.android.chess.ics.ICSClient;
import jwtc.android.chess.lichess.LichessActivity;
import jwtc.android.chess.play.PlayActivity;
import jwtc.android.chess.practice.PracticeActivity;
import jwtc.android.chess.puzzle.PuzzleActivity;
import jwtc.android.chess.tools.AdvancedActivity;

public class startModern extends StartBaseActivity {

    private static final String TAG = "StartModernActivity";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Set the modern layout before calling super
        layoutResource = R.layout.start_foss_modern;
        
        super.onCreate(savedInstanceState);
        
        setupCardAnimations();
        setupCardClickListeners();
    }
    
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        
        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                try {
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    actionBar.setSubtitle(pInfo.versionName);
                } catch (PackageManager.NameNotFoundException e) {
                    // Handle exception
                }
            }
        }
    }
    
    private void setupCardAnimations() {
        // Find all cards
        MaterialCardView playCard = findViewById(R.id.card_play);
        MaterialCardView lichessCard = findViewById(R.id.card_lichess);
        MaterialCardView practiceCard = findViewById(R.id.card_practice);
        MaterialCardView puzzlesCard = findViewById(R.id.card_puzzles);
        MaterialCardView settingsCard = findViewById(R.id.board_preferences);
        MaterialCardView toolsCard = findViewById(R.id.card_tools);
        
        // Animate cards with staggered delay
        if (playCard != null) {
            animateCardWithDelay(playCard, 100);
        }
        if (lichessCard != null) {
            animateCardWithDelay(lichessCard, 200);
        }
        if (practiceCard != null) {
            animateCardWithDelay(practiceCard, 300);
        }
        if (puzzlesCard != null) {
            animateCardWithDelay(puzzlesCard, 400);
        }
        if (settingsCard != null) {
            animateCardWithDelay(settingsCard, 500);
        }
        if (toolsCard != null) {
            animateCardWithDelay(toolsCard, 600);
        }
    }
    
    private void animateCardWithDelay(MaterialCardView card, long delay) {
        if (card != null) {
            card.setAlpha(0f);
            card.setScaleX(0.8f);
            card.setScaleY(0.8f);
            
            card.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setStartDelay(delay)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        }
    }
    
    private void setupCardClickListeners() {
        // Play Card
        View playCard = findViewById(R.id.card_play);
        if (playCard != null) {
            playCard.setOnClickListener(v -> {
                animateCardClick(v);
                startActivity(new Intent(this, PlayActivity.class));
            });
        }
        
        // Lichess Card
        View lichessCard = findViewById(R.id.card_lichess);
        if (lichessCard != null) {
            lichessCard.setOnClickListener(v -> {
                animateCardClick(v);
                startActivity(new Intent(this, LichessActivity.class));
            });
        }
        
        // Practice Card
        View practiceCard = findViewById(R.id.card_practice);
        if (practiceCard != null) {
            practiceCard.setOnClickListener(v -> {
                animateCardClick(v);
                startActivity(new Intent(this, PracticeActivity.class));
            });
        }
        
        // Puzzles Card
        View puzzlesCard = findViewById(R.id.card_puzzles);
        if (puzzlesCard != null) {
            puzzlesCard.setOnClickListener(v -> {
                animateCardClick(v);
                startActivity(new Intent(this, PuzzleActivity.class));
            });
        }
        
        // Settings Card
        View settingsCard = findViewById(R.id.board_preferences);
        if (settingsCard != null) {
            settingsCard.setOnClickListener(v -> {
                animateCardClick(v);
                startActivity(new Intent(this, BoardPreferencesActivity.class));
            });
        }
        
        // Tools Card
        View toolsCard = findViewById(R.id.card_tools);
        if (toolsCard != null) {
            toolsCard.setOnClickListener(v -> {
                animateCardClick(v);
                showToolsDialog();
            });
        }
    }
    
    private void animateCardClick(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f, 1f);
        
        scaleX.setDuration(150);
        scaleY.setDuration(150);
        
        scaleX.start();
        scaleY.start();
    }
    
    private void showToolsDialog() {
        // Create a simple dialog for tools options
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Tools");
        String[] tools = {"ICS (FICS)", "Hotspot Board", "PGN Tools"};
        
        builder.setItems(tools, (dialog, which) -> {
            Intent i = new Intent();
            switch (which) {
                case 0: // ICS
                    i.setClass(this, ICSClient.class);
                    startActivity(i);
                    break;
                case 1: // Hotspot Board
                    i.setClass(this, HotspotBoardActivity.class);
                    startActivity(i);
                    break;
                case 2: // PGN Tools
                    i.setClass(this, AdvancedActivity.class);
                    startActivity(i);
                    break;
            }
        });
        
        builder.show();
    }
}