# Clean Architecture

⇒ 『클린 코드(Clean Code)』를 저술한 로버트 마틴(Robert C. Martin)이 제안한 시스템 아키텍처로, 기존의 계층형 아키텍처가 가지던 의존성에서 벗어나도록 하는 설계를 제공

![01.png](https://image.toast.com/aaaadh/real/2022/techblog/01%282%29.png)

### 엔티티 (Entity)

- 핵심 업무 규칙을 캡슐화함
- 메소드를 가지는 객체, 일련의 데이터 구조와 함수의 집합
- 가장 변하지 않으며, 외부로부터 영향을 받지 않는 영역

### 유즈 케이스 (UseCase)

- 애플리케이션에 특화된 업무 규칙을 포함함
- 시스템의 모든 유즈케이스를 캡슐화하고 구현함
- 엔티티로 들어오고 나가는 흐름을 조정과 조작함

### 인터페이스 어댑터 (Interface Adapter)

- 일련의 어댑터들로 구성함
- 외부 인터페이스에서 들어오는 데이터 → 유즈 케이스와 엔티티에서 처리하기 편한 방식으로 변환
- 유즈케이스와 엔티티에서 나가는 데이터 → 외부 인터페이스에서 처리하기 편한 방식으로 변환
- 컨트롤러, 프레젠터, 게이트웨이

### 프레임워크와 드라이버 (Frameworks & Drivers)

- 시스템의 핵심 업무와는 관련 없는 세부 사항
- 프레임워크나, 데이터베이스, 웹 서버 등이 해당
- 화살표의 방향은 의존성을 뜻함
- 클린 아키텍처의 의존성은 밖에서 안으로 향하고, 바깥 원은 안쪽 원에 영향을 미치지 않음
- 경계의 바깥으로 갈수록 덜 중요하고 세부적인 영역으로 표현되며, 안으로 갈수록 고수준(좀더 추상화된 개념)으로 표현됨

## 클린 아키텍처는 왜 필요할까?

------

- **각 계층이 분리되어 있기 때문에 한 계층을 변경해도 다른 계층에 영향을 미치지 않아 유지보수가 쉬움**
- **의존성을 주입하여 유닛 테스트 및 통합 테스트를 수행하기 용이**

# In Android

![03.png](https://image.toast.com/aaaadh/real/2022/techblog/03%283%29.png)

- 일반적으로 Presentation, Domain, Data 총 3개의 계층
- Presentation → Domain, Data → Domain 방향으로 의존성을 가지고 있음

## 1. Presentaion

- 화면과 입력에 대한 처리 등 UI와 관련된 부분 담당
- Activity, Fragment, View, Presenter 및 ViewModel을 포함
- Presentation 계층은 Domain 계층에 대한 의존성을 가지고 있음

## 2. Domain

- 앱의 비즈니스 로직에서 필요한 UseCase와 Model을 포함
- UseCase는 각 개별 기능 또는 비즈니스 논리 단위, Presentation, Data 계층에 대한 의존성을 가지지 않음
- Repository 인터페이스도 포함

## 3. Data

- Domain 계층에 의존성을 가지고 있음
- Domain 계층의 Repository 구현체를 포함
- 데이터베이스, 서버와의 통신
- mapper 클래스를 통해 Data 계층의 모델을 Domain 계층의 모델로 변환

### 데이터의 흐름

![03.png](https://image.toast.com/aaaadh/real/2022/techblog/03%283%29.png)

- 사용자가 이벤트를 발생시키면 **UI** → **프레젠터** → **유즈 케이스** → **엔티티** → **리포지터리** → **데이터 소스**로 이동
- 위 원에서 도메인 계층에 속해 있던 엔티티가 왜 데이터 계층에 있지?
  - 원의 엔티티는 도메인 계층의 모델이며, 데이터 계층의 엔티티는 네트워크나 로컬 DB에서 받아온 DTO를 의미
- 도메인 계층이 데이터 계층을 알고 있어야 데이터를 보낼 수 있는 게 아닌가?
  - 계층을 횡단할 때 해당 계층에 맞게 변환해야 함
  - 도메인 계층에서 모델이 트랜스레이터를 거쳐, 데이터 계층의 엔티티로 변환되는 것
  - 실제로 도메인 계층은 데이터 계층을 참고하고 있지 않고, 리포지터리에서 이루어지는 의존성 역전 법칙 때문

### 의존성 역전 법칙?

> 객체 지향 프로그래밍에서 의존 관계 역전 원칙은 소프트웨어 모듈들을 분리하는 특정 형식을 지칭 이 원칙을 따르면, 상위 계층(정책 결정)이 하위 계층(세부 사항)에 의존하는 전통적인 의존 관계를 반전(역전)시킴으로써 상위 계층이 하위 계층의 구현으로부터 독립되게 할 수 있음

![04.png](https://image.toast.com/aaaadh/real/2022/techblog/04%283%29.png)