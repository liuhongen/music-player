package com.example.musicplayer;

import java.util.Formatter;
import java.util.Locale;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;

public class MusicUtils {
	
	
	private static StringBuilder sFormatBuilder = new StringBuilder();
    private static Formatter sFormatter = new Formatter(sFormatBuilder, Locale.getDefault());

	public static String makeAlbumsLabel(Context context, int numalbums,
			int numsongs, boolean isUnknown) {
		// There are two formats for the albums/songs information:
		// "N Song(s)" - used for unknown artist/album
		// "N Album(s)" - used for known albums

		StringBuilder songs_albums = new StringBuilder();

		Resources r = context.getResources();
		if (isUnknown) {
			if (numsongs == 1) {
				songs_albums.append(context.getString(R.string.onesong));
			} else {
				String f = r.getQuantityText(R.plurals.Nsongs, numsongs)
						.toString();
				sFormatBuilder.setLength(0);
				sFormatter.format(f, Integer.valueOf(numsongs));
				songs_albums.append(sFormatBuilder);
			}
		} else {
			String f = r.getQuantityText(R.plurals.Nalbums, numalbums)
					.toString();
			sFormatBuilder.setLength(0);
			sFormatter.format(f, Integer.valueOf(numalbums));
			songs_albums.append(sFormatBuilder);
			songs_albums.append(context.getString(R.string.albumsongseparator));
		}
		return songs_albums.toString();
	}
	
	public static Cursor query(Context context, Uri uri, String[] projection,
            String selection, String[] selectionArgs, String sortOrder, int limit) {
        try {
            ContentResolver resolver = context.getContentResolver();
            if (resolver == null) {
                return null;
            }
            if (limit > 0) {
                uri = uri.buildUpon().appendQueryParameter("limit", "" + limit).build();
            }
            return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
         } catch (UnsupportedOperationException ex) {
            return null;
        }
        
    }
	
	public static Cursor query(Context context, Uri uri, String[] projection,
            String selection, String[] selectionArgs, String sortOrder) {
        return query(context, uri, projection, selection, selectionArgs, sortOrder, 0);
    }

}
