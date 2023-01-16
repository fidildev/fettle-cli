package dev.fettle

sealed interface Auth {
    fun registerProvider(authProvider: AuthProvider<T>)
    fun authenticate(authenticationRequest: AuthenticationRequest<R>): ProviderAuthentication<T>
}

sealed interface AuthProvider<T> {
    val name: String
    fun authenticate(authenticationRequest: AuthenticationRequest<R>): ProviderAuthentication<T>
}

sealed interface ProviderAuthentication<T> {
    fun get(): T
    fun supports(authentication: ProviderAuthentication<T>): Boolean
}

sealed interface AuthenticationRequest<R> {
    fun get(): R
}
