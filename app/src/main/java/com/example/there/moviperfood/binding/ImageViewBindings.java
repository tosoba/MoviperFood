package com.example.there.moviperfood.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.there.moviperfood.R;
import com.squareup.picasso.Picasso;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageViewBindings {

    @BindingAdapter("imageUrl")
    public static void bindImageUrl(ImageView imageView, String url) {
        if (url != null && !url.isEmpty()) {
            Picasso.with(imageView.getContext())
                    .load(url)
                    .error(R.drawable.thumbnail_placeholder)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.thumbnail_placeholder);
        }
    }
}
