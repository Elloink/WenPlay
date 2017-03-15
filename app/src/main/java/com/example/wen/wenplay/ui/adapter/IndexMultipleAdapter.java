package com.example.wen.wenplay.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.Banner;
import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.common.imageloader.ImageLoader;
import com.example.wen.wenplay.ui.decoration.DividerItemDecoration;
import com.example.wen.wenplay.ui.widget.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wen on 2017/3/14.
 */

public class IndexMultipleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public static final int TYPE_BANNER = 1;
    private static final int TYPE_ICON = 2;
    private static final int TYPE_APPS = 3;
    private static final int TYPE_GAMES = 4;



    private IndexBean mIndexBean;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public IndexMultipleAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else if (position == 1) {
            return TYPE_ICON;
        } else if (position == 2) {
            return TYPE_APPS;
        } else if (position == 3) {
            return TYPE_GAMES;
        }
        return 0;
    }

    public void setData(IndexBean indexBean) {
        this.mIndexBean = indexBean;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_BANNER == viewType) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.item_banner, parent, false));
        } else if (TYPE_ICON == viewType) {
            return new NavIconViewHolder(mLayoutInflater.inflate(R.layout.item_nav_icon, parent, false));
        } else if (TYPE_APPS == viewType) {
            return new AppViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_view_with_title, null, false), TYPE_APPS);
        } else if (TYPE_GAMES == viewType) {
            return new AppViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_view_with_title, null, false), TYPE_GAMES);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;

            List<Banner> banners = mIndexBean.getBanners();
            List<String> urls = new ArrayList<>(banners.size());

            for (Banner banner : banners) {

                urls.add(banner.getThumbnail());
            }

            bannerViewHolder.mBanner.setViewUrls(urls);

            bannerViewHolder.mBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
//                    banners.get(position)
                }
            });
        } else if (position == 1) {
            NavIconViewHolder navIconViewHolder = (NavIconViewHolder) holder;
            navIconViewHolder.mLayoutHotApp.setOnClickListener(this);
            navIconViewHolder.mLayoutHotGame.setOnClickListener(this);
            navIconViewHolder.mLayoutHotSubject.setOnClickListener(this);
        } else {
            AppViewHolder viewHolder = (AppViewHolder) holder;

            AppInfoAdapter appInfoAdapter = AppInfoAdapter.Builder().showCategoryName(false).showPosition(false).showBrief(true).build();

            if (viewHolder.type == TYPE_APPS) {
                viewHolder.mText.setText("热门应用");
                appInfoAdapter.addData(mIndexBean.getRecommendApps());
            } else if (viewHolder.type == TYPE_GAMES) {
                viewHolder.mText.setText("热门游戏");
                appInfoAdapter.addData(mIndexBean.getRecommendGames());
            }

            viewHolder.mRecyclerView.setAdapter(appInfoAdapter);
            int i = mIndexBean.getRecommendApps().size() + mIndexBean.getRecommendGames().size();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onClick(View v) {

    }


    class BannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        BannerLayout mBanner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBanner.setImageLoader(new ImgLoader());
        }
    }

    class NavIconViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_hot_app)
        LinearLayout mLayoutHotApp;
        @BindView(R.id.layout_hot_game)
        LinearLayout mLayoutHotGame;
        @BindView(R.id.layout_hot_subject)
        LinearLayout mLayoutHotSubject;

        public NavIconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    class AppViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.text)
        TextView mText;
        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;


        int type;

        public AppViewHolder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.type = type;

            initRecyclerView();


        }

        private void initRecyclerView() {

            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext) {
                //嵌套RecyclerView垂直方向不能滑动
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);

            mRecyclerView.addItemDecoration(itemDecoration);

        }
    }

    class ImgLoader implements BannerLayout.ImageLoader {

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoader.load(path, imageView);
        }
    }
}
