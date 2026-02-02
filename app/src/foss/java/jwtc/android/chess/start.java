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
        layoutResource = R.layout.start_foss;

        super.onCreate(savedInstanceState);
        
        setupCardClickListeners();
    }
    
    private void setupCardClickListeners() {
        // The old layout uses ListView with array entries, no need for manual setup
        // The ListView will handle clicks automatically from the array resource
        Log.d("start", "Using classic ListView layout");
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
