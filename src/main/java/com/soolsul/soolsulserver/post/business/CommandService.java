package com.soolsul.soolsulserver.post.business;

public interface CommandService<T> {
    void create(String id, T request);

    void update(String id, T param);

    void delete(String id);
}
