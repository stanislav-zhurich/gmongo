connection {
	type  GMongoClientType.DEFAULT
	database  "catalog"
	authentication {
		username "Stan"
		password "qwerty"
	}
	options {
		poolSize 10
		ssl true
	}
	addresses {
		connection("host":"127.0.0.1", "port": 27017)
	}
}

