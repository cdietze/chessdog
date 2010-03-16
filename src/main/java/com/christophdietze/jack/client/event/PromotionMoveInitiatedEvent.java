package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PromotionMoveInitiatedEvent extends GwtEvent<PromotionMoveInitiatedEventHandler> {

	public static final Type<PromotionMoveInitiatedEventHandler> TYPE = new Type<PromotionMoveInitiatedEventHandler>();

	private int from;
	private int to;

	public PromotionMoveInitiatedEvent(int from, int to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public Type<PromotionMoveInitiatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PromotionMoveInitiatedEventHandler handler) {
		handler.onEvent(this);
	}

	public int getFromIndex() {
		return from;
	}

	public int getToIndex() {
		return to;
	}

}
