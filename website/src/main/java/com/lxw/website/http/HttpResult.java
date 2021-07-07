package com.lxw.website.http;

import com.sun.istack.internal.NotNull;
import lombok.Data;

/**
 * @author LXW
 * @date 2021年04月25日 11:28
 */
@Data
public class HttpResult {

    @NotNull
    private Integer resultCode;
    @NotNull
    private String resultBody;

    public HttpResult(int resultCode, String resultBody) {
    }


    public HttpResult() {
    }

}
