# Activity 생명주기

생명주기에 따른 여러 콜백은 Activity의 상태 변화에 따라서 적합한 작업을 수행할 수 있도록 해야 한다, 또한 수명주기 콜백은 올바르게 구현하면 리소스가 낭비되거나 다른 앱으로 전환할 떄, 다른  앱에서 다시 돌아올 떄 야기되는 다양한 문제들을 예방할 수 있음

![image](https://github.com/DSM-Android-Study/DSM-Android-Study-Season-3/assets/128464859/ee118575-2076-4300-a3d6-bc619602878e)

- **ocCreate()** : 필수적으로 구현해야 하며, **시스템이 Activity를 생성할 떄 실행된다.** Activity는 생성되면서 CREATED 상태가 된다. onCreate()에서는 주로 데이터를 바인딩하거나, Activity와 ViewModel을 연결한다. savedInstanceState라는 매개변수는 Activity의 이전 상태에 대한 정보가 저장되어 있는 Bundle 객체이다. 처음 생성되면 이 값은 null이다.  연결된 수명 주기 인식 구성요소가 있다면 ON_CREATE 이벤트를 수신한다.

- **onStart()** : Activity가 STARTED 상태에 들어가면 시스템은 이 콜백을 호출한다. **Activity가 사용자에게 표시되는 단계이며, 앱이 Activity를 포그라운드에 보내 사용자가 상호작용할 준비를 한다.** 함수에서 앱이 UI를 관리하는 코드를 초기화한다. 연결된 수명 주기인식 구성요소는 ON_START 이벤트를 수신한다.

- **onResume()** : Activity가 RESUMED 상태에 들어가면 시스템은 이 콜백을 호출함. **Activity는 포그라운드에 표시되며 앱이 사용자와 상호작용함.** 어떠한 이벤트로 앱의 포커스가 사라지기 전까지 앱은 이 상태에 머무름. 예를 들어 전화가 오거나, 멀티 윈도우 상태에서 다른 앱과 상호작용하는 경우가 있음. 이렇게 방해되는 이벤트가 발생하면 Activity가 PAUSED 상태에서 다시 RESUMED 상태로 돌아오면 시스템이 onResume()을 다시 호출한다.
- onPaue() : 사용자가 이 Activity를 잠시 떠나면 앱은 이 콜백을 호출합니다.
