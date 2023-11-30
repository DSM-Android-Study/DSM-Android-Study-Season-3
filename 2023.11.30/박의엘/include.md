# include 태그란?

자주 재사용되는 레이아웃이 있는 경우가 많음

→ 반복되는 레이아웃을 재활용할 수 있도록 만들어짐

### 사용법

1. 반복적으로 사용될 레이아웃을 별도 레이아웃 파일로 추출한다.

```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/black">
    <TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    android:text="Title Text"
    android:textColor="@color/white"/>
</FrameLayout>
```

1. 원하는 다른 레이아웃에 include 태그로 해당 레이아웃을 추가한다.

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <include layout="@layout/layout_title"/> <!-- 툴바 -->

    <TextView
        android:id="@+id/edit_text_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center"
        android:text="FirstActivity"/>
</LinearLayout>
```

이렇게 개발하면 레이아웃이 훨씬 간결해질 뿐 아니라, 일부가 변경되면 이 레이아웃 include된 모른 레이아웃에서 변경사항이 반영됨..!!

## 데이터 바인딩으로 include 응용하기

1. 먼저 include할 레이아웃에서 변경 가능한 부분에 대한 변수를 지정한다.

```xml
<layout>
    <data>
        <variable
            name="titleText"
            type="String" /> <!-- 변경 가능한 변수 -->
    </data>
    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/black">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="@{titleText == null ? `Default Title Text` : titleText}"
            android:textColor="@color/white"/> <!-- 여기에서 변수가 활용되고 있음 -->
    </FrameLayout>
</layout>
```

1. include하는 레이아웃에서 include 태그 내부로 해당 변수를 전달한다.

```xml
<layout>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <include layout="@layout/layout_title"
            app:titleText="@{`First Activity Title`}"/> <!-- 변수 활용 -->

        <TextView
            android:id="@+id/edit_text_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:text="First Activity"/>
    </LinearLayout>
</layout>
```

## <merge>

불필요한 뷰 계층을 줄이는데 사용된다. <include>태그를 사용하여 레이아웃을 포함시키면, 레이아웃의 최상의 뷰가 부모 뷰의 자식으로 추가된다.

→ 불필요한 뷰 계층이 생길 수 있음

따라서 <merge>태그를 사용하여 포함될 레이아웃의 최상위 뷰를 제거하고, 그 자식 뷰들을 직접 부모 뷰에 추가 할 수 있음

```xml
<merge xmlns:android="http://schemas.android.com/apk/res/android">
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button 1" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button 2" />
</merge>
```

### 콜라보레이션

레이아웃을 재사용하면서 불필요한 뷰 그룹 계층을 추가하지 않으려면 <include>태그와 <merge>태그를 함께 사용하는 것이 좋다

### 주의점

<include>선언시 명시했던 id가 사라진다. 따라서 <merge>를 xml은 중복 id가 발생할 수 있으니 한곳에서 <include>하는 상황을 피해야한다
