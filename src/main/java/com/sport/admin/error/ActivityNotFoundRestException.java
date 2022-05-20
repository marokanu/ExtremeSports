package com.sport.admin.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Activity not found")
public class ActivityNotFoundRestException extends Exception{
}
