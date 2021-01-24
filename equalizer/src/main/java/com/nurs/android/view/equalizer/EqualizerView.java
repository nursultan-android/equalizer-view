package com.nurs.android.view.equalizer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class EqualizerView extends LinearLayout {
    ImageView musicBar1;
    ImageView musicBar2;
    ImageView musicBar3;

    AnimatorSet playingSet;
    AnimatorSet stopSet;
    Boolean animating = false;

    int foregroundColor, duration;
    Drawable columnDrawable;
    float columnWidth;
    private final Context context;


    public EqualizerView(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public EqualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setAttrs(context, attrs);
        initViews();
    }

    public EqualizerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setAttrs(context, attrs);
        initViews();
    }

    private void setAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EqualizerView,
                0, 0);
        try {
            foregroundColor = a.getInt(R.styleable.EqualizerView_column_color, Color.BLACK);
            columnDrawable = a.getDrawable(R.styleable.EqualizerView_column_drawable);
            duration = a.getInt(R.styleable.EqualizerView_anim_duration, 8000);
            columnWidth = a.getDimension(R.styleable.EqualizerView_column_width, 15);
        } finally {
            a.recycle();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.equalizer_lay, this, true);
        musicBar1 = findViewById(R.id.music_bar1);
        musicBar2 = findViewById(R.id.music_bar2);
        musicBar3 = findViewById(R.id.music_bar3);

        if(columnDrawable!=null){
            setFromAttrs(columnDrawable);
        } else {
            setFromAttrs(context.getDrawable(R.drawable.rounded_white_box_eq));
        }
        musicBar1.setColorFilter(foregroundColor);
        musicBar2.setColorFilter(foregroundColor);
        musicBar3.setColorFilter(foregroundColor);
        ViewGroup.LayoutParams params1 = musicBar1.getLayoutParams();
        ViewGroup.LayoutParams params2 = musicBar2.getLayoutParams();
        ViewGroup.LayoutParams params3 = musicBar3.getLayoutParams();
        params1.width=countWidth(columnWidth);
        params2.width=countWidth(columnWidth);
        params3.width=countWidth(columnWidth);
        musicBar1.setLayoutParams(params1);
        musicBar2.setLayoutParams(params2);
        musicBar3.setLayoutParams(params3);
        setPivots();
    }

    private void setFromAttrs(Drawable columnDrawable) {
        musicBar1.setImageDrawable(columnDrawable);
        musicBar2.setImageDrawable(columnDrawable);
        musicBar3.setImageDrawable(columnDrawable);
    }

    private int countWidth(float columnWidth) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (columnWidth * scale + 0.5f);
    }

    private void setPivots() {
        musicBar1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setHeightToView(musicBar1, this);
            }
        });
        musicBar2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setHeightToView(musicBar2, this);
            }
        });
        musicBar3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setHeightToView(musicBar3, this);
            }
        });
    }

    private void setHeightToView(ImageView musicBar, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (musicBar.getHeight() > 0) {
            musicBar.setPivotY(musicBar.getHeight());
            musicBar.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    public void animateBars() {
        initViews();
        animating = true;
        if (playingSet == null) {
            ObjectAnimator scaleYbar1 = ObjectAnimator.ofFloat(musicBar1, "scaleY", 0.2f, 0.8f, 0.1f, 0.1f, 0.3f, 0.1f, 0.2f, 0.8f, 0.7f, 0.2f, 0.4f, 0.9f, 0.7f, 0.6f, 0.1f, 0.3f, 0.1f, 0.4f, 0.1f, 0.8f, 0.7f, 0.9f, 0.5f, 0.6f, 0.3f, 0.1f);
            scaleYbar1.setRepeatCount(ValueAnimator.INFINITE);
            ObjectAnimator scaleYbar2 = ObjectAnimator.ofFloat(musicBar2, "scaleY", 0.2f, 0.5f, 1.0f, 0.5f, 0.3f, 0.1f, 0.2f, 0.3f, 0.5f, 0.1f, 0.6f, 0.5f, 0.3f, 0.7f, 0.8f, 0.9f, 0.3f, 0.1f, 0.5f, 0.3f, 0.6f, 1.0f, 0.6f, 0.7f, 0.4f, 0.1f);
            scaleYbar2.setRepeatCount(ValueAnimator.INFINITE);
            ObjectAnimator scaleYbar3 = ObjectAnimator.ofFloat(musicBar3, "scaleY", 0.6f, 0.5f, 1.0f, 0.6f, 0.5f, 1.0f, 0.6f, 0.5f, 1.0f, 0.5f, 0.6f, 0.7f, 0.2f, 0.3f, 0.1f, 0.5f, 0.4f, 0.6f, 0.7f, 0.1f, 0.4f, 0.3f, 0.1f, 0.4f, 0.3f, 0.7f);
            scaleYbar3.setRepeatCount(ValueAnimator.INFINITE);
            createAnimationSetAndStart(scaleYbar1, scaleYbar2, scaleYbar3);
        } else {
            if (playingSet.isPaused()) {
                playingSet.resume();
            }
        }
    }

    private void createAnimationSetAndStart(ObjectAnimator scaleYbar1, ObjectAnimator scaleYbar2, ObjectAnimator scaleYbar3) {
        playingSet = createWithDuration(duration);
        playingSet.playTogether(scaleYbar2, scaleYbar3, scaleYbar1);
        playingSet.setInterpolator(new LinearInterpolator());
        playingSet.start();
    }

    public void stopBars() {
        animating = false;
        if (playingSet != null && playingSet.isRunning() && playingSet.isStarted()) {
            playingSet.pause();
        }

        if (stopSet == null) {
            ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(musicBar1, "scaleY", 0.2f);
            ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(musicBar2, "scaleY", 0.2f);
            ObjectAnimator scaleY3 = ObjectAnimator.ofFloat(musicBar3, "scaleY", 0.2f);
            createAnimationSetAndStop(scaleY3,scaleY2,scaleY1);
        } else if (!stopSet.isStarted()) {
            stopSet.start();
        }
    }

    private void createAnimationSetAndStop(ObjectAnimator scaleY3, ObjectAnimator scaleY2, ObjectAnimator scaleY1) {
        stopSet = createWithDuration(400);
        stopSet.playTogether(scaleY3, scaleY2, scaleY1);
        stopSet.start();
    }

    private AnimatorSet createWithDuration(int duration) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        return set;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setColumnDrawable(Drawable columnDrawable) {
        this.columnDrawable = columnDrawable;
    }

    public Boolean isAnimating() {
        return animating;
    }
}
