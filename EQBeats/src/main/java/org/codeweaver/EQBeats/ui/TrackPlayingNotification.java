package org.codeweaver.eqbeats.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import org.codeweaver.eqbeats.R;
import org.codeweaver.eqbeats.model.Track;

import java.io.IOException;

/**
 * Helper class for showing and cancelling track playing notifications.
 * <p>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way
 * </p>
 * Created by Berwyn Codeweaver on 30/06/13.
 */
public class TrackPlayingNotification {

	/**
	 * The unique identifier for this type of notification
	 */
	private static final String	NOTIFICATION_TAG	= "EQBeatsTrackPlaying";

	public static void notify(final Context context, final Track track,
			final RemoteViews bigContent) {

		new Thread() {
			@Override
			public void run() {

				final Resources res = context.getResources();

				// This image is used as the notification's large icon
				// (thumbnail).
				// TODO: Remove this if the notification has no relevant
				// thumbnail
				Bitmap _picture;
				try {
					if (track.getDownload().getArt() == null) {
						_picture = Picasso.with(context)
								.load(R.drawable.ic_filler_art)
								.resize(200, 200).centerCrop().get();
					} else {
						_picture = Picasso.with(context)
								.load(track.getDownload().getArt())
								.resize(200, 200).centerCrop().get();
					}
				} catch (IOException e) {
					_picture = BitmapFactory.decodeResource(res,
							R.drawable.ic_filler_art);
				}
				final Bitmap picture = _picture;

				final String ticker = res.getString(
						R.string.notification_track_playing_ticker,
						track.getTitle(), track.getArtist().getName());
				final String title = res.getString(
						R.string.notification_track_playing_title,
						track.getTitle());
				final String text = res.getString(
						R.string.notification_track_playing_subtext, track
								.getArtist().getName());
				final String summary = res
						.getString(R.string.notification_track_playing_bigtext_summary);

				final NotificationCompat.Builder builder = new NotificationCompat.Builder(
						context)//

						// Set the appropriate defaults for notification light,
						// sound,
						// and vibration
						.setDefaults(Notification.DEFAULT_ALL)//

						// Required fields
						.setSmallIcon(R.drawable.ic_stat_notification)//
						.setContentTitle(title)//
						.setContentText(text)//

						// Optional fields

						// Use default priority on 4.1 and later
						.setPriority(NotificationCompat.PRIORITY_MAX)//

						// Provide the large image shown in the drawer
						.setLargeIcon(picture)//

						// Set the ticker text
						.setTicker(ticker)//

						// Set this as ongoing
						.setOngoing(true)

						// Show expanded text content on devices running 4.1 or
						// later
						.setStyle(
								new NotificationCompat.BigTextStyle()
										.bigText(text)
										.setBigContentTitle(title)
										.setSummaryText(summary))//

						// Set the Actions for 4.1+ devices
						// TODO: Add Play/Pause, and Next actions

						// Don't dismiss the notification when it's touched
						.setAutoCancel(false);

				Notification notif = builder.build();

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					notif.bigContentView = bigContent;
					bigContent.setCharSequence(R.id.notification_title,
							"setText", title);
					bigContent.setCharSequence(R.id.notification_artist,
							"setText", text);
					bigContent.setBitmap(R.id.notification_art,
							"setImageBitmap", picture);
				}

				TrackPlayingNotification.notify(context, notif);
			}
		}.start();
	}

	private static void notify(final Context context,
			final Notification notification) {
		final NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(NOTIFICATION_TAG, 0, notification);
	}

	public static void cancel(final Context context) {
		final NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(NOTIFICATION_TAG, 0);
	}

}
