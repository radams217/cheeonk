package com.cheeonk.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageResources extends ClientBundle
{
	public static final ImageResources INSTANCE = GWT.create(ImageResources.class);

	@Source("com/cheeonk/client/images/banner.png")
	public ImageResource getBanner();

	@Source("com/cheeonk/client/images/calendar.png")
	public ImageResource getCalendar();

	@Source("com/cheeonk/client/images/tab.png")
	public ImageResource getTab();

	@Source("com/cheeonk/client/images/reddot.png")
	public ImageResource getRedDot();

	@Source("com/cheeonk/client/images/greendot.png")
	public ImageResource getGreenDot();

	@Source("com/cheeonk/client/images/yellowdot.png")
	public ImageResource getYellowDot();

	@Source("com/cheeonk/client/images/graydot.png")
	public ImageResource getGrayDot();

	@Source("com/cheeonk/client/images/minimize.png")
	public ImageResource getMinimizeSquare();

	@Source("com/cheeonk/client/images/maximize.png")
	public ImageResource getMaximizeSquare();

	@Source("com/cheeonk/client/images/close.png")
	public ImageResource getCloseSquare();
}
