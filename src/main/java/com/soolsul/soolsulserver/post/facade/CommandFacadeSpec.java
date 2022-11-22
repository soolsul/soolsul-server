package com.soolsul.soolsulserver.post.facade;

public interface CommandFacadeSpec<T> {
    void create(String userId, T request);

    void update(String id, T param);

    void delete(String id);

    void scrap(String userId, String postId);
}
