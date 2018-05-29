package misk.security.ssl

import javax.inject.Qualifier
import misk.scope.ActionScoped
import misk.security.x509.X500Name
import java.security.cert.X509Certificate

/**
 * Qualifier annotation for an [ActionScoped] array of [X509Certificate]s containing the
 * certificate chain provided by the client (if any)
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.TYPE)
annotation class ClientCertChain

/**
 * Qualifier annotation for an [ActionScoped] [X500Name] containing the subject of the client cert
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.TYPE)
annotation class ClientCertSubject

/**
 * Qualifier annotation for an [ActionScoped] [X500Name] containing the issuer of the client cert
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.TYPE)
annotation class ClientCertIssuer
