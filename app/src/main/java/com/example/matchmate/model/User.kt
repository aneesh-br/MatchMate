package com.example.matchmate.model

data class User(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val dob: Dob,
    val phone: String,
    val picture: Picture,
    val login: Login
) {
    val fullName: String
        get() = "${name.first} ${name.last}"

    val age: Int
        get() = dob.age

    val photoUrl: String
        get() = picture.large
}

data class Login(
    val uuid: String
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Location(
    val city: String,
    val state: String,
    val country: String
)

data class Dob(
    val date: String,
    val age: Int
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)