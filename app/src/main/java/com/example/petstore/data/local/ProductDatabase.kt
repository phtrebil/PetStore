import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.petstore.model.ProductLocal

class ProductDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Product.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "products"
        private const val COL_ID = "_id"
        private const val COL_QUANTITY = "quantity"
        private const val COL_IMAGE = "image"
        private const val COL_NAME = "name"
        private const val COL_DESCRIPTION = "description"
        private const val COL_PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COL_QUANTITY INTEGER, "
                + "$COL_IMAGE TEXT, "
                + "$COL_NAME TEXT, "
                + "$COL_DESCRIPTION TEXT, "
                + "$COL_PRICE REAL);")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addProduct(product: ProductLocal) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_QUANTITY, product.quantity)
        values.put(COL_IMAGE, product.image)
        values.put(COL_NAME, product.name)
        values.put(COL_DESCRIPTION, product.description)
        values.put(COL_PRICE, product.price)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun calculateTotalPrice(): Double {
        var totalPrice = 0.0

        val db = this.readableDatabase
        val query = "SELECT $COL_QUANTITY, $COL_PRICE FROM $TABLE_NAME"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val quantity = cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))
                val price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE))
                val productTotalPrice = quantity * price
                totalPrice += productTotalPrice
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return totalPrice
    }

    fun clearAllProducts() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}