package com.sopt.now.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AUTH

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class REQRES
