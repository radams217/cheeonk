package com.ryannadams.cheeonk.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageResources extends ClientBundle
{
	public static final ImageResources INSTANCE = GWT.create(ImageResources.class);

	@Source("com/ryannadams/cheeonk/client/images/banner.png")
	public ImageResource getBanner();

	@Source("com/ryannadams/cheeonk/client/images/calendar.png")
	public ImageResource getCalendar();

	@Source("com/ryannadams/cheeonk/client/images/tab.png")
	public ImageResource getTab();

	@Source("com/ryannadams/cheeonk/client/images/reddot.png")
	public ImageResource getRedDot();

	@Source("com/ryannadams/cheeonk/client/images/greendot.png")
	public ImageResource getGreenDot();

	@Source("com/ryannadams/cheeonk/client/images/yellowdot.png")
	public ImageResource getYellowDot();

	@Source("com/ryannadams/cheeonk/client/images/graydot.png")
	public ImageResource getGrayDot();
}
