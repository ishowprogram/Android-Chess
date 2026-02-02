package jwtc.android.chess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import jwtc.android.chess.activities.BoardPreferencesActivity;
import jwtc.android.chess.activities.StartBaseActivity;
import jwtc.android.chess.hotspotboard.HotspotBoardActivity;
import jwtc.android.chess.ics.ICSClient;
import jwtc.android.chess.lichess.LichessActivity;
import jwtc.android.chess.play.PlayActivity;
import jwtc.android.chess.practice.PracticeActivity;
import jwtc.android.chess.puzzle.PuzzleActivity;
import jwtc.android.chess.tools.AdvancedActivity;

public class start extends StartBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        layoutResource = R.layout.start_foss_modern;

        super.onCreate(savedInstanceState);
        
        setupModernCardClickListeners();
    }
    
    private void setupModernCardClickListeners() {
        try {
            // Play Card
            View playCard = findViewById(R.id.card_play);
            if (playCard != null) {
                playCard.setOnClickListener(v -> {
                    try {
                        Intent intent = new Intent();
                        intent.setClass(this, PlayActivity.class);
                        startActivity(intent);
                        Log.d("start", "PlayActivity started successfully");
                    } catch (Exception e) {
                        Log.e("start", "Error starting PlayActivity", e);
                    }
                });
            }
            
            // Lichess Card
            View lichessCard = findViewById(R.id.card_lichess);
            if (lichessCard != null) {
                lichessCard.setOnClickListener(v -> {
                    try {
                        Intent intent = new Intent();
                        intent.setClass(this, LichessActivity.class);
                        startActivity(intent);
                        Log.d("start", "LichessActivity started successfully");
                    } catch (Exception e) {
                        Log.e("start", "Error starting LichessActivity", e);
                    }
                });
            }
            
            // Practice Card
            View practiceCard = findViewById(R.id.card_practice);
            if (practiceCard != null) {
                practiceCard.setOnClickListener(v -> {
                    try {
                        Intent intent = new Intent();
                        intent.setClass(this, PracticeActivity.class);
                        startActivity(intent);
                        Log.d("start", "PracticeActivity started successfully");
                    } catch (Exception e) {
                        Log.e("start", "Error starting PracticeActivity", e);
                    }
                });
            }
            
            // Puzzles Card
            View puzzlesCard = findViewById(R.id.card_puzzles);
            if (puzzlesCard != null) {
                puzzlesCard.setOnClickListener(v -> {
                    try {
                        Intent intent = new Intent();
                        intent.setClass(this, PuzzleActivity.class);
                        startActivity(intent);
                        Log.d("start", "PuzzleActivity started successfully");
                    } catch (Exception e) {
                        Log.e("start", "Error starting PuzzleActivity", e);
                    }
                });
            }
            
            // Settings Card
            View settingsCard = findViewById(R.id.board_preferences);
            if (settingsCard != null) {
                settingsCard.setOnClickListener(v -> {
                    try {
                        Intent intent = new Intent();
                        intent.setClass(this, BoardPreferencesActivity.class);
                        startActivity(intent);
                        Log.d("start", "BoardPreferencesActivity started successfully");
                    } catch (Exception e) {
                        Log.e("start", "Error starting BoardPreferencesActivity", e);
                    }
                });
            }
            
            // Tools Card
            View toolsCard = findViewById(R.id.card_tools);
            if (toolsCard != null) {
                toolsCard.setOnClickListener(v -> {
                    try {
                        showToolsDialog();
                        Log.d("start", "Tools dialog shown successfully");
                    } catch (Exception e) {
                        Log.e("start", "Error showing tools dialog", e);
                    }
                });
            }
        } catch (Exception e) {
            Log.e("start", "Error setting up click listeners", e);
        }
    }
    
    private void showToolsDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tools");
            String[] tools = {"ICS (FICS)", "Hotspot Board", "PGN Tools"};
            
            builder.setItems(tools, (dialog, which) -> {
                try {
                    Intent intent = new Intent();
                    switch (which) {
                        case 0: // ICS
                            intent.setClass(this, ICSClient.class);
                            startActivity(intent);
                            break;
                        case 1: // Hotspot Board
                            intent.setClass(this, HotspotBoardActivity.class);
                            startActivity(intent);
                            break;
                        case 2: // PGN Tools
                            intent.setClass(this, AdvancedActivity.class);
                            startActivity(intent);
                            break;
                    }
                } catch (Exception e) {
                    Log.e("start", "Error starting activity from tools dialog", e);
                }
            });
            
            builder.show();
        } catch (Exception e) {
            Log.e("start", "Error creating tools dialog", e);
        }
    }
}
