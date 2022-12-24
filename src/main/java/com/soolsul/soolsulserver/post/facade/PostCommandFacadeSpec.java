package com.soolsul.soolsulserver.post.facade;

public interface PostCommandFacadeSpec<T> {
    void create(String userId, T request);

    void update(String id, T param);

    void delete(String userId, String postId);

    void scrap(String userId, String postId);
}
