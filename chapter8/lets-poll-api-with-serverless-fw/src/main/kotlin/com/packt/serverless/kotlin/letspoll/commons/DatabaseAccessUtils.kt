package com.packt.serverless.kotlin.letspoll.commons

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

import com.sun.corba.se.impl.encoding.CodeSetConversion.impl


object DatabaseAccessUtils {
    var dslContext: DSLContext? = null
    var connection: Connection? = null

    val databaseConnection: DSLContext?
        get() {

            if (dslContext != null) {
                return dslContext
            } else {

                val userName = LambdaEnvironmentUtils.getValue("databaseUsername")
                val password = LambdaEnvironmentUtils.getValue("databasePassword")
                val databaseUrl = LambdaEnvironmentUtils.getValue("databaseUrl")
                val databasePort = LambdaEnvironmentUtils.getValue("databasePort")
                val databaseName = LambdaEnvironmentUtils.getValue("databaseName")


                val sb = StringBuilder()
                sb.append("jdbc:postgresql://")
                sb.append(databaseUrl)
                sb.append(":")
                sb.append(databasePort)
                sb.append("/")
                sb.append(databaseName)

                val url = sb.toString()
                println(url)
                try {
                    println(url)
                    println(userName)
                    println(password)
                    val conn = DriverManager.getConnection(url, userName, password)
                    dslContext = DSL.using(conn, SQLDialect.POSTGRES)

                } catch (e: Exception) {
                    e.printStackTrace()

                }

                return dslContext
            }
        }

    val simpleConnection: Connection?
        get() {

            if (connection != null) {
                return connection

            } else {
                try {
                    Class.forName("org.postgresql.Driver")
                    connection = DriverManager.getConnection(connectionString, LambdaEnvironmentUtils.getValue("databaseUsername"), LambdaEnvironmentUtils.getValue("databasePassword"))
                } catch (e: SQLException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }

            }
            return connection
        }

    private val connectionString: String
        get() {
            val userName = LambdaEnvironmentUtils.getValue("databaseUsername")
            val password = LambdaEnvironmentUtils.getValue("databasePassword")
            val databaseUrl = LambdaEnvironmentUtils.getValue("databaseUrl")
            val databasePort = LambdaEnvironmentUtils.getValue("databasePort")
            val databaseName = LambdaEnvironmentUtils.getValue("databaseName")


            val sb = StringBuilder()
            sb.append("jdbc:postgresql://")
            sb.append(databaseUrl)
            sb.append(":")
            sb.append(databasePort)
            sb.append("/")
            sb.append(databaseName)

            return sb.toString()

        }
}
