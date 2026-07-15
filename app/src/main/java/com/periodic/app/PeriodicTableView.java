package com.periodic.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.view.*;
import java.util.*;

/**
 * v2.3 main exhibit. Preserves the v2.2 element geometry while adding the
 * approved laboratory navigation, quick-search, favorites, settings and
 * achievement surfaces directly over the real Filament room.
 */
public final class PeriodicTableView extends View {
    public interface Listener { void onElement(int atomicNumber); }

    private final Listener listener;
    private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private final ScaleGestureDetector scaler;
    private final GestureDetector gestures;
    private final HashMap<Integer, RectF> elementHits = new HashMap<>();
    private final HashMap<String, RectF> uiHits = new HashMap<>();
    private final LinkedHashSet<Integer> favorites = new LinkedHashSet<>();

    private float zoom = 1.12f, panX = 0f, yaw = 0f, phase = 0f;
    private int pressed = -1;
    private String overlay = "";
    private boolean cameraLocked = false;
    private boolean reducedMotion = false;
    private boolean highQuality = true;

    public PeriodicTableView(Context c, Listener listener) {
        super(c);
        this.listener = listener;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setBackgroundColor(Color.TRANSPARENT);
        p.setTypeface(Typeface.create("sans", Typeface.BOLD));

        scaler = new ScaleGestureDetector(c, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override public boolean onScale(ScaleGestureDetector d) {
                if (cameraLocked || !overlay.isEmpty()) return false;
                zoom = Math.max(.82f, Math.min(2.7f, zoom * d.getScaleFactor()));
                invalidate();
                return true;
            }
        });

