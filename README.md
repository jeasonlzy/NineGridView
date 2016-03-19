# NineGridView
类似QQ空间，微信朋友圈，微博主页等，展示图片的九宫格控件，自动根据图片的数量确定图片大小和控件大小，使用Adapter模式设置图片，对外提供接口回调。


该项目是根据：[https://github.com/laobie/NineGridImageView](https://github.com/laobie/NineGridImageView) 修改而成，进行了优化扩展，使代码更加简单，同时增加了只有一张图片时的特殊显示效果，喜欢原作的可以去使用。同时欢迎大家下载体验本项目，如果使用过程中遇到什么问题，欢迎反馈。

## 演示
 ![image](https://github.com/jeasonlzy0216/NineGridView/blob/master/screenshots/demo1.png) ![image](https://github.com/jeasonlzy0216/NineGridView/blob/master/screenshots/demo2.gif) ![image](https://github.com/jeasonlzy0216/NineGridView/blob/master/screenshots/demo3.png) ![image](https://github.com/jeasonlzy0216/NineGridView/blob/master/screenshots/demo4.png) ![image](https://github.com/jeasonlzy0216/NineGridView/blob/master/screenshots/demo5.png)


## 1.参数含义

<table>
  <tdead>
    <tr>
      <th align="center">自定义属性名字</th>
      <th align="center">参数含义</th>
    </tr>
  </tdead>
  <tbody>
    <tr>
      <td align="center">singleImageSize</td>
      <td align="center">只显示一张图片时的最大图片大小</td>
    </tr>
    <tr>
      <td align="center">singleImageRatio</td>
      <td align="center">只显示一张图片时图片宽高比/td>
    </tr>
    <tr>
      <td align="center">singleImageScaleType</td>
      <td align="center">只显示一张图片时图片的缩放模式</td>
    </tr>
    <tr>
      <td align="center">gridSpacing</td>
      <td align="center">网格显示图片时，图片之间的间距，默认3dp</td>
    </tr>
    <tr>
      <td align="center">maxSize</td>
      <td align="center">最多显示图片的数量，默认最大9张</td>
    </tr>
     </tbody>
</table>

## 代码演示
每条数据做如下布局：
```xml
	<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:app="http://schemas.android.com/apk/res-auto"
          xmlns:tools="http://schemas.android.com/tools"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:layout_margin="8dp"
          android:background="#FFF"
          android:orientation="vertical"
          android:padding="8dp">
	
	    <TextView
	        android:id="@+id/title"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:layout_marginBottom="8dp"
	        android:textColor="#333333"
	        android:textSize="16sp"
	        tools:text="测试文字"/>
	
	    <com.lzy.ui.NineGridView
	        android:id="@+id/nineGrid"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        app:gridSpacing="3dp"
	        app:maxSize="9"
	        app:singleImageRatio="1"
	        app:singleImageScaleType="fit_start"
	        app:singleImageSize="250dp"/>
	</LinearLayout>
```
每个 NineGridView的Adapter代码如下：
```java
	public class DefaultNineGridViewAdapter extends NineGridViewAdapter<ImageDetail> {
	
	    public DefaultNineGridViewAdapter(List<ImageDetail> list) {
	        super(list);
	    }
	
	    @Override
	    protected ImageView generateImageView(Context context) {
	        return super.generateImageView(context);
	    }
	
	    @Override
	    protected void onDisplayImage(Context context, ImageView imageView, ImageDetail imageDetail) {
	        Glide.with(context)//
	                .load(imageDetail.getUrl())//
	                .placeholder(R.mipmap.ic_default_image)//
	                .diskCacheStrategy(DiskCacheStrategy.ALL)//
	                .into(imageView);
	    }
	
	    @Override
	    protected void onImageItemClick(Context context, int index, List<ImageDetail> list) {
	        Toast.makeText(context, "图片条目：" + index, Toast.LENGTH_SHORT).show();
	    }
}
```

使用ListView展示数据时，代码如下：
```java
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    if (convertView == null) {
	        convertView = View.inflate(getApplicationContext(), R.layout.item_grid, null);
	    }
	    ViewHolder holder = ViewHolder.getHolder(convertView);
	    DetailNews item = getItem(position);
	    holder.title.setText(item.getTitle());
	    List<ImageDetail> imageDetails = item.getImageDetails();
	    holder.nineGrid.setAdapter(new DefaultNineGridViewAdapter(imageDetails));
	    if (imageDetails.size() == 1) {
	        holder.nineGrid.setSingleImageScaleType(ImageView.ScaleType.FIT_START);
	        holder.nineGrid.setSingleImageRatio(imageDetails.get(0).getWidth() * 1.0f / imageDetails.get(0).getHeight());
	    }
	    return convertView;
	}
```