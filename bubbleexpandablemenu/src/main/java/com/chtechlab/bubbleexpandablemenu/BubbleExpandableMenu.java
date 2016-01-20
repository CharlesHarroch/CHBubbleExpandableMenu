package com.chtechlab.bubbleexpandablemenu;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import static com.chtechlab.bubbleexpandablemenu.Tools.roundUp;

/**
 * Created by Charles on 19/01/16.
 */
public class BubbleExpandableMenu extends RelativeLayout {

    private int CLOSE_MARGIN_BOTTOM = 100;
    private int BUBBLE_MARGIN_TOP = 100;
    private int BUBBLE_MARGIN_LEFT = 250;

    private View alphaView;
    private RelativeLayout bubbleContainer;
    private ScrollView scrollView;
    private RelativeLayout rootContainer;

    private View _root;
    private int animationDuration = 200;
    private ArrayList<RelativeLayout> items;
    private RelativeLayout closeBubble;

    private CGSize closeBubbleSize = new CGSize(80, 80);
    private CGSize bubbleSize = new CGSize(100, 100);

    private Point initialCloseMarginPosition;
    private Point initialMarginPosition;

    private OnBubbleMenuListener menuListener;
    private boolean initialized = false;

    public BubbleExpandableMenu(Context context) {
        super(context);
        init();
    }

    public BubbleExpandableMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BubbleExpandableMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        _root = View.inflate(getContext(), R.layout.bubble_expandable_menu_custom, this);

        bubbleContainer = (RelativeLayout)_root.findViewById(R.id.bubble_container);
        scrollView = (ScrollView)_root.findViewById(R.id.bubble_scrollview);
        rootContainer = (RelativeLayout)_root.findViewById(R.id.bubble_root_layout);
        alphaView = _root.findViewById(R.id.bubble_alpha_view);

        CLOSE_MARGIN_BOTTOM = (int)Tools.convertDpToPixel(30, getContext());
        BUBBLE_MARGIN_TOP = (int)Tools.convertDpToPixel(40, getContext());
        BUBBLE_MARGIN_LEFT = (int)Tools.convertDpToPixel(80, getContext());

        items = new ArrayList<>();

