https://junjaboy.notion.site/Gradle-Version-Catalog-c10f0ef4544447f79b640b9bb3473312?pvs=4
# Gradle Version Catalog

# Gradle Version Catalog

## Gradle Version Catalog ?

의존성과 플러그인의 추가 및 유지보수를 효율적으로 관리하는 기술이다.

- 다중 모듈을 사용할 때 의존성과 플러그인 관리를 쉽게 할 수 있다
- 하나의 버전 목록을 만들어 여러 모듈이 type-safe하게 참조한다

## buildSrc를 사용한 의존성, 플러그인 관리와 비교

|  | Gradle Version Catalog | buildSrc |
| --- | --- | --- |
| 버전 검색 | 자동 | 수동 |
| 버전 번호 변경 후 Rebuild | 버전의 영향을 받는 모듈만 | 프로젝트의 모든 모듈 |

## Gradle Version Catalog를 사용하는 이유

의존성과 플러그인을 하나의 공간에서 관리하고 유지보수하기 위함이다.

- 다중 모듈 프로젝트를 개발할 때, 모듈별로 의존성이나 플러그인의 버전이 다르게 선언되는 것을 방지한다
- 의존성과 플러그인을 불러오기 위해 문자열을 모듈마다 하드코딩하는 것을 방지한다
- `libs` 변수에 접근하여 안전하게 의존성 또는 플러그인을 참조한다
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/c3a7b61d-80bf-4673-a8d6-f1b159c4e3ee/f1ffe075-882d-4b28-98a3-897eba555629/Untitled.png)
    

## Gradle Version Catalog 섹션

Gradle Version Catalog의 섹션은 `versions`, `libraries`, `plugins`로 구성되어있다.

### `[libraries]`

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/c3a7b61d-80bf-4673-a8d6-f1b159c4e3ee/e36dc775-2d2a-4bdb-88d0-aca77b616426/Untitled.png)

의존성 정보를 담는 변수를 정의한다.

### `[plugins]`

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/c3a7b61d-80bf-4673-a8d6-f1b159c4e3ee/92c67df7-6c99-4326-b71f-6bd0c75f3af8/Untitled.png)

플러그인 정보를 담는 변수를 정의한다.

### `[versions]`

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/c3a7b61d-80bf-4673-a8d6-f1b159c4e3ee/c6cc2318-8a3d-4a9e-8ce5-0944b7cacf5a/Untitled.png)

선언되어있는 의존성, 플러그인의 버전을 담는 변수를 정의한다.

- `[libraries]`, `[plugins]` 섹션에서 `[versions]` 섹션의 버전 정보를 참조한다

## Gradle Version Catalog path 구분

의존성, 플러그인 등의 정보를 담은 변수를 선언할 때 path를 통해 Gradle 검색 경로를 지정해줄 수 있다.

### `group`

```toml
androidx-core = { group = "androidx.core", name = "core-ktx", version.ref = "core" }
```

여러 모듈을 가지고 있는 그룹을 명시한다.

- `name`을 함께 사용하여 특정 모듈을 참조한다

### `module`

```toml
androidx-core = { modulie = "androidx.core:core-ktx", version.ref = "core" }
```

특정 모듈을 명시한다.

### `id`

```toml
android-application = { id = "com.android.application", version.ref = "agp" }
```

플러그인의 path를 명시한다.

## Gradle Version Catalog 사용

### Version Catalog 파일 추가

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/c3a7b61d-80bf-4673-a8d6-f1b159c4e3ee/7ceec4b1-6df4-4c41-933b-ac8a2f42e46a/Untitled.png)

루트 프로젝트의 `gradle/` 하위에 `libs.versions.toml` 파일을 추가한다.

- `libs.versions.toml`는 Gradle이 기본적으로 검색하는 파일 이름 이름이다

### Version Catalog 파일 내에 의존성 선언

```toml
[versions]
core = "<version>"

[libraries]
androidx-core = { group = "androidx.core", name = "core-ktx", version.ref = "core" }
```

Version Catalog 내 변수는 Kebab 케이스(`app-junsu-kebabExample`)를 사용하여 명명한다.

- 더 나은 코드 완성 어시스턴스가 가능하다
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/c3a7b61d-80bf-4673-a8d6-f1b159c4e3ee/fb0ce3c0-e6dc-46c2-b7c9-ee47ac45ee14/Untitled.png)
    

### 기존 코드 이전

- 루트 프로젝트의 `build.gradle.kts`
    
    ```kotlin
    plugins {
    		id("com.android.application") version "<version>" apply false
    		// ...
    }
    ```
    
    ```toml
    [versions]
    agp = "<version>"
    
    [plugins]
    android-application = { id = "com.android.application", version.ref = "agp" }
    ```
    
    ```kotlin
    plugins {
    		alias(libs.plugins.android.application) apply false
    		// ...
    }
    ```
    
- 모듈의 `build.gradle.kts`
    - `plugins` 블록
        
        ```kotlin
        plugins {
           id("com.android.application")
        }
        ```
        
        ```toml
        [versions]
        agp = "<version>"
        
        [plugins]
        android-application = { id = "com.android.application", version.ref = "agp" }
        ```
        
        ```kotlin
        plugins {
           alias(libs.plugins.android.application)
        }
        ```
        
    - `dependencies` 블록
        
        ```kotlin
        dependencies {
        		implementation("androidx.core:core-ktx:<version>")
        		implementation("androidx.appcompat:appcompat:<version>")
        		implementation("com.google.android.material:material:<version>")
        }
        ```
        
        ```kotlin
        [versions]
        core = "<version>"
        appcompat = "<version>"
        material = "<version>"
        
        [libraries]
        androidx-core = { group = "androidx.core", name = "core-ktx", version.ref = "core" }
        androidx-appcompat = { module = "androidx.appcompat:appcompat", version.ref = "appcompat" }
        material = { module = "com.google.android.material:material", version.ref = "material" }
        ```
        
        ```kotlin
        dependencies {
        		implementation(libs.androidx.core)
        		implementation(libs.androidx.appcompat)
        		implementation(libs.material)
        }
        ```
        

## 이슈

### Use the explicit one if necessary

```
'val Project.libs: LibrariesForLibs' can't be called in this context by implicit receiver. Use the explicit one if necessary
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/c3a7b61d-80bf-4673-a8d6-f1b159c4e3ee/4c66f511-ae97-45c7-b9e3-772cc24552de/Untitled.png)

[](https://youtrack.jetbrains.com/issue/KTIJ-19369/False-positive-cant-be-called-in-this-context-by-implicit-receiver-with-plugins-in-Gradle-version-catalogs-as-a-TOML-file)

인텔리제이 이슈이다.

- 오류가 발생하는 파일에 `@Suppress` 애너테이션을 명시하여 해결할 수 있다
