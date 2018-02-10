package com.rohailkabani.photoify;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        activateToolBar(true);

        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);

        if (photo != null) {
            Resources resources = getResources();

            TextView title = (TextView) findViewById(R.id.photoTitle);
            title.setText(resources.getString(R.string.photo_title_text, photo.getTitle()));

            TextView tags = (TextView) findViewById(R.id.photoTags);
            tags.setText(resources.getString(R.string.photo_title_tags, photo.getTags()));

            TextView author = (TextView) findViewById(R.id.photoAuthor);
            author.setText(resources.getString(R.string.photo_title_author, photo.getAuthor()));

            ImageView image = (ImageView) findViewById(R.id.photoImage);
            Picasso.with(this).load(photo.getLink())
                    .error(R.drawable.img_placeholder)
                    .placeholder(R.drawable.img_placeholder)
                    .into(image);

        }

    }

}
