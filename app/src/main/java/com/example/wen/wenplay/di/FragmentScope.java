package com.example.wen.wenplay.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wen on 2017/2/28.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface FragmentScope {
}
