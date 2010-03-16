package com.christophdietze.jack.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface PieceImageBundle extends ClientBundle {

	static PieceImageBundle INSTANCE = GWT.create(PieceImageBundle.class);

	public ImageResource bbishop();

	public ImageResource bking();

	public ImageResource bknight();

	public ImageResource bqueen();

	public ImageResource brook();

	public ImageResource bpawn();

	public ImageResource empty();

	public ImageResource wbishop();

	public ImageResource wking();

	public ImageResource wknight();

	public ImageResource wqueen();

	public ImageResource wrook();

	public ImageResource wpawn();

}
