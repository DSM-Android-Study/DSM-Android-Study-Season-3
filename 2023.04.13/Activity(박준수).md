# Activity

## Activity ?

안드로이드 애플리케이션 화면의 기본 요소이다.

<aside>
💡 일반적인 프로그래밍 패러다임에서, `main()` 메서드를 호출하여 프로그램을 시작하는 것과 다르게, 안드로이드 시스템이 앱을 시작할 때와 같이, 수명주기에 따라 메서드를 호출한다

</aside>

## Activity의 특징

사용자가 애플리케이션의 처음 진입점부터 단계적으로 상호작용하는 데스크탑 애플리케이션과 달리, Activity는 애플리케이션의 시작 지점이 정해져 있지 않다.

- 모든 애플리케이션은 하나 이상의 Activity를 가지고 있다
- 애플리케이션의 첫 진입점이 될 수 있다

<aside>
💡 예를 들어, 홈 화면에서 이메일 앱을 실행하면 이메일 전체 목록 화면이 표시되지만, 이메일 앱을 실행시키는 SNS 앱을 사용하면 이메일 작성 화면이 표시될 것이다

</aside>

---

# Activity의 생명 주기

## Activity의 생명 주기 ?

안드로이드 시스템이 Activity의 생명 주기에 따라, 적절한 메서드를 호출하여 생명 주기를 관리한다.

### `onCreate()`

Activity의 인스턴스가 생성되었을 때 호출되는 메서드이다.

- View를 초기화하기 적합하다
- CREATED 상태이다

### `onStart()`

Activity가 사용자에게 보여질 때 호출되는 메서드이다.

- STARTED 상태이다

### `onResume()`

Activity가 사용자와 상호작용을 시작할 때 호출되는 메서드이다.

- Activity가 Activity 스택의 최상단에 위치할 때 호출된다
- RESUMED 상태이다

### `onPause()`

Activity가 더이상 포커스를 받지 않을 때 호출되는 메서드이다.

- Activity가 부분적으로 사용자에게 보여질 수 있다
- 다시 포커스를 받을 경우 `onResume()`, 그렇지 않을 경우 `onStop()`을 호출한다
- UI 업데이트가 예상되는 경우, 업데이트를 계속한다
- `onPause()`에서 애플리케이션, 또는 사용자의 데이터를 저장하면 안된다
- PAUSED 상태이다

### `onStop()`

Activity가 더이상 사용자에게 보여지지 않을 때 호출되는 메서드이다.

- 현재 Activity가 파괴되거나, 새로운 Activity가 시작될 때 호출된다
- STOPPED 상태이다

### `onRestart()`

STOPPED 상태의 Activity가 재시작될 때 호출되는 메서드이다.

- 해당 Activity가 STOPPED 상태를 가진 시점의 Activity 상태를 복원한다
- `onStart()`를 연이어 호출한다

### `onDestroy()`

Activity가 파괴되었을 때 호출되는 메서드이다.

- Activity에 할당된 자원, 프로세스 등을 해제하기 적합한 메서드이다
- DESTROYED 상태를 가진다

---

# Activity의 호출

## 1. Activity를 선언한다

### 클래스 선언 및 구현

```kotlin
class JunsuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        setContentView(
            R.layout.activity_main,
        )
				
				...
    }
}
```

### Manifest에 클래스 선언

```xml
<activity
    android:name=".JunsuActivity"
    android:exported="true">
</activity>
```

## 2. Activity를 호출한다

```kotlin
fun moveToJunsuActivity() {
	context.startActivity(
		Intent(
			this@MainActivity, // from
			JunsuActivity::class.java, // to
		),
	)
}
```

---

# Activity의 통신

## 1. Intent를 선언한다

### Intent 선언

```kotlin
val intentToJunsu = Intent(
	this@MainActivity,
	JunsuActivity::class.java,
)

with (intentToJunsu) {
	putExtra("age", 18) // int
	putExtra("height", 160f) // float
	putExtra("blood_type", 'A') // char
	putExtra("name", "junsu park") // string
}
```

### Intent 실행

```kotlin
fun moveToJunsuActivity() {
	context.startActivity(
		Intent(
			this@MainActivity, // from
			JunsuActivity::class.java, // to
		),
	)
}
```

## 2. Intent를 수신한다

```kotlin
class JunsuActivity : AppCompatActivity() {
    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?,
    ) {
        val age = savedInstanceState?.getInt("age") // int
        val height = savedInstanceState?.getFloat("height") // float
        val bloodType = savedInstanceState?.getChar("blood_type", 'N') // char
        val name = savedInstanceState?.getString("blood_type", "NONAMED") // string
    }
}
```
