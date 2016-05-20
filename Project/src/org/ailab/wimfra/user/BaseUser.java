package org.ailab.wimfra.user;

import org.ailab.wimfra.core.BaseDTO;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-10-2
 * Time: 下午3:47
 */
public class BaseUser extends BaseDTO {
    protected String username;
    protected String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPermittedToAdd() {
        return true;
    }

    public boolean isPermittedToEdit() {
        return true;
    }

    public boolean isPermittedToDelete() {
        return true;
    }
}
