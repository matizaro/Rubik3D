package com.mycompany.myjpctapp;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

import com.mycompany.myjpctapp.screens.MainActivity;

/**
 * Created by Welcome on 2015-09-11.
 */
public class StaticThings {
     public static TextView timer, moves;
     public static MainActivity rubikActivity;
     public static RubikTimer rubikTimer;
     public static int rotatingSpeed = 300, wallSpeed = 300;
     public static Typeface robotoLight, robotoBold, robotoThin, robotoRegular;
     public static SpannableStringBuilder convertMovesText(String movesText){
          SpannableStringBuilder styledString = new SpannableStringBuilder(movesText);
          if(movesText.compareTo("00")==0){
               styledString.setSpan(new CustomTypefaceSpan("", StaticThings.robotoThin), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
          }else if(movesText.charAt(0)=='0'){
               styledString.setSpan(new CustomTypefaceSpan("", StaticThings.robotoThin), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
               styledString.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), 1, movesText.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
          }else{
               styledString.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), 0, movesText.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

          }
          return styledString;
     }
     public static void setMovesText(String movesText){
          SpannableStringBuilder styledString = new SpannableStringBuilder(movesText);
          if(movesText.compareTo("00")==0){
               styledString.setSpan(new CustomTypefaceSpan("", StaticThings.robotoThin), 0, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
          }else if(movesText.charAt(0)=='0'){
               styledString.setSpan(new CustomTypefaceSpan("", StaticThings.robotoThin), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
               styledString.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), 1, movesText.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
          }else{
               styledString.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), 0, movesText.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

          }
          StaticThings.moves.setText(styledString);
     }
     public static SpannableStringBuilder convetTime(String text){
          int minutes = Integer.valueOf(text.substring(0,2)), seconds = Integer.valueOf(text.substring(3,5)), milis = Integer.valueOf(text.substring(6,9));
          SpannableStringBuilder timeText = new SpannableStringBuilder(text);

          timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoThin), 0, timeText.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

          int i = 0;
          if (minutes > 0) {
               while (timeText.charAt(i) == '0') {
                    i++;
               }
               timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), i, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
               timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), 3, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
               timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoLight), 6, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
          } else {
               i = 3;
               if (seconds > 0) {
                    while (timeText.charAt(i) == '0') {
                         i++;
                    }
                    timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), i, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoLight), 6, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
               } else {
                    i = 6;
                    if (milis > 0) {
                         while (timeText.charAt(i) == '0') {
                              i++;
                         }
                         timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoLight), i, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    }
               }
          }
          return timeText;
     }
     public static SpannableStringBuilder convetTime(int minutes, int seconds, int milis) {

          String text = (minutes < 10 ? "0" : "") + minutes + ":"
                  + (seconds < 10 ? "0" : "") + seconds + ":"
                  + (milis < 100 ? "0" : "") + (milis < 10 ? "0" : "") + milis;

          SpannableStringBuilder timeText = new SpannableStringBuilder(text);

          timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoThin), 0, timeText.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

          int i = 0;
          if (minutes > 0) {
               while (timeText.charAt(i) == '0') {
                    i++;
               }
               timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), i, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
               timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), 3, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
               timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoLight), 6, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
          } else {
               i = 3;
               if (seconds > 0) {
                    while (timeText.charAt(i) == '0') {
                         i++;
                    }
                    timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoRegular), i, 5, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoLight), 6, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
               } else {
                    i = 6;
                    if (milis > 0) {
                         while (timeText.charAt(i) == '0') {
                              i++;
                         }
                         timeText.setSpan(new CustomTypefaceSpan("", StaticThings.robotoLight), i, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    }
               }
          }
          return timeText;
     }
}
