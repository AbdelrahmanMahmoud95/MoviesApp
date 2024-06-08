<h1 align="center"> Movies App </h1>
![movies_list](https://github.com/AbdelrahmanMahmoud95/MoviesApp/assets/31896269/8742a3ec-4d0c-4aec-a2dd-76164b58203b)

###  Project Features

- Written in Kotlin
- Implementing MVVM design pattern with Android Architecture Components
- Following clean architecture principles
- Apply SOLID principles  
- Dependency injection with Hilt
- Safe API call with Retrofit
- Caching API response with `OkHttpClient`
- Observing data changes and updating the UI state with `StateFlow`
- Using Flow and Coroutines
- Using Room for Local database
- Using Glide library for retrieve image from api

##  Project Structure

The project separated into three main layers:
- Data
- Presentation
- Domain

### Data

Consists of four main packages:
- `local` contains *Room* components to fetch data from the local database
- `remote` contains *Retrofit* components to fetch data from the network source
- `repository` contains **implementations** of repository interfaces that are defined in the domain layer
- `models` contains data classes that hold the data

###  Domain
Domain layer is the central layer of the project contain business logic

Consists of two packages:
- `repository` contains repository **interfaces** to abstract the domain layer from the data layer
- `usecase` contains use cases(interactors) that handle the business logic, which are reused by multiple ViewModels

###  Presentation

Consists of two packages:
- `base` contains BaseFragment and BaseViewModel 
- `Fragments` contains  Fragments with their corresponding ViewModel classes


