package no.nav.personopplysninger.proxy.config

data class Environment(
    //val corsAllowedOrigins: String = getEnvVar("CORS_ALLOWED_ORIGINS")
    val corsAllowedOrigins: String = "*"

    // InfluxDB
    /*
    val influxdbHost: String = getEnvVar("INFLUXDB_HOST"),
    val influxdbPort: Int = IntEnvVar.getEnvVarAsInt("INFLUXDB_PORT"),
    val influxdbName: String = getEnvVar("INFLUXDB_DATABASE_NAME"),
    val influxdbUser: String = getEnvVar("INFLUXDB_USER"),
    val influxdbPassword: String = getEnvVar("INFLUXDB_PASSWORD"),
    val influxdbRetentionPolicy: String = getEnvVar("INFLUXDB_RETENTION_POLICY"),
    */
)
