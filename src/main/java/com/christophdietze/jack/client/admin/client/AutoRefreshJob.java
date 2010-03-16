package com.christophdietze.jack.client.admin.client;

import java.util.ArrayList;

import com.christophdietze.jack.client.admin.common.AdminServiceAsync;
import com.christophdietze.jack.client.admin.common.MatchDto;
import com.christophdietze.jack.client.util.MyAsyncCallback;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ListBox;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AutoRefreshJob {

	private static final int POLL_INTERVAL = 5000;

	@Inject
	private AdminServiceAsync adminService;

	private AdminView adminView;

	private Timer timer;

	private boolean isActive = true;
	private int pendingAsyncCalls = 0;

	public AutoRefreshJob() {
	}

	public void attachToView(AdminView adminView) {
		this.adminView = adminView;
		init();
	}

	public void toggleActive() {
		if (isActive) {
			isActive = false;
		} else {
			isActive = true;
			timer.run();
		}
	}

	public boolean isActive() {
		return isActive;
	}

	private void init() {
		timer = new Timer() {
			@Override
			public void run() {
				if (!isActive) {
					// we were turned inactive while the timer was running
					return;
				}
				pendingAsyncCalls = 2;
				refreshSeekList();
				refreshMatchList();
			}
		};
		if (isActive) {
			timer.run();
		}
	}

	private void onAsyncCallReturned() {
		pendingAsyncCalls--;
		if (pendingAsyncCalls <= 0 && isActive) {
			timer.schedule(POLL_INTERVAL);
		}
	}

	private void refreshSeekList() {
		adminService.getSeekingUsers(new MyAsyncCallback<ArrayList<Long>>() {
			@Override
			public void onSuccess(ArrayList<Long> result) {
				ListBox seekBox = adminView.seekBox;
				seekBox.clear();
				for (Long item : result) {
					seekBox.addItem("User " + item.toString());
				}
				onAsyncCallReturned();
			}
		});
	}

	private void refreshMatchList() {
		adminService.getMatches(new MyAsyncCallback<ArrayList<MatchDto>>() {
			@Override
			public void onSuccess(ArrayList<MatchDto> result) {
				ListBox matchBox = adminView.matchBox;
				matchBox.clear();
				for (MatchDto match : result) {
					matchBox.addItem("User " + match.getUserA() + " vs " + "User " + match.getUserB());
				}
				onAsyncCallReturned();
			}
		});
	}
}
