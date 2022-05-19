package com.microstore.dhanya.constants;

public class AuthenticationConstants {

    public static final Integer TOKEN_VALID = 1;
    public static final Integer TOKEN_INVALID = 2;      // token not present in DB , perhaps first login
    public static final Integer TOKEN_EXPIRED = 3;

    public static final Integer TOKEN_VALIDITY_TIME_PERIOD = 3600;              // time period (in seconds) for which the token is valid
}
