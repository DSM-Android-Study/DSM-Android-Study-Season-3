## ViewModel

뷰의 상태를 가지고 있다가 UI단에 뿌려줌

### 왜 사용할까?

Activity, Fragment에서 자체적으로 UI 데이터를 가지고 있는 경우 Configuration Change가 발생하면 데이터가 손실됨

### Configuration Change?

앱을 구성하는 구성 요소가 변화하는 것으로 앱 실행 도중 발생할 수 있음

- 앱 화면 크기
- 화면 회전
- 폰트 크기 및 굵기
- 지역
- 다크 모드 / 라이트 모드 전환

### Lifecycle

뷰모델은 범위가 지정된 ViewModelStoreOwner가 남아있는동안 메모리에 유지됨

- Activity
  - Activity가 종료되는 경우 - onDestroy()
- Fragment
  - Fragment가 Activity로부터 Detached 되는 경우

### ViewModelLifecycleOwner

```kotlin
private lateinit var secondActivityViewModel: SecondActivityViewModel

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_second)

    secondActivityViewModel = ViewModelProvider(this)[SecondActivityViewModel::class.java]
}
```

- ViewModel은 ViewModelProvider를 통해 생성하게 되고 이때 생성자로 넘겨주는 this가 ViewModel을 소유하고 있는 객체

  
