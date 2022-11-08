package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.auth.User;

public interface CommandService<T> {
    void create(User user, T request);

    void update(String id, T param);

    void delete(String id);
}
