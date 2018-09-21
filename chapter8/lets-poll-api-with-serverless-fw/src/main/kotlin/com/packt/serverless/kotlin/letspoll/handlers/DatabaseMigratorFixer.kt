package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import java.sql.ResultSet
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.packt.serverless.kotlin.letspoll.models.responses.APISuccessResponse
import org.apache.logging.log4j.LogManager


class DatabaseMigratorFixer : RequestHandler<Map<String, Any>, ApiGatewayResponse> {
    internal var conn = DatabaseAccessUtils.simpleConnection
    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        try {
            val stmt = conn!!.createStatement()
            //The query can be update query or can be select query
            val query = "delete  from DATABASECHANGELOGLOCK;"
            val status = stmt.execute(query)
            if (status) {
                val rs = stmt.resultSet
                while (rs.next()) {
                    LOG.info(rs.getString(1))
                }
                rs.close()
            } else {
                //query can be update or any query apart from select query
                val count = stmt.updateCount
                LOG.info("Total records updated: $count")
            }
        } catch (e: Exception) {
            LOG.error("Could not fix the migration",e.printStackTrace())
        }

        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = APISuccessResponse("Sucessfully Fixed Migration Lock")
        }

    }

    companion object {
        private val LOG = LogManager.getLogger(DatabaseMigratorFixer::class.java)
    }



}
