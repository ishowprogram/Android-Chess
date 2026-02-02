package jwtc.android.chess;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import jwtc.android.chess.lichess.LichessActivity;
import jwtc.android.chess.play.PlayActivity;
import jwtc.android.chess.startModern;

public class ShopActivity extends AppCompatActivity {

    private static final String TAG = "ShopActivity";
    
    // Currency values
    private int gems = 1250;
    private int coins = 5000;
    
    // UI Elements
    private TextView gemsText;
    private TextView coinsText;
    
    // Theme purchases
    private boolean ownsClassicTheme = true;
    private boolean ownsDarkTheme = false;
    private boolean ownsNeonTheme = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);
        
        loadPlayerData();
        setupUI();
        setupClickListeners();
        startAnimations();
    }
    
    private void loadPlayerData() {
        SharedPreferences prefs = getSharedPreferences("ChessGame", Context.MODE_PRIVATE);
        gems = prefs.getInt("gems", 1250);
        coins = prefs.getInt("coins", 5000);
        ownsClassicTheme = prefs.getBoolean("ownsClassicTheme", true);
        ownsDarkTheme = prefs.getBoolean("ownsDarkTheme", false);
        ownsNeonTheme = prefs.getBoolean("ownsNeonTheme", false);
    }
    
    private void savePlayerData() {
        SharedPreferences prefs = getSharedPreferences("ChessGame", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("gems", gems);
        editor.putInt("coins", coins);
        editor.putBoolean("ownsClassicTheme", ownsClassicTheme);
        editor.putBoolean("ownsDarkTheme", ownsDarkTheme);
        editor.putBoolean("ownsNeonTheme", ownsNeonTheme);
        editor.apply();
    }
    
    private void setupUI() {
        // Currency displays
        gemsText = findViewById(R.id.gems_text);
        coinsText = findViewById(R.id.coins_text);
        
        // Update UI with loaded data
        updateCurrencyDisplay();
        updateThemeButtons();
    }
    
    private void updateCurrencyDisplay() {
        if (gemsText != null) {
            gemsText.setText(String.format("%,d", gems));
        }
        if (coinsText != null) {
            coinsText.setText(String.format("%,d", coins));
        }
    }
    
    private void updateThemeButtons() {
        // Update theme buttons based on ownership
        Button btnBuyClassic = findViewById(R.id.btn_buy_classic);
        Button btnBuyDark = findViewById(R.id.btn_buy_dark);
        Button btnBuyNeon = findViewById(R.id.btn_buy_neon);
        
        if (btnBuyClassic != null) {
            btnBuyClassic.setText(ownsClassicTheme ? "OWNED" : "BUY");
            btnBuyClassic.setEnabled(!ownsClassicTheme);
        }
        
        if (btnBuyDark != null) {
            btnBuyDark.setText(ownsDarkTheme ? "OWNED" : "BUY");
            btnBuyDark.setEnabled(!ownsDarkTheme);
        }
        
        if (btnBuyNeon != null) {
            btnBuyNeon.setText(ownsNeonTheme ? "OWNED" : "BUY");
            btnBuyNeon.setEnabled(!ownsNeonTheme);
        }
    }
    
    private void setupClickListeners() {
        // Back button
        View btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                animateButtonClick(v);
                finish(); // Go back to previous activity
            });
        }
        
        // Gem pack purchases
        View btnWatchAdSmall = findViewById(R.id.btn_watch_ad_small);
        if (btnWatchAdSmall != null) {
            btnWatchAdSmall.setOnClickListener(v -> {
                animateButtonClick(v);
                watchAdForGems(100, "Small Gem Pack");
            });
        }
        
        View btnWatchAdMedium = findViewById(R.id.btn_watch_ad_medium);
        if (btnWatchAdMedium != null) {
            btnWatchAdMedium.setOnClickListener(v -> {
                animateButtonClick(v);
                watchAdForGems(300, "Medium Gem Pack");
            });
        }
        
        View btnWatchAdLarge = findViewById(R.id.btn_watch_ad_large);
        if (btnWatchAdLarge != null) {
            btnWatchAdLarge.setOnClickListener(v -> {
                animateButtonClick(v);
                watchAdForGems(750, "Large Gem Pack");
            });
        }
        
        // Theme purchases
        View btnBuyClassic = findViewById(R.id.btn_buy_classic);
        if (btnBuyClassic != null) {
            btnBuyClassic.setOnClickListener(v -> {
                animateButtonClick(v);
                if (!ownsClassicTheme) {
                    purchaseTheme("Classic 8 Ball Pool", 500, 0);
                }
            });
        }
        
        View btnBuyDark = findViewById(R.id.btn_buy_dark);
        if (btnBuyDark != null) {
            btnBuyDark.setOnClickListener(v -> {
                animateButtonClick(v);
                if (!ownsDarkTheme) {
                    purchaseTheme("Dark Elite", 750, 1);
                }
            });
        }
        
        View btnBuyNeon = findViewById(R.id.btn_buy_neon);
        if (btnBuyNeon != null) {
            btnBuyNeon.setOnClickListener(v -> {
                animateButtonClick(v);
                if (!ownsNeonTheme) {
                    purchaseTheme("Neon Nights", 1000, 2);
                }
            });
        }
        
        // Navigation
        setupNavigationClickListeners();
    }
    
    private void setupNavigationClickListeners() {
        // Home
        View navHome = findViewById(R.id.nav_home);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                animateNavClick(v);
                Intent intent = new Intent(this, startModern.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }
        
        // Friends
        View navFriends = findViewById(R.id.nav_friends);
        if (navFriends != null) {
            navFriends.setOnClickListener(v -> {
                animateNavClick(v);
                Toast.makeText(this, "Friends feature coming soon!", Toast.LENGTH_SHORT).show();
            });
        }
        
        // Equipment/Chess
        View navEquipment = findViewById(R.id.nav_equipment);
        if (navEquipment != null) {
            navEquipment.setOnClickListener(v -> {
                animateNavClick(v);
                showChessModesDialog();
            });
        }
        
        // Events
        View navEvents = findViewById(R.id.nav_events);
        if (navEvents != null) {
            navEvents.setOnClickListener(v -> {
                animateNavClick(v);
                startActivity(new Intent(this, LichessActivity.class));
            });
        }
        
        // Shop (already on shop)
        View navShop = findViewById(R.id.nav_shop);
        if (navShop != null) {
            navShop.setOnClickListener(v -> {
                animateNavClick(v);
                Toast.makeText(this, "Already in Shop", Toast.LENGTH_SHORT).show();
            });
        }
    }
    
    private void watchAdForGems(int gemAmount, String packName) {
        // TODO: Integrate actual ad SDK here
        // For now, simulate ad completion
        showAdWatchDialog(gemAmount, packName);
    }
    
    private void showAdWatchDialog(int gemAmount, String packName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Watch Advertisement");
        builder.setMessage("Watch a short advertisement to receive " + gemAmount + " gems?\n\n" +
                        "This helps support the development of the app!");
        
        builder.setPositiveButton("Watch Ad", (dialog, which) -> {
            // Simulate ad completion after 3 seconds
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                gems += gemAmount;
                updateCurrencyDisplay();
                savePlayerData();
                Toast.makeText(this, "Received " + gemAmount + " gems! Thank you for supporting!", Toast.LENGTH_LONG).show();
            }, 3000);
            
            Toast.makeText(this, "Ad loading...", Toast.LENGTH_SHORT).show();
        });
        
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    
    private void purchaseTheme(String themeName, int gemCost, int themeType) {
        if (gems < gemCost) {
            showInsufficientGemsDialog(themeName, gemCost);
            return;
        }
        
        // Confirm purchase
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Purchase Theme");
        builder.setMessage("Buy " + themeName + " theme for " + gemCost + " gems?\n\n" +
                        "This theme will be permanently unlocked!");
        
        builder.setPositiveButton("Buy", (dialog, which) -> {
            // Process purchase
            gems -= gemCost;
            
            // Update ownership
            switch (themeType) {
                case 0: // Classic
                    ownsClassicTheme = true;
                    break;
                case 1: // Dark
                    ownsDarkTheme = true;
                    break;
                case 2: // Neon
                    ownsNeonTheme = true;
                    break;
            }
            
            updateCurrencyDisplay();
            updateThemeButtons();
            savePlayerData();
            
            Toast.makeText(this, "Theme purchased: " + themeName + "!", Toast.LENGTH_LONG).show();
            
            // TODO: Apply theme change immediately
            applyTheme(themeType);
        });
        
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    
    private void showInsufficientGemsDialog(String themeName, int requiredGems) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insufficient Gems");
        builder.setMessage("You need " + requiredGems + " gems to purchase " + themeName + ".\n\n" +
                        "Current gems: " + gems + "\n" +
                        "Watch advertisements to earn more gems!");
        
        builder.setPositiveButton("Get Gems", (dialog, which) -> {
            // Scroll to gem section
            // TODO: Implement smooth scroll to gem section
        });
        
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    
    private void applyTheme(int themeType) {
        SharedPreferences prefs = getSharedPreferences("ChessGame", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("currentTheme", themeType);
        editor.apply();
        
        // TODO: Implement theme changing logic
        // This would update colors, drawables, etc.
        Toast.makeText(this, "Theme applied! Restart to see full effect.", Toast.LENGTH_SHORT).show();
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
        
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    
    private void startAnimations() {
        // Animate cards with staggered delay
        animateViewWithDelay(findViewById(R.id.card_small_gems), 100);
        animateViewWithDelay(findViewById(R.id.card_medium_gems), 200);
        animateViewWithDelay(findViewById(R.id.card_large_gems), 300);
        animateViewWithDelay(findViewById(R.id.card_classic_theme), 400);
        animateViewWithDelay(findViewById(R.id.card_dark_theme), 500);
        animateViewWithDelay(findViewById(R.id.card_neon_theme), 600);
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
}