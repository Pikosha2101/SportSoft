package com.example.sportsoft.API

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class MatchHostnameVerifier : HostnameVerifier{
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        return true
    }
}