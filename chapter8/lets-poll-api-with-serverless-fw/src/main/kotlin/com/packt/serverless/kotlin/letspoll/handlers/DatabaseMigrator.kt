package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import liquibase.LabelExpression
import liquibase.Contexts
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.Liquibase
import liquibase.database.jvm.JdbcConnection
import liquibase.database.DatabaseFactory
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.packt.serverless.kotlin.letspoll.models.responses.APIErrorMessage
import com.packt.serverless.kotlin.letspoll.models.responses.APISuccessResponse
import org.apache.logging.log4j.LogManager


class DatabaseMigrator : RequestHandler<Map<String, Any>, ApiGatewayResponse> {
    internal var conn = DatabaseAccessUtils.simpleConnection
    override  fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {

        try {
            val dataBase = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                    JdbcConnection(conn))
            val liquiBase = liquibase.Liquibase("letsPoll.changelog.xml", ClassLoaderResourceAccessor(), dataBase)
            liquiBase.update(Contexts(), LabelExpression())
        } catch (e: Exception) {
            LOG.error("Exception occured in migrating the database",e.printStackTrace())
            return ApiGatewayResponse.build {
                statusCode = 409
                objectBody = APIErrorMessage("Could not migrate the database")
            }

        }

        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = APISuccessResponse("Sucessfully Migrated the database")
        }

    }

    companion object {
        private val LOG = LogManager.getLogger(DatabaseMigrator::class.java)
    }

}
