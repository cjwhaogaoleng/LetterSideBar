# LetterSideBar
字母索引栏

 ## 效果展示

https://github.com/cjwhaogaoleng/LetterSideBar/assets/117556474/6f6315f5-3c50-4449-b447-92936bd7a35e

 ## 源码位置
/app/src/main/java/com/example/lettersidebar/LetterSideBar.kt

 ## 代码讲解
  ### JAVA
  #### 实现方法
  xml布局中
```
 //设置一个textView实现屏幕中间绘制字母的效果

<TextView
        android:id="@+id/letter_tv"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_centerInParent="true"
        android:background="@color/red"
        android:gravity="center"
        android:visibility="gone"
        android:text="A"
        android:textSize="30dp"
        />
    <com.example.lettersidebar.LetterSideBar
        android:id="@+id/letter_bar"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_alignParentEnd="true"
        android:padding="10dp"
        />
```
Activity中
```
letterSideBar.setLetterTouchListener(this);

//重写touch方法即可
@Override
    public void touch(char letter, boolean ifTouch) {
        if (ifTouch) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(letter));
        } else {
            textView.setVisibility(View.GONE);
        }
    }
```

 ## 待完成
 - [x] 自定义view
   - [x] onMeasure 源码和写法基本了解
   - [x] onDraw 源码和写法基本了解
   - [x] onTouch 触碰分发事件正在学习
 - [ ] compose 已经接触，还没有另一种熟练
 - [ ] :disappointed: :blush:


 
 https://github.com/verticaldraglistview/VerticalDragListView.git

 https://github.com/cjwhaogaoleng/FlowLayout.git

