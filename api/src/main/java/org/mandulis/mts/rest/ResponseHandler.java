package org.mandulis.mts.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>During development, there is the need to return a consistent API response. This class contains all the possible
 * API responses</p>
 *
 * @author Ernest Sarfo
 * @since 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHandler {
    private HttpStatus status;
    private Object data;

    public static ResponseEntity<Object> successResponse(String message, Object data,HttpStatus status) {
        return response("success",status,data);
    }

    public static ResponseEntity<Object> errorResponse(Object data, HttpStatus status){
        return response("error", status, data);
    }

    public static ResponseEntity<Object> response(String statusMessage,HttpStatus httpStatus,Object data) {
        Map<String,Object> response = new HashMap<>();
        response.put("status",statusMessage);
        response.put("content",data);
        return new ResponseEntity<>(response,httpStatus);
    }

    public static <T> ResponseEntity<ApiResponse<T>> handleSuccess(
            T data, HttpStatus httpStatus, String statusMessage
    ){
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setMessage(statusMessage);
        response.setSuccess(true);
        return new ResponseEntity<>(response,httpStatus);
    }
    public static <T> ResponseEntity<ApiResponse<T>> handleError(
            T data, HttpStatus httpStatus, String statusMessage, List<String> errors
    ){
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setMessage(statusMessage);
        response.setSuccess(false);
        response.setErrors(errors);
        return new ResponseEntity<>(response,httpStatus);
    }
}
