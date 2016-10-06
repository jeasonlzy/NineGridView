# NineGridView
类似QQ空间，微信朋友圈，微博主页等，展示图片的九宫格控件，自动根据图片的数量确定图片大小和控件大小，使用Adapter模式设置图片，对外提供接口回调，支持任意的图片加载框架,如 Glide,ImageLoader,Fresco,xUtils3,Picasso 等，支持点击图片全屏预览大图。


该项目是根据：[https://github.com/laobie/NineGridImageView](https://github.com/laobie/NineGridImageView) 修改而成，进行了优化扩展，使代码更加简单，喜欢原作的可以去使用。同时欢迎大家下载体验本项目，如果使用过程中遇到什么问题，欢迎反馈。

### 联系方式
 * 邮箱地址： liaojeason@126.com
 * QQ群： 489873144 （建议使用QQ群，邮箱使用较少，可能看的不及时）
 * 本群刚建立，旨在为使用我的github项目的人提供方便，如果遇到问题欢迎在群里提问。个人能力也有限，希望一起学习一起进步。


## 演示
 ![image](https://github.com/jeasonlzy/Screenshots/blob/master/NineGridView/demo9.png) ![image](https://github.com/jeasonlzy/Screenshots/blob/master/NineGridView/demo10.gif) ![image](https://github.com/jeasonlzy/Screenshots/blob/master/NineGridView/demo3.png) ![image](https://github.com/jeasonlzy/Screenshots/blob/master/NineGridView/demo14.gif) ![image](https://github.com/jeasonlzy/Screenshots/blob/master/NineGridView/demo12.png)![image](https://github.com/jeasonlzy/Screenshots/blob/master/NineGridView/demo8.png)

## 1.用法
使用前，对于Android Studio的用户，可以选择添加:
```java
	compile 'com.lzy.widget:ninegridview:0.2.0'
```
或者使用
```java
    compile project(':ninegridview')
```

## 2.项目功能
 * 使用Adapter模式设置图片
 * 当图片数量只有一张时，自动根据图片大小调整控件大小
 * 默认增加了图片点击全屏预览效果，并附带预览动画
 * 使用接口加载图片,支持任意的图片加载框架,如 Glide,ImageLoader,Fresco,xUtils3,Picasso 等
 * 整合了PhotoView图片预览
 * 使用接口抽出图片的加载方式，可以方便的将Glide替换成自己喜欢的ImageLoader等
 * 支持fill个grid两种显示模式
 * 当获取的图片数量超过最大显示的图片数量时，最后一张图片上会显示剩余数量（类似于QQ的动态效果）
 * 使用代码简单，只需要几行代码
 * 其他功能增加中......

## 3.参数含义

<table>
  <tdead>
    <tr>
      <th align="center">自定义属性名字</th>
      <th align="center">参数含义</th>
    </tr>
  </tdead>
  <tbody>
    <tr>
      <td align="center">ngv_singleImageSize</td>
      <td align="center">只显示一张图片时的最大图片大小</td>
    </tr>
    <tr>
      <td align="center">ngv_singleImageRatio</td>
      <td align="center">只显示一张图片时图片宽高比</td>
    </tr>
    <tr>
      <td align="center">ngv_gridSpacing</td>
      <td align="center">网格显示图片时，图片之间的间距，默认3dp</td>
    </tr>
    <tr>
      <td align="center">ngv_maxSize</td>
      <td align="center">最多显示图片的数量，默认最大9张</td>
    </tr>
    <tr>
      <td align="center">ngv_mode</td>
      <td align="center">支持fill和grid两种显示模式，其中grid模式在显示4张图片时采用2*2的布局</td>
    </tr>
     </tbody>
</table>

## 4.代码演示
### 1.在Application中初始化NineGridView的图片加载器
```java
    NineGridView.setImageLoader(new PicassoImageLoader());

    /** Picasso 加载 */
    private class PicassoImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Picasso.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_image)//
                    .error(R.drawable.ic_default_image)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
```
### 2.在自己的Adapter中初始化NineGridView的适配器
 * `ImageInfo`是库中提供的数据Bean，需要两个url，分别表示小图和大图的url，没有大图或者小图，则都赋给相同的Url即可。
 * `ClickNineGridViewAdapter`是库中提供的默认实现了点击预览的Adapter，如果不想使用预览效果，可以自己继承 `NineGridViewAdapter` 实现其中 `onDisplayImage` 方法即可。
```java
	ArrayList<ImageInfo> imageInfo = new ArrayList<>();
    List<EvaluationPic> imageDetails = item.getAttachments();
    if (imageDetails != null) {
        for (EvaluationPic imageDetail : imageDetails) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(imageDetail.smallImageUrl);
            info.setBigImageUrl(imageDetail.imageUrl);
            imageInfo.add(info);
        }
    }
    holder.nineGrid.setAdapter(new ClickNineGridViewAdapter(context, imageInfo));
```
