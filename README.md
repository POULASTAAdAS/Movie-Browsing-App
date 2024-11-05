# mFlix (Movie-Browsing-App)

mFlix is a movie browsing navite android application build using jetpack compose and custom backend build using ktor (used only for Authentication) uses [The Movie Database](https://developer.themoviedb.org/reference/intro/getting-started) as data source.

# Features

- Supports Every Screen size
- Authentication using google and email
- A custom library to save your favourite movies and tv shows
- Auto Sync data with cloud
- An enhanced search functionality
- upto date and accurate information

| **Client**      | **Server** |
| --------------- | ---------- |
| Jetpack Compose | Ktor       |
| Navigation      | Exposed    |
| OkHttp          | SQL        |
| Paging          | Session    |
| Room            |            |
| Coil            |            |
| Palette         |            |
| WindowSizeClass |            |

# Preview

<a href="https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/ss/mobile/auth_mFlix.gif">
    <img src="https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/ss/mobile/auth_mFlix.gif" width="200" height="400" alt="Auth Preview">
</a>
<a href="https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/ss/mobile/home_mFlix.gif">
    <img src="https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/ss/mobile/home_mFlix.gif" width="200" height="400" alt="Home Preview">
</a>
<a href="https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/ss/mobile/preview.gif">
    <img src="https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/ss/mobile/preview.gif" width="200" height="400" alt="Preview">
</a>

## Tab

<a href="https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/ss/tab/tab%20home.gif">
    <img src="https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/ss/tab/tab%20home.gif" width="300" height="300" alt="Tab Home Preview">
</a>

## Setup

- Create an app on [Google Cloud Platform](https://console.cloud.google.com/welcome)
- add web and android OAuth Key
- setup web client id as environtment variable used on [server](https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/mFlexAuth/src/main/kotlin/com/poulastaa/route/auth/Auth.kt)
- setup sessionEncryptionKey and sessionSecretKey used on [server](https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/mFlexAuth/src/main/kotlin/com/poulastaa/plugins/Session.kt) visit [here](https://ktor.io/docs/server-sessions.html) for more info
- setup proper databse url on [applcaiton.conf](https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/mFlexAuth/src/main/resources/application.conf)
- setup local.properties file on android (look into buildType block [app level build.gradle.kts file](https://github.com/POULASTAAdAS/Movie-Browsing-App/blob/main/mFlix/app/build.gradle.kts))
- run sever and client
- you are all set :)

# License

```xml
Designed and developed by Poulastaa Das

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
