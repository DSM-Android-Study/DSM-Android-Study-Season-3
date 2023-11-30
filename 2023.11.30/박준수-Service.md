# Service

# Service

!https://images.unsplash.com/photo-1661956602153-23384936a1d3?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb

## Service ?

백그라운드에서 오랫동안 실행되는 작업을 수행할 수 있는 애플리케이션 컴포넌트이다.

- UI를 제공하지 않는다
- 다른 애플리케이션의 컴포넌트가 실행시킬 수 있다
    - Manifest에서 서비스를 비공개로 선언하여 방지한다

## Service의 유형

### Foreground

!https://i.stack.imgur.com/2YEGI.png

사용자에게 보여지는 작업을 수행한다.

- 오디오 트랙 재생 등이 있다
- 알림을 표시하는 서비스이다

### Background

사용자에게 직접 보여지지 않는 작업을 수행한다.

- 파일 압축 등이 해당된다

### Bound

애플리케이션 구성요소와 바인딩된 서비스이다.

- Client-Server 인터페이스를 제공하여 구성요소와 서비스가 상호작용한다
    - 위 개념으로 IPC 작업도 수행할 수 있다

## Service의 수명 주기

### `onStartCommand()`

서비스가 시작되고, 백그라운드에서 무한히 실행된다.

- `stopSelf()`로 서비스가 직접 종료하거나 다른 애플리케이션 구성요소에서 `stopService()`를 호출하여 서비스를 중단시킬 수 있다
- 다른 애플리케이션 구성요소가 서비스를 시작하도록 요청한다
- 시스템이 호출한다

### `onBind()`

클라이언트와 서비스가 바인딩되어 통신하기 위해 사용할 `IBinder` 인터페이스를 반환한다.

- 바인딩을 지원하지 않는다면 `null`을 반환한다

### `onCreate()`

서비스가 처음 생성되었을 때 한 번 호출된다.

- `onStartCommand()`/`onBind()`가 호출되기 전에 호출된다
- 서비스가 실행중일때 호출되지 않는다

### `onDestroy()`

서비스가 더이상 사용되지 않아 소멸될 때 호출된다.

- 등록한 리소스를 정리한다
- 서비스가 수신하는 마지막 호출이다

## Service의 호출

### `startService()`

```kotlin
startService(Intent(applicationContext, MusicService::class.java))
```

다른 애플리케이션 구성요소에서 Service를 호출한다.

- Service의 `onStartCommand()`가 호출된다
- `stopSelf()`/`stopService()`가 호출되기 전까지 실행을 유지한다
    - `stopSelf()` - Service가 직접 호출한다
    - `stopService()` - 다른 애플리케이션 구성요소가 호출한다

### `bindService()`

```kotlin
private val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
			  // ...
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
        // ...
    }
}

override fun onStart() {
    super.onStart()
    Intent(this, MusicService::class.java).also { intent ->
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
}

override fun onStop() {
    super.onStop()
    unbindService(connection)
}
```

다른 애플리케이션 구성요소에 바인딩되어 상호작용할 수 있는 Service이다.

- Service의 `onBind()` 콜백에서 `IBinder`를 반환해야한다
- 3번째 flag 아규먼트로 바인딩 옵션을 정의할 수 있다

## 시스템의 Service의 소멸

### Service의 소멸

기기의 메모리가 부족하면, 시스템에 의해 Service 인스턴스가 소멸될 수 있다.

<aside>
💡 Foreground Service → 소멸 가능성 매우 적음
Bound Service → 소멸 가능성 적음
Background Service → 소멸 가능성 높음

</aside>

### Service 소멸 후

`onStartCommand()`의 반환 flag에 따라 시스템에 의한 Service 소멸 후 재시작 여부를 결정할 수 있다.

- `START_NOT_STICKY` - Service가 **다시 생성되지 않는다**
    - 전달할 pending intent가 남아있는 경우 재생성된다
- `START_STICKY` - 전달중이던 **intent 없이** Service가 **다시 생성된다**
    - 시스템이 Intent 값이 null인 `onStartCommand()`를 호출한다
        - 전달한 pending intent가 남아있는 경우 해당 Intent를 전달한다
    - 직접 수행하는 명령 없이, 무한히 작업을 기다리며 실행되는 작업에 적합하다
        - media player 등이 있다
- `START_REDELIVER_INTENT` - 전달중이던 intent와 함께 Service가 **다시 생성된다**
    - pending intent가 차례로 전달된다
    - Service가 즉시 재개되어야하는 작업에 적합하다
        - 다운로딩 서비스 등이 있다
    

## Service의 종류

### `Service`

모든 서비스의 기본이 되는 클래스이다.

- 애플리케이션의 UI 스레드를 사용한다
    - 서비스가 작업을 수행할 때, 새로운 스레드를 생성 하는것이 중요하다

### `IntentService`

Worker 스레드를 사용하여 수신받은 요청을 하나씩 처리한다.

- 서비스가 여러 요청을 동시에 처리하지 않아도 될 때 사용할 수 있다
- `onHandleIntent()`를 구현한다

<aside>
⚠️ Android 8 이상의 기기에서는 [백그라운드 실행 제한](https://developer.android.com/guide/components/services#:~:text=Background%20execution%20limits)으로 인해 IntentService의 사용이 권장되지 않는다. 대신, JobIntentService를 사용할 수 있다. (JobIntentService 또한 [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager/basics?hl=ko)로 대체할 수 있다.)

</aside>
