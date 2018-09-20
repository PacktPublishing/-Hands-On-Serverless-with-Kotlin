package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import java.sql.ResultSet
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import com.amazonaws.services.lambda.runtime.RequestHandler


class DatabaseMigratorFixer : RequestHandler<Map<String, Any>, ApiGatewayResponse> {
    internal var conn = DatabaseAccessUtils.simpleConnection
    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        println("connection is " + conn!!)
        try {
            val stmt = conn!!.createStatement()
            //The query can be update query or can be select query
            val query = "delete  from DATABASECHANGELOGLOCK;"
            val status = stmt.execute(query)
            println(status)
            if (status) {
                val rs = stmt.resultSet
                while (rs.next()) {
                    println(rs.getString(1))
                }
                rs.close()
            } else {
                //query can be update or any query apart from select query
                val count = stmt.updateCount
                println("Total records updated: $count")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ApiGatewayResponse.build {
            statusCode = 200
        }

    }


}
