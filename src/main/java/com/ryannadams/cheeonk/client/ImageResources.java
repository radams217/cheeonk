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

}
