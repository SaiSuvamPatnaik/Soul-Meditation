package com.example.soulmeditation;

import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Linkify {



    public static void addLinks(TextView textView, String linkThis, String toThis) {
        Pattern pattern = Pattern.compile(linkThis);
        String scheme = toThis;
        android.text.util.Linkify.addLinks(textView, pattern, scheme, new android.text.util.Linkify.MatchFilter() {
            @Override
            public boolean acceptMatch(CharSequence s, int start, int end) {
                return true;
            }
        }, new android.text.util.Linkify.TransformFilter() {

            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        });
    }
}
