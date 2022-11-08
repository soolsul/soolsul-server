package com.soolsul.soolsulserver.post.business;

public interface QueryService<T> {
    T find(String id);
}
