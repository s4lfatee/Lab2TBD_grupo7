"# lab2-tbd"

# Creacion de tablas base de datos

psql -U {usuario} -f dbCreate.sql -d {database}

# Dump de datos

psql -U {usuario} -f dumpFile.sql -d {database}
