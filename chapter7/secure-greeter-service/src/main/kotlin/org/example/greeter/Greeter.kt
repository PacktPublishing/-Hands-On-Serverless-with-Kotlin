package org.example.greeter

import com.amazonaws.services.kms.AWSKMSClientBuilder
import com.amazonaws.services.kms.model.DecryptRequest
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.util.Base64
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.sql.DriverManager
import java.util.*

class Greeter : RequestHandler<String, String> {


    override fun handleRequest(greetee:String, context: Context): String {
        val logger = context.logger
        logger.log("lets greet $greetee")
        val userName = decryptKey("databaseUsername")
        val password = decryptKey("databasePassword")
        val databaseUrl = decryptKey("databaseUrl")
        val databasePort = decryptKey("databasePort")
        val databaseName = decryptKey("databaseName")


        val sb = StringBuilder()
        sb.append("jdbc:postgresql://")
        sb.append(databaseUrl)
        sb.append(":")
        sb.append(databasePort)
        sb.append("/")
        sb.append(databaseName)

        val url = sb.toString()

        try {
            val conn = DriverManager.getConnection(url, userName, password)
            val dslContext = DSL.using(conn, SQLDialect.POSTGRES)
            val result = dslContext.fetchOne("SELECT * FROM now()")
            val ts = result.get("now") as java.sql.Timestamp
            val tsString = ts.toString()
            return "Hello,  $greetee on $tsString  as per the time on database server"

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val lambdaTime =  Date().toString()
        return "Hello, $greetee  on $lambdaTime  as per the default time on lambda"
    }

    private fun decryptKey(environmentKeyName: String): String {
        val encryptedKey = Base64.decode(System.getenv(environmentKeyName))

        val client = AWSKMSClientBuilder.defaultClient()

        val request = DecryptRequest()
                .withCiphertextBlob(ByteBuffer.wrap(encryptedKey))

        val plainTextKey = client.decrypt(request).plaintext
        return String(plainTextKey.array(), Charset.forName("UTF-8"))
    }
}
