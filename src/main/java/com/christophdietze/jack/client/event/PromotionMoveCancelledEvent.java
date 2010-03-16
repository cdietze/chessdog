package com.christophdietze.jack.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PromotionMoveCancelledEvent extends GwtEvent<PromotionMoveCancelledEventHandler> {

	public static final Type<PromotionMoveCancelledEventHandler> TYPE = new Type<PromotionMoveCancelledEventHandler>();

	@Override
	public Type<PromotionMoveCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PromotionMoveCancelledEventHandler handler) {
		handler.onEvent(this);
	}
}
