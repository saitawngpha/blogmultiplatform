package com.saitawngpha.blogmultiplatform.models

/**
 * Created by တွင်ႉၾႃႉ on 02/11/2023.
 */
expect  class User {
    val id: String
    val username: String
    val password: String
}

expect  class UserWithoutPass {
    val id: String
    val username: String
}