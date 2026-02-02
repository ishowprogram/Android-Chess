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
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    
    // Currency values
    private int gems = 1250;
    private int coins = 5000;
    private int playerLevel = 1;
    
    // UI Elements
    private TextView gemsText;
    private TextView coinsText;
    private TextView levelBadge;
    private TextView avatarText;
    private View chessLogo;
    
    // Animation handler for coin rotation
    private Handler animationHandler = new Handler();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Set the modern layout before calling super
        layoutResource = R.layout.start_foss_modern;
        
        super.onCreate(savedInstanceState);
        
        loadPlayerData();
        setupUI();
        setupAnimations();
        setupClickListeners();
        startCurrencyAnimations();
    }
    
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }
    
    private void loadPlayerData() {
        SharedPreferences prefs = getSharedPreferences("ChessGame", Context.MODE_PRIVATE);
        gems = prefs.getInt("gems", 1250);
        coins = prefs.getInt("coins", 5000);
        playerLevel = prefs.getInt("level", 1);
    }
    
    private void savePlayerData() {
        SharedPreferences prefs = getSharedPreferences("ChessGame", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("gems", gems);
        editor.putInt("coins", coins);
        editor.putInt("level", playerLevel);
        editor.apply();
    }
    
    private void setupUI() {
        // Currency displays
        gemsText = findViewById(R.id.gems_text);
        coinsText = findViewById(R.id.coins_text);
        levelBadge = findViewById(R.id.level_badge);
        avatarText = findViewById(R.id.avatar_text);
        chessLogo = findViewById(R.id.chess_logo);
        
        // Update UI with loaded data
        updateCurrencyDisplay();
        
        // Set avatar initial (you could customize this based on player name)
        avatarText.setText("M");
        levelBadge.setText(String.valueOf(playerLevel));
    }
    
    private void updateCurrencyDisplay() {
        if (gemsText != null) {
            gemsText.setText(String.format("%,d", gems));
        }
        if (coinsText != null) {
            coinsText.setText(String.format("%,d", coins));
        }
    }
    
    private void setupAnimations() {
        // Animate chess logo with float effect
        if (chessLogo != null) {
            chessLogo.setAlpha(0f);
            chessLogo.setScaleX(0.5f);
            chessLogo.setScaleY(0.5f);
            
            chessLogo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(800)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
                
            // Continuous floating animation
            startLogoFloatAnimation();
        }
        
        // Animate cards with staggered delay
        animateViewWithDelay(findViewById(R.id.card_classic_chess), 400);
        animateViewWithDelay(findViewById(R.id.card_quick_chess), 600);
        animateViewWithDelay(findViewById(R.id.card_more_features), 800);
        
        // Animate bottom navigation
        animateViewWithDelay(findViewById(R.id.bottom_navigation), 1000);
    }
    
    private void startLogoFloatAnimation() {
        if (chessLogo != null) {
            chessLogo.animate()
                .translationY(-10f)
                .setDuration(2000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(() -> {
                    chessLogo.animate()
                        .translationY(0f)
                        .setDuration(2000)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .withEndAction(this::startLogoFloatAnimation)
                        .start();
                })
                .start();
        }
    }
    
    private void animateViewWithDelay(View view, long delay) {
        if (view != null) {
            view.setAlpha(0f);
            view.setScaleX(0.8f);
            view.setScaleY(0.8f);
            
            view.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        }
    }
    
    private void setupClickListeners() {
        // Classic Chess button
        View classicChessBtn = findViewById(R.id.btn_classic_chess);
        View classicChessCard = findViewById(R.id.card_classic_chess);
        View.OnClickListener classicListener = v -> {
            animateButtonClick(v);
            startActivity(new Intent(this, PlayActivity.class));
        };
        
        if (classicChessBtn != null) classicChessBtn.setOnClickListener(classicListener);
        if (classicChessCard != null) classicChessCard.setOnClickListener(classicListener);
        
        // Quick Chess button (could be mapped to rapid chess mode)
        View quickChessBtn = findViewById(R.id.btn_quick_chess);
        View quickChessCard = findViewById(R.id.card_quick_chess);
        View.OnClickListener quickListener = v -> {
            animateButtonClick(v);
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("quick_mode", true);
            startActivity(intent);
        };
        
        if (quickChessBtn != null) quickChessBtn.setOnClickListener(quickListener);
        if (quickChessCard != null) quickChessCard.setOnClickListener(quickListener);
        
        // More Features button
        View moreFeaturesBtn = findViewById(R.id.btn_more_features);
        View moreFeaturesCard = findViewById(R.id.card_more_features);
        View.OnClickListener moreFeaturesListener = v -> {
            animateButtonClick(v);
            showMoreFeaturesDialog();
        };
        
        if (moreFeaturesBtn != null) moreFeaturesBtn.setOnClickListener(moreFeaturesListener);
        if (moreFeaturesCard != null) moreFeaturesCard.setOnClickListener(moreFeaturesListener);
        
        // Currency add buttons
        View addGems = findViewById(R.id.add_gems);
        if (addGems != null) {
            addGems.setOnClickListener(v -> {
                animateButtonClick(v);
                showAddCurrencyDialog("gems");
            });
        }
        
        View addCoins = findViewById(R.id.add_coins);
        if (addCoins != null) {
            addCoins.setOnClickListener(v -> {
                animateButtonClick(v);
                showAddCurrencyDialog("coins");
            });
        }
        
        // Bottom navigation
        setupNavigationClickListeners();
    }
    
    private void setupNavigationClickListeners() {
        // Home (already on home screen)
        View navHome = findViewById(R.id.nav_home);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                animateNavClick(v);
                Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show();
            });
        }
        
        // Friends (could link to social features)
        View navFriends = findViewById(R.id.nav_friends);
        if (navFriends != null) {
            navFriends.setOnClickListener(v -> {
                animateNavClick(v);
                Toast.makeText(this, "Friends feature coming soon!", Toast.LENGTH_SHORT).show();
            });
        }
        
        // Equipment/Chess (main play modes)
        View navEquipment = findViewById(R.id.nav_equipment);
        if (navEquipment != null) {
            navEquipment.setOnClickListener(v -> {
                animateNavClick(v);
                showChessModesDialog();
            });
        }
        
        // Events (tournaments, etc.)
        View navEvents = findViewById(R.id.nav_events);
        if (navEvents != null) {
            navEvents.setOnClickListener(v -> {
                animateNavClick(v);
                startActivity(new Intent(this, LichessActivity.class));
            });
        }
        
        // Shop (currency purchases)
        View navShop = findViewById(R.id.nav_shop);
        if (navShop != null) {
            navShop.setOnClickListener(v -> {
                animateNavClick(v);
                Intent intent = new Intent(this, ShopActivity.class);
                startActivity(intent);
            });
        }
    }
    
    private void animateButtonClick(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f, 1f);
        
        scaleX.setDuration(100);
        scaleY.setDuration(100);
        
        scaleX.start();
        scaleY.start();
    }
    
    private void animateNavClick(View view) {
        // Add glow effect and scale animation for navigation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.9f, 1f);
        
        scaleX.setDuration(150);
        scaleY.setDuration(150);
        
        scaleX.start();
        scaleY.start();
    }
    
    private void startCurrencyAnimations() {
        // Animate coin icon rotation (simulate spinning coin)
        animationHandler.postDelayed(() -> {
            View coinBar = findViewById(R.id.coin_bar);
            if (coinBar != null) {
                // You could add a custom spinning animation here
            }
            startCurrencyAnimations(); // Continue animation
        }, 3000);
    }
    
    private void showMoreFeaturesDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("More Features");
        String[] features = {
            "Lichess Online Play", 
            "Practice & Training", 
            "Puzzles", 
            "Board Settings", 
            "Advanced Tools"
        };
        
        builder.setItems(features, (dialog, which) -> {
            Intent intent = new Intent();
            switch (which) {
                case 0: // Lichess
                    intent.setClass(this, LichessActivity.class);
                    break;
                case 1: // Practice
                    intent.setClass(this, PracticeActivity.class);
                    break;
                case 2: // Puzzles
                    intent.setClass(this, PuzzleActivity.class);
                    break;
                case 3: // Settings
                    intent.setClass(this, BoardPreferencesActivity.class);
                    break;
                case 4: // Tools
                    showToolsDialog();
                    return;
            }
            startActivity(intent);
        });
        
        builder.show();
    }
    
    private void showChessModesDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Chess Modes");
        String[] modes = {"Classic Chess", "Quick Chess", "Chess 960", "Duck Chess"};
        
        builder.setItems(modes, (dialog, which) -> {
            Intent intent = new Intent(this, PlayActivity.class);
            switch (which) {
                case 0: // Classic
                    // Default mode
                    break;
                case 1: // Quick
                    intent.putExtra("quick_mode", true);
                    break;
                case 2: // Chess 960
                    intent.putExtra("chess960", true);
                    break;
                case 3: // Duck Chess
                    intent.putExtra("duck_chess", true);
                    break;
            }
            startActivity(intent);
        });
        
        builder.show();
    }
    
    private void showToolsDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Advanced Tools");
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
    
    private void showAddCurrencyDialog(String currencyType) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        
        if ("gems".equals(currencyType)) {
            builder.setTitle("Get More Gems");
            builder.setMessage("Purchase gems to unlock premium features and content!");
            builder.setPositiveButton("Buy Gems", (dialog, which) -> {
                // Add gems logic
                gems += 100;
                updateCurrencyDisplay();
                savePlayerData();
                Toast.makeText(this, "Added 100 gems!", Toast.LENGTH_SHORT).show();
            });
        } else {
            builder.setTitle("Get More Coins");
            builder.setMessage("Purchase coins for in-game items and rewards!");
            builder.setPositiveButton("Buy Coins", (dialog, which) -> {
                // Add coins logic
                coins += 500;
                updateCurrencyDisplay();
                savePlayerData();
                Toast.makeText(this, "Added 500 coins!", Toast.LENGTH_SHORT).show();
            });
        }
        
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    
    private void showShopDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Chess Shop");
        String[] shopItems = {
            "Gem Packs", 
            "Coin Bundles", 
            "Premium Themes", 
            "Special Pieces"
        };
        
        builder.setItems(shopItems, (dialog, which) -> {
            switch (which) {
                case 0: // Gem Packs
                    showAddCurrencyDialog("gems");
                    break;
                case 1: // Coin Bundles
                    showAddCurrencyDialog("coins");
                    break;
                case 2: // Premium Themes
                    Toast.makeText(this, "Themes coming soon!", Toast.LENGTH_SHORT).show();
                    break;
                case 3: // Special Pieces
                    Toast.makeText(this, "Custom pieces coming soon!", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
        
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animationHandler != null) {
            animationHandler.removeCallbacksAndMessages(null);
        }
    }
}