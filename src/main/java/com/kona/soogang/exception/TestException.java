package com.kona.soogang.exception;

import lombok.Getter;

@Getter
public class TestException extends RuntimeException {
    private TestHttpResponseCode testHttpResponseCode;

    public TestException(TestHttpResponseCode testHttpResponseCode){
        this.testHttpResponseCode = testHttpResponseCode;
    }
}
