package com.example.gym_app.common;

import java.util.Map;

public record ApiError(int status, String error, Map<String, String> fieldErrors) {}