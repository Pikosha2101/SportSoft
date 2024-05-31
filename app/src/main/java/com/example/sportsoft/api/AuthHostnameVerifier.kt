package com.example.sportsoft.api

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class AuthHostnameVerifier : HostnameVerifier {
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        return true
    }
}
