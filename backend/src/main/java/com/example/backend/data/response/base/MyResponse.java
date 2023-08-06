package com.example.backend.data.response.base;



import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MyResponse {

    @Expose
    private final int code;
    @Expose
    private final UUID requestId = UUID.randomUUID();
    @Expose
    private final String message;
    @Expose
    private final Object data;
    @Expose
    private final Object category;
    @Expose
    private final boolean success;
    @Expose
    private final String token;
    @Expose
    private final String photo;

    public static BaseResponseBuilder builder() {
        return new BaseResponseBuilder();
    }

    private MyResponse(BaseResponseBuilder builder) {
        this.code = builder.code;
        this.message= builder.message;
        this.data = builder.data;
        this.category = builder.category;
        this.success = builder.success;
        this.token= builder.token;
        this.photo= builder.photo;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success=" + success +
                "code=" + code +
                ", message='" + message+ '\'' +
                ", data=" + data +
                ", category=" + category +
                ", token=" + token +
                ", photo=" + photo +
                '}';
    }

    public static class BaseResponseBuilder {

        private int code = -1;
        private String message = "default message";
        private Object data = null;
        private Object category = null;

        private boolean success = false;

        private String token = "";
        private String photo = "";

        public BaseResponseBuilder buildCode(int code) {
            this.code = code;
            return this;
        }

        public BaseResponseBuilder buildMessage(String message) {
            this.message = message;
            return this;
        }

        public BaseResponseBuilder buildCategory(Object category) {
            this.category = category;
            return this;
        }

        public BaseResponseBuilder buildData(Object data) {
            this.data = data;
            return this;
        }

        public BaseResponseBuilder buildSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public BaseResponseBuilder buildToken(String token) {
            this.token = token;
            return this;
        }

        public BaseResponseBuilder buildPhoto(String photo) {
            this.photo = photo;
            return this;
        }
        public MyResponse get() {
            return new MyResponse(this);
        }

    }
}
