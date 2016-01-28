# NineGridView
类似控件和朋友圈展示图片的九宫格控件，自动根据图片的数量确定图片大小和控件大小

## 演示
 ![image](https://github.com/jeasonlzy0216/NineGridView/blob/master/screenshots/demo1.gif)


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
      <td align="center">imageWidth1</td>
      <td align="center">只显示一张图片时的宽度</td>
    </tr>
    <tr>
      <td align="center">imageHeight1</td>
      <td align="center">只显示一张图片时的高度/td>
    </tr>
    <tr>
      <td align="center">imageWidth2</td>
      <td align="center">显示两张图片时的宽度</td>
    </tr>
    <tr>
      <td align="center">imageHeight2</td>
      <td align="center">显示两张图片时的高度</td>
    </tr>
    <tr>
      <td align="center">imageWidth3</td>
      <td align="center">显示三张以上图片时的宽度</td>
    </tr>
    <tr>
      <td align="center">imageHeight3</td>
      <td align="center">显示三张以上图片时的高度</td>
    </tr>
    <tr>
      <td align="center">imageSpace</td>
      <td align="center">图片之间的间距</td>
    </tr>
  </tbody>
</table>

## 代码演示
```java
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (NineGridView) findViewById(R.id.container);
    }

    public void onClick(View view) {
        ImageView imageView = new ImageView(this);
        images.add(imageView);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
        imageView.setImageDrawable(getResources().getDrawable(resId[i % 9]));
        container.addView(imageView);
	}
```
