package com.example.there.moviperfood.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.there.moviperfood.R;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageViewBindings {

    @BindingAdapter("imageUrl")
    public static void bindImageUrl(ImageView imageView, String url) {
        if (url != null && !url.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.loading_placeholder)
                            .error(R.drawable.thumbnail_placeholder))
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.thumbnail_placeholder);
        }
    }
}