        gestures = new GestureDetector(c, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onDown(MotionEvent e) { return true; }
            @Override public boolean onScroll(MotionEvent a, MotionEvent b, float dx, float dy) {
                if (cameraLocked || !overlay.isEmpty()) return false;
                panX = Math.max(-getWidth() * .32f, Math.min(getWidth() * .32f, panX - dx));
                yaw = Math.max(-13f, Math.min(13f, panX / Math.max(1f, getWidth()) * 30f));
                invalidate();
                return true;
            }
            @Override public boolean onDoubleTap(MotionEvent e) {
                if (!overlay.isEmpty()) return false;
                resetCamera();
                return true;
            }
            @Override public boolean onSingleTapUp(MotionEvent e) { return tap(e.getX(), e.getY()); }
        });
    }

    @Override public boolean onTouchEvent(MotionEvent e) {
        scaler.onTouchEvent(e);
        gestures.onTouchEvent(e);
        return true;
    }

    private Matrix tableMatrix() {
        float w = getWidth(), h = getHeight();
        float usableW = Math.max(1f, w - 100f);
        float base = Math.min(usableW / 1520f, h / 790f);
        Matrix m = new Matrix();
        m.postTranslate(-760, -395);
        m.postScale(base * zoom, base * zoom);
        float perspective = (float) Math.sin(Math.toRadians(yaw));
        m.postSkew(perspective * .055f, 0f);
        m.postScale(1f - Math.abs(perspective) * .045f, 1f, 760, 395);
        m.postTranslate(50 + usableW / 2f + panX * .24f, h / 2f + 12);
        return m;
    }

    private boolean tap(float x, float y) {
        float sx = 1600f / Math.max(1, getWidth());
        float sy = 900f / Math.max(1, getHeight());
        float ux = x * sx, uy = y * sy;

        for (Map.Entry<String, RectF> en : uiHits.entrySet()) {
            if (en.getValue().contains(ux, uy)) {
                handleUi(en.getKey());
                performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                return true;
            }
        }
        if (!overlay.isEmpty()) return true;

        Matrix inv = new Matrix();
        if (!tableMatrix().invert(inv)) return false;
        float[] q = {x, y};
        inv.mapPoints(q);
        for (Map.Entry<Integer, RectF> en : elementHits.entrySet()) {
            if (en.getValue().contains(q[0], q[1])) {
                pressed = en.getKey();
                invalidate();
                performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                postDelayed(() -> listener.onElement(en.getKey()), 100);
                return true;
            }
        }
        return false;
    }

    private void handleUi(String key) {
        if (key.equals("close")) overlay = "";
        else if (key.equals("home") || key.equals("reset")) { overlay = ""; resetCamera(); }
        else if (key.equals("search")) overlay = "search";
        else if (key.equals("favorites")) overlay = "favorites";
        else if (key.equals("settings")) overlay = "settings";
        else if (key.equals("achievements")) overlay = "achievements";
        else if (key.equals("journey")) getContext().startActivity(new Intent(getContext(), ScaleJourneyActivity.class));
        else if (key.equals("explore")) openExplore();
        else if (key.equals("compare")) openExplore();
        else if (key.equals("favorite-current")) {
            int z = pressed > 0 ? pressed : 26;
            if (favorites.contains(z)) favorites.remove(z); else favorites.add(z);
        }
        else if (key.equals("lock")) cameraLocked = !cameraLocked;
        else if (key.equals("quality")) highQuality = !highQuality;
        else if (key.equals("motion")) reducedMotion = !reducedMotion;
        else if (key.startsWith("pick-")) {
            int z = Integer.parseInt(key.substring(5));
            overlay = "";
            pressed = z;
            listener.onElement(z);
        }
        invalidate();
    }

    private void openExplore() {
        int z = pressed > 0 ? pressed : 26;
        getContext().startActivity(new Intent(getContext(), ExploreActivity.class).putExtra("atomicNumber", z));
    }

    private void resetCamera() {
        zoom = 1.12f; panX = 0f; yaw = 0f; pressed = -1; invalidate();
    }

    @Override protected void onDraw(Canvas c) {
        super.onDraw(c);
        c.save();
        c.concat(tableMatrix());
        drawTable(c);
        c.restore();

        float ax = getWidth() / 1600f, ay = getHeight() / 900f;
        c.save(); c.scale(ax, ay);
        drawChrome(c);
        if (!overlay.isEmpty()) drawOverlay(c);
        c.restore();

        if (!reducedMotion) phase = (phase + .55f) % 360f;
        postInvalidateOnAnimation();
    }

    private void drawTable(Canvas c) {
        elementHits.clear();
        float cw = 79, ch = 78, ox = 50, oy = 42;
        p.setStyle(Paint.Style.FILL);
        p.setShadowLayer(32, 0, 18, Color.argb(185, 0, 0, 0));
        p.setColor(Color.argb(78, 8, 13, 18));
        c.drawRoundRect(15, 6, 1505, 760, 30, 30, p);
        p.clearShadowLayer();
        p.setShader(new LinearGradient(0, 0, 1520, 760,
                new int[]{Color.argb(66, 28, 39, 45), Color.argb(38, 12, 20, 27), Color.argb(68, 32, 42, 47)},
                null, Shader.TileMode.CLAMP));
        c.drawRoundRect(22, 13, 1498, 753, 26, 26, p);
        p.setShader(null);
        p.setShader(new RadialGradient(760, 735, 610,
                new int[]{Color.argb(90, 66, 154, 183), Color.argb(24, 32, 87, 104), Color.TRANSPARENT},
                null, Shader.TileMode.CLAMP));
        c.drawRect(80, 560, 1440, 770, p);
        p.setShader(null);

        for (Element e : ElementData.ALL) {
            float x = ox + (e.group - 1) * cw, y = oy + (e.period - 1) * ch;
            RectF r = new RectF(x, y, x + 72, y + 70);
            elementHits.put(e.number, r);
            drawTile(c, e, r, e.number == pressed);
        }
        p.setTextAlign(Paint.Align.LEFT);
        p.setColor(Color.rgb(190, 211, 218));
        p.setTextSize(15);
        c.drawText("Lanthanides and actinides are displayed in their conventional detached rows.", 54, 735, p);
    }

    private void drawTile(Canvas c, Element e, RectF r, boolean active) {
        float lift = active ? -5 : 0, depth = active ? 10 : 7;
        RectF top = new RectF(r.left, r.top + lift, r.right, r.bottom + lift);
        int base = Visuals.categoryColor(e.category), side = Visuals.darken(base, .42f), edge = Visuals.darken(base, .62f), hi = Visuals.lighten(base, .20f);
        Path right = new Path();
        right.moveTo(top.right, top.top + 5); right.lineTo(top.right + depth, top.top + depth);
        right.lineTo(top.right + depth, top.bottom + depth); right.lineTo(top.right, top.bottom); right.close();
        p.setColor(side); c.drawPath(right, p);
        Path low = new Path();
        low.moveTo(top.left + 5, top.bottom); low.lineTo(top.right, top.bottom);
        low.lineTo(top.right + depth, top.bottom + depth); low.lineTo(top.left + depth, top.bottom + depth); low.close();
        p.setColor(edge); c.drawPath(low, p);
        p.setShadowLayer(active ? 22 : 6, 0, active ? 6 : 4,
                active ? Color.argb(225, 90, 210, 245) : Color.argb(130, 0, 0, 0));
        p.setShader(new LinearGradient(top.left, top.top, top.right, top.bottom,
                new int[]{hi, base, Visuals.darken(base, .13f)}, null, Shader.TileMode.CLAMP));
        c.drawRoundRect(top, 6, 6, p); p.setShader(null); p.clearShadowLayer();
        float sweep = ((phase * 1.15f + e.number * 13) % 125) - 30;
        p.setShader(new LinearGradient(top.left + sweep, top.top, top.left + sweep + 27, top.bottom,
                new int[]{Color.TRANSPARENT, Color.argb(48, 245, 252, 255), Color.TRANSPARENT}, null, Shader.TileMode.CLAMP));
        c.drawRoundRect(top, 6, 6, p); p.setShader(null);
        p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(active ? 2.8f : 1.8f);
        p.setColor(active ? Color.rgb(118, 226, 245) : edge); c.drawRoundRect(top, 6, 6, p);
        p.setStyle(Paint.Style.FILL);
        int ink = Color.rgb(238, 244, 244);
        p.setShadowLayer(2, 0, 2, Color.argb(190, 0, 0, 0)); p.setColor(ink);
        p.setTextAlign(Paint.Align.LEFT); p.setTextSize(10.5f); c.drawText(String.valueOf(e.number), top.left + 6, top.top + 13, p);
        p.setTextAlign(Paint.Align.CENTER); p.setTextSize(27); c.drawText(e.symbol, top.centerX(), top.top + 38, p);
        p.setTextSize(8.4f); c.drawText(e.name, top.centerX(), top.top + 51, p);
        p.setTextSize(8.0f); p.setColor(Color.rgb(218, 229, 229)); c.drawText(e.mass, top.centerX(), top.top + 63, p);
        p.clearShadowLayer();
    }

    private void drawChrome(Canvas c) {
        uiHits.clear();
        // Top title bar
        p.setColor(Color.argb(145, 4, 12, 19)); c.drawRoundRect(158, 18, 1548, 83, 18, 18, p);
        text(c, 31, Color.WHITE, Paint.Align.LEFT, "Periodic 2.3", 220, 50);
        text(c, 13, Color.rgb(144, 203, 218), Paint.Align.LEFT, "Explore the Elements", 221, 72);
        chip(c, "search", new RectF(1420, 26, 1480, 75), "⌕", false);
        chip(c, "favorite-current", new RectF(1488, 26, 1540, 75), "★", favorites.contains(pressed > 0 ? pressed : 26));

        // Left navigation rail
        p.setColor(Color.argb(205, 5, 17, 25)); c.drawRoundRect(12, 18, 145, 866, 22, 22, p);
        String[][] nav = {{"home","⌂","Home"},{"explore","⚛","Explore"},{"journey","◉","Journey"},{"compare","▦","Compare"},{"search","⌕","Search"},{"settings","⚙","Settings"},{"favorites","★","Favorites"},{"achievements","♛","Progress"}};
        float y = 105;
        for (String[] n : nav) {
            RectF r = new RectF(22, y - 32, 135, y + 40); uiHits.put(n[0], r);
            if (n[0].equals(overlay)) { p.setColor(Color.argb(150, 26, 93, 116)); c.drawRoundRect(r, 14, 14, p); }
            text(c, 27, Color.rgb(216, 237, 242), Paint.Align.CENTER, n[1], 78, y - 2);
            text(c, 11, Color.rgb(190, 214, 220), Paint.Align.CENTER, n[2], 78, y + 24);
            y += 92;
        }

        // Bottom fact cards, matching the approved direction
        infoCard(c, 160, 793, 240, 72, "118", "Elements");
        infoCard(c, 412, 793, 240, 72, "3D", "Real World");
        infoCard(c, 664, 793, 300, 72, "Science", "At Your Fingertips");
        chip(c, "reset", new RectF(1290, 807, 1410, 857), "RESET", false);
        chip(c, "lock", new RectF(1422, 807, 1542, 857), cameraLocked ? "UNLOCK" : "LOCK", cameraLocked);
    }

    private void drawOverlay(Canvas c) {
        p.setColor(Color.argb(220, 1, 7, 12)); c.drawRect(145, 0, 1600, 900, p);
        RectF box = new RectF(200, 90, 1515, 830);
        p.setColor(Color.argb(245, 6, 20, 29)); c.drawRoundRect(box, 28, 28, p);
        p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(2); p.setColor(Color.rgb(48, 118, 140)); c.drawRoundRect(box, 28, 28, p); p.setStyle(Paint.Style.FILL);
        chip(c, "close", new RectF(1435, 108, 1490, 160), "×", false);

        if (overlay.equals("search")) drawSearchOverlay(c);
        else if (overlay.equals("favorites")) drawFavoritesOverlay(c);
        else if (overlay.equals("settings")) drawSettingsOverlay(c);
        else drawAchievementsOverlay(c);
    }

    private void drawSearchOverlay(Canvas c) {
        text(c, 30, Color.WHITE, Paint.Align.LEFT, "Search & Filter", 245, 145);
        text(c, 14, Color.rgb(142, 201, 214), Paint.Align.LEFT, "Quick access to elements and categories", 247, 176);
        String[] cats = {"All", "Metals", "Nonmetals", "Metalloids", "Gases", "Liquids"};
        float x = 245;
        for (String cat : cats) { simpleChip(c, new RectF(x, 205, x + 160, 252), cat, cat.equals("All")); x += 172; }
        int[] picks = {1,2,3,4,5,6,7,8,26,29,47,79,80,92};
        float sx = 245, sy = 292;
        for (int i = 0; i < picks.length; i++) {
            Element e = ElementData.byNumber(picks[i]); if (e == null) continue;
            RectF r = new RectF(sx, sy, sx + 160, sy + 105); uiHits.put("pick-" + e.number, r);
            p.setColor(Visuals.categoryColor(e.category)); c.drawRoundRect(r, 14, 14, p);
            text(c, 13, Color.WHITE, Paint.Align.LEFT, String.valueOf(e.number), sx + 12, sy + 22);
            text(c, 33, Color.WHITE, Paint.Align.CENTER, e.symbol, sx + 80, sy + 61);
            text(c, 12, Color.WHITE, Paint.Align.CENTER, e.name, sx + 80, sy + 88);
            sx += 180; if (sx > 1320) { sx = 245; sy += 125; }
        }
    }

    private void drawFavoritesOverlay(Canvas c) {
        text(c, 30, Color.WHITE, Paint.Align.LEFT, "★  My Favorite Elements", 245, 145);
        if (favorites.isEmpty()) {
            text(c, 18, Color.rgb(175, 205, 212), Paint.Align.CENTER, "Open an element and tap the star to add it here.", 850, 420);
            return;
        }
        float x = 245, y = 230;
        for (int z : favorites) {
            Element e = ElementData.byNumber(z); if (e == null) continue;
            RectF r = new RectF(x, y, x + 190, y + 160); uiHits.put("pick-" + z, r);
            p.setColor(Color.argb(230, 12, 36, 47)); c.drawRoundRect(r, 18, 18, p);
            text(c, 48, Color.WHITE, Paint.Align.CENTER, e.symbol, x + 95, y + 72);
            text(c, 15, Color.rgb(160, 211, 224), Paint.Align.CENTER, e.name, x + 95, y + 112);
            x += 215; if (x > 1300) { x = 245; y += 180; }
        }
    }

    private void drawSettingsOverlay(Canvas c) {
        text(c, 30, Color.WHITE, Paint.Align.LEFT, "Settings", 245, 145);
        settingRow(c, "quality", 245, 215, "Visual Quality", highQuality ? "High" : "Balanced");
        settingRow(c, "motion", 245, 305, "Motion", reducedMotion ? "Reduced" : "Normal");
        settingRow(c, "lock", 245, 395, "Table Camera", cameraLocked ? "Locked" : "Free");
        text(c, 15, Color.rgb(158, 195, 204), Paint.Align.LEFT,
                "These controls change rendering behavior without altering the original v2.2 tile design.", 245, 520);
    }

    private void drawAchievementsOverlay(Canvas c) {
        text(c, 30, Color.WHITE, Paint.Align.LEFT, "Learning Progress", 245, 145);
        p.setColor(Color.argb(235, 12, 43, 56)); c.drawRoundRect(245, 215, 1425, 405, 20, 20, p);
        text(c, 55, Color.rgb(235, 189, 71), Paint.Align.CENTER, "♛", 340, 305);
        text(c, 25, Color.WHITE, Paint.Align.LEFT, "Elements Explorer", 430, 275);
        text(c, 15, Color.rgb(173, 207, 214), Paint.Align.LEFT, "Discover elements, compare properties, and complete laboratory modules.", 430, 310);
        p.setColor(Color.rgb(20, 62, 75)); c.drawRoundRect(430, 340, 1330, 365, 12, 12, p);
        p.setColor(Color.rgb(72, 204, 113)); c.drawRoundRect(430, 340, 800, 365, 12, 12, p);
        text(c, 13, Color.WHITE, Paint.Align.RIGHT, "Local progress • no online account required", 1330, 390);
    }

    private void settingRow(Canvas c, String key, float x, float y, String label, String value) {
        RectF r = new RectF(x, y, 1425, y + 70); uiHits.put(key, r);
        p.setColor(Color.argb(230, 10, 35, 46)); c.drawRoundRect(r, 16, 16, p);
        text(c, 18, Color.WHITE, Paint.Align.LEFT, label, x + 28, y + 43);
        text(c, 16, Color.rgb(145, 210, 225), Paint.Align.RIGHT, value + "  ›", 1390, y + 43);
    }

    private void infoCard(Canvas c, float x, float y, float w, float h, String big, String small) {
        p.setColor(Color.argb(210, 8, 31, 42)); c.drawRoundRect(x, y, x + w, y + h, 16, 16, p);
        p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(1.5f); p.setColor(Color.rgb(44, 111, 132)); c.drawRoundRect(x, y, x + w, y + h, 16, 16, p); p.setStyle(Paint.Style.FILL);
        text(c, 19, Color.WHITE, Paint.Align.CENTER, big, x + w / 2, y + 29);
        text(c, 12, Color.rgb(165, 202, 211), Paint.Align.CENTER, small, x + w / 2, y + 53);
    }

    private void chip(Canvas c, String key, RectF r, String label, boolean active) {
        uiHits.put(key, r); simpleChip(c, r, label, active);
    }

    private void simpleChip(Canvas c, RectF r, String label, boolean active) {
        p.setColor(active ? Color.rgb(18, 96, 128) : Color.argb(230, 9, 34, 45)); c.drawRoundRect(r, 14, 14, p);
        p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(active ? 2.2f : 1.3f); p.setColor(active ? Color.rgb(116, 227, 245) : Color.rgb(45, 103, 119)); c.drawRoundRect(r, 14, 14, p); p.setStyle(Paint.Style.FILL);
        text(c, 13, Color.WHITE, Paint.Align.CENTER, label, r.centerX(), r.centerY() + 5);
    }

    private void text(Canvas c, float size, int color, Paint.Align align, String value, float x, float y) {
        p.setTextSize(size); p.setColor(color); p.setTextAlign(align); p.setTypeface(Typeface.create("sans", Typeface.BOLD)); c.drawText(value, x, y, p);
    }
}
