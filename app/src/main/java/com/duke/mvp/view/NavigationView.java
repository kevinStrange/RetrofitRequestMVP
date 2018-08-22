package com.duke.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duke.mvp.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author : Duke
 * date   : 2018/8/22
 * explain   :顶部导航栏
 * version: 1.0
 */
public class NavigationView extends LinearLayout {

    public NavigationView(Context context) {
        this(context, null);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_navigationview, this, true);
        initView();
        initAttrs(context, attrs);
    }

    protected TextView nv_leftTextView, nv_centerTextView, nv_rightTextView;
    protected FrameLayout nv_leftLayoutContainer, nv_rightLayoutContainer, nv_centerLayoutContainer;
    protected ImageView nv_leftImageView;

    protected ImageView nv_centerImageView;
    protected ImageView nv_rightImageView;

    protected void initView() {
        nv_leftTextView = (TextView) findViewById(R.id.nv_leftTextView);
        nv_leftImageView = (ImageView) findViewById(R.id.nv_leftImageView);
        nv_leftLayoutContainer = (FrameLayout) findViewById(R.id.nv_leftLayoutContainer);

        nv_centerTextView = (TextView) findViewById(R.id.nv_centerTextView);
        nv_centerImageView = (ImageView) findViewById(R.id.nv_centerImageView);
        nv_centerLayoutContainer = (FrameLayout) findViewById(R.id.nv_centerLayoutContainer);

        nv_rightTextView = (TextView) findViewById(R.id.nv_rightTextView);
        nv_rightImageView = (ImageView) findViewById(R.id.nv_rightImageView);
        nv_rightLayoutContainer = (FrameLayout) findViewById(R.id.nv_rightLayoutContainer);
    }

    protected void initAttrs(final Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationView);

        String leftText = typedArray.getString(R.styleable.NavigationView_nv_leftText);
        int leftTextColor = typedArray.getResourceId(R.styleable.NavigationView_nv_leftTextColor, 0);
        int leftTextDrawable = typedArray.getResourceId(R.styleable.NavigationView_nv_leftTextDrawable, 0);
        int leftImageResource = typedArray.getResourceId(R.styleable.NavigationView_nv_leftImageResource, 0);
        boolean leftTextIsBackEvent = typedArray.getBoolean(R.styleable.NavigationView_nv_leftTextIsBackEvent, false);

        String centerText = typedArray.getString(R.styleable.NavigationView_nv_centerText);
        int centerImageResource = typedArray.getResourceId(R.styleable.NavigationView_nv_centerImageResource, 0);
        int centerTextColor = typedArray.getResourceId(R.styleable.NavigationView_nv_centerTextColor, 0);

        String rightText = typedArray.getString(R.styleable.NavigationView_nv_rightText);
        int rightTextDrawable = typedArray.getResourceId(R.styleable.NavigationView_nv_rightTextDrawable, 0);
        int rightImageResource = typedArray.getResourceId(R.styleable.NavigationView_nv_rightImageResource, 0);
        int rightTextColor = typedArray.getResourceId(R.styleable.NavigationView_nv_rightTextColor, 0);

        typedArray.recycle();

        if (leftText != null) {
            setText(LEFT_TEXT_VIEW, leftText);
        }
        if (leftTextDrawable != 0) {
            setTextViewDrawable(LEFT_TEXT_VIEW, leftTextDrawable);
        }
        if (leftImageResource != 0) {
            setImageViewResource(LEFT_IMAGE_VIEW, leftImageResource);
        }
        if (leftTextIsBackEvent) {
            nv_leftTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) v.getContext()).finish();
                }
            });
        }

        if (centerText != null) {
            setText(CENTER_TEXT_VIEW, centerText);
        }
        if (centerImageResource != 0) {
            setImageViewResource(CENTER_IMAGE_VIEW, centerImageResource);
        }

        if (rightText != null) {
            setText(RIGHT_TEXT_VIEW, rightText);
        }
        if (rightTextDrawable != 0) {
            setTextViewDrawable(RIGHT_TEXT_VIEW, rightTextDrawable);
        }
        if (rightImageResource != 0) {
            setImageViewResource(RIGHT_IMAGE_VIEW, rightImageResource);
        }

        if(leftTextColor != 0){
            nv_leftTextView.setTextColor(getResources().getColor(leftTextColor));
        }
        if(centerTextColor != 0){
            nv_centerTextView.setTextColor(getResources().getColor(centerTextColor));
        }
        if(rightTextColor != 0){
            nv_rightTextView.setTextColor(getResources().getColor(rightTextColor));
        }
    }

    /**
     * 左侧view容器. 该view包含TextView和ImageView
     */
    public final static int LEFT_LAYOUT_CONTAINER = 1100;
    public final static int LEFT_TEXT_VIEW = 1110;
    public final static int LEFT_IMAGE_VIEW = 1120;

    /**
     * 右侧view容器. 该view包含TextView和ImageView
     */
    public final static int RIGHT_LAYOUT_CONTAINER = 2100;
    public final static int RIGHT_TEXT_VIEW = 2110;
    public final static int RIGHT_IMAGE_VIEW = 2120;

    /**
     * 中间view容器. 该view包含TextView和ImageView
     */
    public final static int CENTER_LAYOUT_CONTAINER = 3100;
    public final static int CENTER_TEXT_VIEW = 3110;
    public final static int CENTER_IMAGE_VIEW = 3120;

    @IntDef({LEFT_LAYOUT_CONTAINER, LEFT_TEXT_VIEW, LEFT_IMAGE_VIEW,
            RIGHT_LAYOUT_CONTAINER, RIGHT_TEXT_VIEW, RIGHT_IMAGE_VIEW,
            CENTER_LAYOUT_CONTAINER, CENTER_TEXT_VIEW, CENTER_IMAGE_VIEW})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ViewType {
    }

    @IntDef({LEFT_TEXT_VIEW, RIGHT_TEXT_VIEW, CENTER_TEXT_VIEW})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ViewType_TextView {
    }

    @IntDef({LEFT_IMAGE_VIEW, RIGHT_IMAGE_VIEW, CENTER_IMAGE_VIEW})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ViewType_ImageView {
    }

    /**
     * 获取view
     *
     * @param viewType
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@ViewType int viewType) {
        switch (viewType) {
            case LEFT_TEXT_VIEW:
                return (T) nv_leftTextView;
            case CENTER_TEXT_VIEW:
                return (T) nv_centerTextView;
            case RIGHT_TEXT_VIEW:
                return (T) nv_rightTextView;
            case LEFT_IMAGE_VIEW:
                return (T) nv_leftImageView;
            case CENTER_IMAGE_VIEW:
                return (T) nv_centerImageView;
            case RIGHT_IMAGE_VIEW:
                return (T) nv_rightImageView;
            case LEFT_LAYOUT_CONTAINER:
                return (T) nv_leftLayoutContainer;
            case CENTER_LAYOUT_CONTAINER:
                return (T) nv_centerLayoutContainer;
            case RIGHT_LAYOUT_CONTAINER:
                return (T) nv_rightLayoutContainer;
            default:
                return null;
        }
    }

    public NavigationView setText(@ViewType_TextView int viewType, String text) {
        switch (viewType) {
            case LEFT_TEXT_VIEW:
                nv_leftTextView.setText(text);
                break;
            case CENTER_TEXT_VIEW:
                nv_centerTextView.setText(text);
                //注释掉中间文字加粗属性
                //nv_centerTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                break;
            case RIGHT_TEXT_VIEW:
                nv_rightTextView.setText(text);
                break;
            default:
                break;
        }
        return this;
    }

    public NavigationView setText(@ViewType_TextView int view, @StringRes int resId) {
        return setText(view, getContext().getString(resId));
    }

    public NavigationView setTextViewDrawable(@ViewType_TextView int view, int resId) {
        Drawable drawable = getContext().getResources().getDrawable(resId);
        switch (view) {
            case LEFT_TEXT_VIEW:
                nv_leftTextView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                break;
            case CENTER_TEXT_VIEW:
                nv_centerTextView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                break;
            case RIGHT_TEXT_VIEW:
                nv_rightTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                break;
            default:
                break;
        }
        return this;
    }

    public NavigationView setImageViewResource(@ViewType_ImageView int view, @DrawableRes int resId) {
        switch (view) {
            case LEFT_IMAGE_VIEW:
                nv_leftImageView.setImageResource(resId);
                nv_leftImageView.setVisibility(VISIBLE);
                break;
            case CENTER_IMAGE_VIEW:
                nv_centerImageView.setImageResource(resId);
                nv_centerImageView.setVisibility(VISIBLE);
                break;
            case RIGHT_IMAGE_VIEW:
                nv_rightImageView.setImageResource(resId);
                nv_rightImageView.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
        return this;
    }

    public NavigationView setHeaderViewBackgroundColor(@ColorRes int color) {
        this.setBackgroundColor(getContext().getResources().getColor(color));
        return this;
    }

    public void hideView(@ViewType_ImageView int view){
        switch (view) {
            case LEFT_IMAGE_VIEW:
                nv_leftImageView.setVisibility(GONE);
                break;
            case CENTER_IMAGE_VIEW:
                nv_centerImageView.setVisibility(GONE);
                break;
            case RIGHT_IMAGE_VIEW:
                nv_rightImageView.setVisibility(GONE);
                break;
            default:
                break;
        }
    }
}
