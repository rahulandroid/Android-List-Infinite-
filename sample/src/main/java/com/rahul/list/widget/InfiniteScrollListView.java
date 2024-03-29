package com.rahul.list.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 //Developed by Rahul Mane https://github.com/rahulandroid
 * A ListView   infinite scrolling capability, with a customizable view
 * to be displayed at the top/bottom of the list as the loading indicator
 */
public class InfiniteScrollListView extends ListView implements InfiniteScrollListPageListener {

	public static final String TAG = InfiniteScrollListView.class.getSimpleName();
	// Two conditions allowing loading to happen: either the list view scrolls to the top or bottom
	public static enum LoadingMode {SCROLL_TO_TOP, SCROLL_TO_BOTTOM};
	// Decides where the ending position: either scrolls up to the start of the list, scrolls down to the 
	// end of the list or where remains where it was
	public static enum StopPosition {START_OF_LIST, END_OF_LIST, REMAIN_UNCHANGED};

	private View loadingView;
	private LoadingMode loadingMode = LoadingMode.SCROLL_TO_BOTTOM; 
	private StopPosition stopPosition = StopPosition.REMAIN_UNCHANGED;

	// A flag to prevent loading header or footer more than once
	private boolean loadingViewVisible = false;

    public InfiniteScrollListView(Context context) {
        super(context);
    }

    public InfiniteScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InfiniteScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public void setLoadingView(View loadingView) {
		this.loadingView = loadingView;
	}

    public void setLoadingMode(LoadingMode loadingMode) {
    	this.loadingMode = loadingMode;
    }
    
    public void setStopPosition(StopPosition stopPosition) {
		this.stopPosition = stopPosition;
	}

    private void addLoadingView(ListView listView, View loadingView) {
    	if (listView == null || loadingView == null) {
    		return;
    	}
    	// Avoid overlapping the header or footer
    	if (!loadingViewVisible) {
			if (loadingMode == LoadingMode.SCROLL_TO_TOP) {
				// Add loading view to list view header when scroll up to load
				listView.addHeaderView(loadingView);
			} else {
				// Add loading view to list view footer when scroll down to load
				listView.addFooterView(loadingView);
			}
			loadingViewVisible = true;
    	}
    }

    private void removeLoadingView(ListView listView, View loadingView) {
    	if (listView == null || loadingView == null) {
    		return;
    	}
    	// Remove header or footer depending on the loading mode
    	if (loadingViewVisible) {
	    	if (loadingMode == LoadingMode.SCROLL_TO_TOP) {
				listView.removeHeaderView(loadingView);
			} else {
				listView.removeFooterView(loadingView);
			}
	    	loadingViewVisible = false;
    	}
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
    	// Force the list view to accept its own type of adapter
    	if (!(adapter instanceof InfiniteScrollListAdapter)) {
    		throw new IllegalArgumentException(InfiniteScrollListAdapter.class.getSimpleName() + " expected");
    	}
    	// Pass information to adaptor
    	InfiniteScrollListAdapter infiniteListAdapter = (InfiniteScrollListAdapter) adapter;
    	infiniteListAdapter.setLoadingMode(loadingMode);
    	infiniteListAdapter.setStopPosition(stopPosition);
    	infiniteListAdapter.setInfiniteListPageListener(this);
		this.setOnScrollListener(infiniteListAdapter);
		// Workaround to keep spaces for header and footer
		View dummy = new View(getContext());
		addLoadingView(InfiniteScrollListView.this, dummy);
    	super.setAdapter(adapter);
    	removeLoadingView(InfiniteScrollListView.this, dummy);
    }

	@Override
	public void endOfList() {
		// Remove loading view when there is no more to load
		removeLoadingView(this, loadingView);
	}

	@Override
	public void hasMore() {
		// Display loading view when there might be more to load
		addLoadingView(InfiniteScrollListView.this, loadingView);
	}

}