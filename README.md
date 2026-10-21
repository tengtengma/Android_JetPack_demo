# Android Demo — Jetpack Compose & MVVM 实战项目

![Kotlin](https://img.shields.io/badge/Kotlin-1.9%2B-blue.svg?logo=kotlin)
![Android](https://img.shields.io/badge/Android-SDK%2028%2B-green.svg?logo=android)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material3-brightgreen.svg?logo=jetpackcompose)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

一个基于 **Jetpack Compose**、**MVVM 架构** 和 **OkHttp3 + Gson 自研网络层** 的现代化 Android 示例应用。项目展示了用户认证、新闻列表流拉取、网页详情渲染以及基于 DataStore 的 Token 本地存储与手动依赖注入（AppContainer）最佳实践。

---

## 🌟 特性亮点

- 🔐 **认证与状态管理 (Auth & State)**：
  - 支持登录与注册功能。
  - 基于 **Jetpack Preferences DataStore** 持久化保存 User Token 与登录信息。
  - 应用启动自动校验登录态，自动路由至首页或登录页。
- 📰 **新闻列表与卡片 (News Stream)**：
  - 使用 Compose `LazyColumn` + 自定义 Card 实现响应式新闻流。
  - 集成 **Coil** 加载异步网络图片并配置优雅裁剪。
- 📖 **新闻详情渲染 (WebView Detail)**：
  - 使用 Compose `AndroidView` 包裹原生 `WebView` 渲染网页内容。
  - 集成带有返回图标的 Material 3 `TopAppBar`。
  - 路由参数传输采用 `URLEncoder` 与 `URLDecoder` 编解码，保障带参数 URL 导航安全性。
- 🌐 **自研轻量级网络通信库 (Network Layer)**：
  - 基于 OkHttp3 + Coroutines + Gson 打造。
  - 统一处理泛型数据反序列化（`NetworkRequest<T>`）、HTTP 错误码映射及超时重连控制。
- 🛠 **手动依赖注入 (AppContainer DI)**：
  - 避免过度复杂的 DI 框架引入，使用单例/容器化 `AppContainer` 管理 `AuthService`、`NewsService` 与 ViewModels。

---

## 🛠 技术栈

| 模块 | 技术选型 | 说明 |
| :--- | :--- | :--- |
| **开发语言** | Kotlin | 全项目 100% 采用 Kotlin 开发 |
| **UI 框架** | Jetpack Compose | Material 3 规范声明式 UI |
| **路由导航** | Navigation Compose | 单 Activity 架构页面流转与参数传递 |
| **架构设计** | MVVM + Repository/Service | 状态驱动 (Compose State / ViewModel) |
| **异步处理** | Kotlin Coroutines & Flow | 协程异步并发与状态监控 |
| **网络请求** | OkHttp3 + Gson | 自研 RESTful 通信层与 JSON 自动解析 |
| **数据持久化** | Preferences DataStore | 轻量级 Key-Value 异步存储 |
| **图片加载** | Coil Compose | 异步图片加载与内存/磁盘缓存 |

---

## 📐 架构设计

整体架构遵循官方推荐的 **MVVM (Model-View-ViewModel)** 模式及单向数据流 (Unidirectional Data Flow)：

```text
┌─────────────────────────────────────────────────────────────┐
│                       UI Layer (Compose)                    │
│    AuthView            HomeView            HomeDetailView   │
└────────▲──────────────────▲──────────────────────▲──────────┘
         │                  │                      │
         ▼                  ▼                      ▼
┌─────────────────────────────────────────────────────────────┐
│                        ViewModel Layer                      │
│   AuthViewModel        HomeViewModel    HomeDetailViewModel │
└────────▲──────────────────▲──────────────────────▲──────────┘
         │                  │                      │
         ▼                  ▼                      ▼
┌─────────────────────────────────────────────────────────────┐
│                 Service / Network Layer                     │
│   AuthService          NewsService         NetworkManager   │
└────────▲──────────────────▲──────────────────────▲──────────┘
         │                  │                      │
         ▼                  ▼                      ▼
┌───────────────────────────┐  ┌──────────────────────────────┐
│  DataStore (Local Storage)│  │   Backend REST APIs (Remote) │
└───────────────────────────┘  └──────────────────────────────┘
```

- **AppContainer**：全局管理 `NetworkManager`、`AuthService`、`NewsService` 以及 ViewModel Factory 的生命周期。

---

## 📂 项目目录结构

```text
app/src/main/java/com/person/android_demo/
├── MainActivity.kt               # 应用主入口，负责 NavHost 挂载与登录态分发
├── models/                       # UI/业务数据模型
│   ├── auth/AuthModel.kt
│   └── home/
│       ├── HomeModel.kt
│       └── HomeDetailModel.kt
├── ui/theme/                     # Compose Material3 全局主题配置
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
├── utils/
│   ├── data/
│   │   └── UserPreferencesManager.kt  # Jetpack DataStore 管理器
│   └── network/                       # 自研网络通信核心模块
│       ├── core/                      # NetworkManager, NetworkRequest, Error处理
│       ├── di/                        # AppContainer 依赖注入容器
│       └── feautures/                 # API 访问定义、Services 与 DTO 数据模型
│           ├── apis/ (AuthAPI, NewsAPI)
│           ├── models/ (AuthResponse, NewsResponse)
│           └── services/ (AuthService, NewsService)
├── viewModels/                   # 页面 ViewModel
│   ├── auth/AuthViewModel.kt
│   └── home/
│       ├── HomeViewModel.kt
│       └── HomeDetailViewModel.kt
└── views/                        # Compose View 布局视图
    ├── auth/AuthView.kt
    └── home/
        ├── HomeView.kt
        └── HomeDetailView.kt
```

---

## 🚀 快速开始

### 1. 环境准备

- **Android Studio**：Ladybug (2024.2.1) 或更高版本
- **JDK**：Java 11 或更高版本
- **Compile SDK**：37
- **Min SDK**：28

### 2. 克隆项目

```bash
git clone https://github.com/your-username/android_demo.git
cd android_demo
```

### 3. 配置后端地址

在 `MainActivity.kt` 中修改后台 API 的服务地址与超时时间：

```kotlin
val config = NetworkConfiguration(
    baseUrl = "http://192.168.x.x:5177", // 替换为你本地或服务端的真实地址
    requestTimeout = 30L,
    responseTimeout = 60L
)
```

### 4. 编译与运行

你可以直接在 Android Studio 中打开项目并点击 **Run**，或者使用 Gradle 命令构建：

```bash
# 编译 Debug APK
./gradlew assembleDebug
```

---

## 📝 许可协议

本项目采用 [MIT License](LICENSE) 许可协议，欢迎自由使用、学习与二次开发。
