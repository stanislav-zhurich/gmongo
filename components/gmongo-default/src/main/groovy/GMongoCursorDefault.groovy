

import com.mongodb.client.MongoCursor
import com.stan.gmongo.api.collection.GMongoCursor

class GMongoCursorDefault implements GMongoCursor {
	
	private MongoCursor cursor
	
	GMongoCursorDefault(MongoCursor cursor){
		this.cursor = cursor
	}

	@Override
	void close() {
		this.cursor.close()
	}

	@Override
	boolean hasNext() {
		return cursor.hasNext()
	}

	@Override
	def next() {
		return cursor.next()
	}

}