        initialCloseMarginPosition = new Point();
        initialMarginPosition = new Point();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!initialized) {
            initializeBubbleContainer();
        }
    }

    public void show() {
        openAnimation();
    }

    private void initializeBubbleContainer() {
        initialized = true;

        int lineCount = (int)roundUp(items.size(), 2);
        int bubbleContainerHeight = (lineCount*(bubbleSize.getHeight() + BUBBLE_MARGIN_TOP)) + bubbleSize.getHeight();
        bubbleContainerHeight = bubbleContainerHeight + BUBBLE_MARGIN_TOP;
        if (bubbleContainerHeight < scrollView.getHeight()) {
            bubbleContainerHeight = scrollView.getHeight();
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, bubbleContainerHeight);
        bubbleContainer.setLayoutParams(params);

        // Set initial position
        initialCloseMarginPosition.x = 0;
        initialCloseMarginPosition.y = rootContainer.getHeight() + closeBubbleSize.getHeight();

        initialMarginPosition.x = (rootContainer.getWidth()/2) - (bubbleSize.getWidth()/2);
        initialMarginPosition.y = bubbleContainerHeight - bubbleSize.getHeight();

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        addCloseBubble();
        addBubbleToPosition(rootContainer, closeBubble, 0, initialCloseMarginPosition.y, 0, 0);

        for (RelativeLayout bubble : items) {
            addBubbleToPosition(bubbleContainer, bubble, initialMarginPosition.x, initialMarginPosition.y, 0, 0);
        }
    }

    public void moveBubbleWithPosition(final RelativeLayout bubble, int newX, int newY, boolean hide) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bubble.getLayoutParams();
        TranslateAnimation animation = makeYAnimation(bubble, params.leftMargin, newX, params.topMargin, newY, hide);
        bubble.startAnimation(animation);
    }

    private void openAnimation() {
        // Move close bubble
        moveBubbleWithPosition(closeBubble, 0, rootContainer.getHeight() - closeBubbleSize.getHeight() - CLOSE_MARGIN_BOTTOM, false);
        alphaView.animate().alpha(0.5f).setDuration(animationDuration);

        int x = 1;
        int y = 1;
        int initialMarginLeft;
        int initialMarginTop = bubbleContainer.getHeight() - bubbleSize.getHeight()*2 - BUBBLE_MARGIN_TOP;

        for (RelativeLayout bubble : items) {
            if (x % 2 != 0) {
                initialMarginLeft = initialMarginPosition.x - BUBBLE_MARGIN_LEFT;
            } else {
                initialMarginLeft = initialMarginPosition.x + BUBBLE_MARGIN_LEFT;
            }
            bubble.setVisibility(VISIBLE);
            if (x == items.size() && items.size()%2 != 0) {
                initialMarginLeft = initialMarginPosition.x;
            }
            moveBubbleWithPosition(bubble, initialMarginLeft, initialMarginTop, false);

            x++;
            if (x % 2 != 0) {
                y++;
                initialMarginTop = (bubbleContainer.getHeight() - bubbleSize.getHeight()) - ((bubbleSize.getHeight()+BUBBLE_MARGIN_TOP) * y);
            }
        }

        if (menuListener != null) {
            menuListener.openMenu();
        }
    }

    public void closeAnimation() {
        // Move close bubble
        moveBubbleWithPosition(closeBubble, 0, initialCloseMarginPosition.y, false);
        alphaView.animate().alpha(0.0f).setDuration(animationDuration);

        for (RelativeLayout bubble : items) {
            moveBubbleWithPosition(bubble, initialMarginPosition.x, initialMarginPosition.y, true);
        }

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        if (menuListener != null)
            menuListener.closeMenu();
    }

    public void addBubbleToPosition(RelativeLayout container, RelativeLayout bubble, int leftMagin, int topMargin, int rightMargin, int bottomMargin) {

        RelativeLayout.LayoutParams params;

        if (bubble.equals(closeBubble)) {
            params = new RelativeLayout.LayoutParams(closeBubbleSize.getWidth(), closeBubbleSize.getHeight());
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else {
            params = new RelativeLayout.LayoutParams(bubbleSize.getWidth(), bubbleSize.getHeight());
            bubble.setVisibility(GONE);
        }
        container.addView(bubble);
        bubble.setLayoutParams(params);
        setLeftMargin(bubble, leftMagin);
        setTopMargin(bubble, topMargin);
    }



    public void addCloseBubble() {

        closeBubble = new RelativeLayout(getContext());
        closeBubble.setBackground(getResources().getDrawable(R.drawable.circle_image_background));
        closeBubble.setAlpha(0.5f);

        // Add image Background
        ImageView image = new ImageView(getContext());
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageParams.setMargins(15, 15, 15, 15);
        image.setLayoutParams(imageParams);
        image.setImageResource(R.mipmap.menu_bubble_close);
        closeBubble.addView(image);

        closeBubble.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnimation();
            }
        });
    }

    public void addItem(int resId, Object tag) {

        RelativeLayout currentItem = new RelativeLayout(getContext());
        currentItem.setBackground(getResources().getDrawable(R.drawable.circle_image_background));
        currentItem.setTag(tag);

        // Add image Background
        ImageView image = new ImageView(getContext());
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageParams.setMargins(5,5,5,5);
        image.setLayoutParams(imageParams);
        image.setImageResource(resId);
        currentItem.addView(image);

        currentItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuListener != null)
                    menuListener.bubbleItemMenuClicked(v.getTag());
                closeAnimation();
            }
        });

        items.add(currentItem);
    }

    private TranslateAnimation makeYAnimation(final RelativeLayout bubble,  final int fromMarginLeft, final int toMarginLeft,final int fromMarginTop, final int toMarginTop, final boolean hide)
    {
        TranslateAnimation animation = new TranslateAnimation(0, toMarginLeft - fromMarginLeft, 0, toMarginTop - fromMarginTop);
        animation.setDuration(animationDuration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                bubble.clearAnimation();
                setTopMargin(bubble, toMarginTop);
                setLeftMargin(bubble, toMarginLeft);
                if (hide) {
                    bubble.setVisibility(GONE);
                }
            }

            public void onAnimationStart(Animation animation) {
                bubble.setVisibility(VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        return animation;
    }

    private void setLeftMargin(View view, int leftMargin)
    {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        layoutParams.leftMargin = leftMargin;
        view.requestLayout();
    }

    private void setTopMargin(View view, int topMargin)
    {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        layoutParams.topMargin = topMargin;
        view.requestLayout();
    }

    /**
     * If you need to do something on closing, opening menu or on bubble selection,
     * set a listener here.
     *
     * @return
     */
    public void setMenuListener(OnBubbleMenuListener menuListener) {
        this.menuListener = menuListener;
    }


    public OnBubbleMenuListener getMenuListener() {
        return menuListener;
    }


    public interface OnBubbleMenuListener {
        public void openMenu();
        public void closeMenu();
        public void bubbleItemMenuClicked(Object tag);
    }

    private class CGSize {
        int _height;
        int _width;

        public CGSize(int width, int height) {
            _height = height;
            _width = width;
        }

        public int getHeight() {
            return (int)Tools.convertDpToPixel(_height, getContext());
        }

        public int getWidth() {
            return (int)Tools.convertDpToPixel(_width, getContext());
        }
    }
}