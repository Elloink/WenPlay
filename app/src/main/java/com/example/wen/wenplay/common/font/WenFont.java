package com.example.wen.wenplay.common.font;

import android.content.Context;
import android.graphics.Typeface;

import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.ITypeface;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by wen on 2017/3/18.
 */

public class WenFont implements ITypeface {

    private static final String TTF_FILE = "iconfont1.ttf";
    private static Typeface typeface = null;
    private static HashMap<String, Character> mChars;

    @Override
    public IIcon getIcon(String key) {
        return WenFont.Icon.valueOf(key);
    }

    @Override
    public HashMap<String, Character> getCharacters() {
        if (mChars == null) {
            HashMap<String, Character> aChars = new HashMap<String, Character>();
            for (WenFont.Icon v : WenFont.Icon.values()) {
                aChars.put(v.name(), v.character);
            }
            mChars = aChars;
        }
        return mChars;
    }

    @Override
    public String getMappingPrefix() {
        return "wen";
    }

    @Override
    public String getFontName() {
        return "wen";
    }

    @Override
    public String getVersion() {
        return "1.0.1";
    }

    @Override
    public int getIconCount() {
        return mChars.size();
    }

    @Override
    public Collection<String> getIcons() {
        Collection<String> icons = new LinkedList<String>();
        for (WenFont.Icon value : WenFont.Icon.values()) {
            icons.add(value.name());
        }
        return icons;
    }

    @Override
    public String getAuthor() {
        return "Wen";
    }

    @Override
    public String getUrl() {
        return "https://github.com/wyydev";
    }

    @Override
    public String getDescription() {
        return "The premium icon font for Ionic Framework.";
    }

    @Override
    public String getLicense() {
        return "MIT Licensed";
    }

    @Override
    public String getLicenseUrl() {
        return "https://github.com/wyydev";
    }

    @Override
    public Typeface getTypeface(Context context) {
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(),TTF_FILE);
            } catch (Exception e) {
                return null;
            }
        }
        return typeface;
    }

    public enum Icon implements IIcon {

        wen_refresh('\ue6aa'),
        wen_set('\ue6ae'),
        wen_download('\ue714'),
        wen_shutdown('\ue621'),
        wen_delete('\ue622'),
        wen_user('\ue623'),
        wen_account('\ue722');

        char character;

        Icon(char character) {
            this.character = character;
        }

        public String getFormattedName() {
            return "{" + name() + "}";
        }

        public char getCharacter() {
            return character;
        }

        public String getName() {
            return name();
        }

        // remember the typeface so we can use it later
        private static ITypeface typeface;

        public ITypeface getTypeface() {
            if (typeface == null) {
                typeface = new WenFont();
            }
            return typeface;
        }
    }

}
