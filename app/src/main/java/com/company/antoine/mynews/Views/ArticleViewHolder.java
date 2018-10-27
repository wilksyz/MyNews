package com.company.antoine.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.company.antoine.mynews.Models.Headline;
import com.company.antoine.mynews.Models.MediaMetadatum;
import com.company.antoine.mynews.Models.Medium;
import com.company.antoine.mynews.Models.Multimedium;
import com.company.antoine.mynews.Models.Result;
import com.company.antoine.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticleViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.recycler_image_view) ImageView imageView;
    @BindView(R.id.recycler_text_view_date) TextView dateView;
    @BindView(R.id.recycler_text_view_section) TextView sectionView;
    @BindView(R.id.recycler_text_view_title) TextView tittleView;
    private String date;

    ArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateArticleListView(Result article, RequestManager pGlide, int positionViewPager){
        formatDate(article, positionViewPager);
        sectionView.setText(article.getSection());
        displayTittle(article, positionViewPager);
        String url = imageRequest(article, positionViewPager);
        if (url != null){
            imageView.setVisibility(View.VISIBLE);
        }
        pGlide.load(url).into(imageView);
    }

    private void formatDate(Result article, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat str = new SimpleDateFormat("dd-mm-yyyy");
        if (position != 2 && article.getPublishedDate() != null){
            try {
                Date today = sdf.parse(article.getPublishedDate());
                date = str.format(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            try {
                Date today = sdf.parse(article.getPubDate());
                date = str.format(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dateView.setText(date);
    }

    private String imageRequest(Result article, int positionViewPager){
        List<Medium> mediumList = article.getMedia();
        List<Multimedium> multimediumList = article.getMultimedia();
        imageView.setVisibility(View.GONE);

        String url = null;
        if(mediumList != null && positionViewPager == 1){
            List<MediaMetadatum> mediaMetadatumList = mediumList.get(0).getMediaMetadata();
            if (mediaMetadatumList.size() != 0)
             url = mediaMetadatumList.get(0).getUrl();
        }
        if (multimediumList != null && positionViewPager == 0){
            if (multimediumList.size() != 0){
                url = multimediumList.get(0).getUrl();
            }
        }
        if (multimediumList != null && positionViewPager == 2){
            if (multimediumList.size() != 0){
                url ="https://static01.nyt.com/"+multimediumList.get(searchImage(multimediumList)).getUrl();
            }
        }
        return url;
    }

    private void displayTittle(Result article, int position){
        if (position != 2){
            tittleView.setText(article.getTitle());
        }else{
            Headline title = article.getHeadline();
            tittleView.setText(title.getMain());
        }
    }

    private int searchImage(List<Multimedium> multimediumList){
        int p = 0;
        int h = 0;
        int w = 0;
        while (h != 75 && w != 75){
            h = multimediumList.get(p).getHeight();
            w = multimediumList.get(p).getWidth();
            p++;
        }
        p--;
        return p;
    }
}
