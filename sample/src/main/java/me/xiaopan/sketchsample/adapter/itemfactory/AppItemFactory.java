package me.xiaopan.sketchsample.adapter.itemfactory;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.xiaopan.assemblyadapter.AssemblyRecyclerItem;
import me.xiaopan.assemblyadapter.AssemblyRecyclerItemFactory;
import me.xiaopan.sketch.SketchImageView;
import me.xiaopan.sketch.shaper.ImageShaper;
import me.xiaopan.sketch.shaper.RoundRectImageShaper;
import me.xiaopan.sketchsample.ImageOptions;
import me.xiaopan.sketchsample.R;
import me.xiaopan.sketchsample.bean.AppInfo;
import me.xiaopan.sketchsample.widget.MyImageView;

public class AppItemFactory extends AssemblyRecyclerItemFactory<AppItemFactory.AppItem> {
    @Override
    public boolean isTarget(Object o) {
        return o instanceof AppInfo;
    }

    @Override
    public AppItem createAssemblyItem(ViewGroup viewGroup) {
        return new AppItem(R.layout.list_item_app, viewGroup);
    }

    public class AppItem extends AssemblyRecyclerItem<AppInfo> {
        @BindView(R.id.image_installedApp_icon)
        MyImageView iconImageView;
        @BindView(R.id.text_installedApp_name)
        TextView nameTextView;
        @BindView(R.id.text_installedApp_info)
        TextView infoTextView;

        public AppItem(int itemLayoutId, ViewGroup parent) {
            super(itemLayoutId, parent);
        }

        @Override
        protected void onFindViews() {
            ButterKnife.bind(this, getItemView());
        }

        @Override
        protected void onConfigViews(Context context) {
            iconImageView.setOptionsByName(ImageOptions.ROUND_RECT);

            ImageShaper imageShaper = iconImageView.getOptions().getImageShaper();
            if (imageShaper instanceof RoundRectImageShaper) {
                RoundRectImageShaper roundRectImageShaper = (RoundRectImageShaper) imageShaper;
                iconImageView.setImageShape(SketchImageView.ImageShape.ROUNDED_RECT);
                iconImageView.setImageShapeCornerRadius(roundRectImageShaper.getOuterRadii());
            }
        }

        @Override
        protected void onSetData(int i, AppInfo appInfo) {
            if (appInfo.isTempInstalled()) {
                iconImageView.displayInstalledAppIcon(appInfo.getId(), appInfo.getVersionCode());
            } else {
                iconImageView.displayImage(appInfo.getApkFilePath());
            }
            nameTextView.setText(appInfo.getName());
            infoTextView.setText(String.format("v%s  |  %s", appInfo.getVersionName(), appInfo.getAppSize()));
        }
    }
}
